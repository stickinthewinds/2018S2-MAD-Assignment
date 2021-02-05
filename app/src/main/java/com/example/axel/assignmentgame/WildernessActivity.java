package com.example.axel.assignmentgame;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class WildernessActivity extends AppCompatActivity
{
    private List<Item> areaItems = new ArrayList<>();
    private List<Equipment> playerItems = new ArrayList<>();

    private Button leaveBtn;
    private TextView txtWild;
    private FragmentStatus fragStat;

    private RecyclerView rvTake;
    private RecyclerView rvDrop;
    private AreaAdapter adapterArea;
    private PlayerAdapter adapterPlayer;
    private LinearLayoutManager rvLayoutTake;
    private LinearLayoutManager rvLayoutDrop;
    private GameData gameData;
    Player p;
    Area a;

    public static Intent getIntent(Context c)
    {
        Intent intent = new Intent(c, WildernessActivity.class);
        return intent;
    }

    @Override
    public void onCreate(Bundle b)
    {
        super.onCreate(b);
        setContentView(R.layout.activity_wilderness);

        // Get the game data
        gameData = GameData.getInstance();
        p = gameData.getPlayer();
        a = gameData.getLocation();

        // Get references to all major UI elements.
        txtWild = (TextView) findViewById(R.id.wilderness);
        rvTake = (RecyclerView) findViewById(R.id.listTake);
        rvDrop = (RecyclerView) findViewById(R.id.listDrop);
        leaveBtn = (Button) findViewById(R.id.leaveBtn);

        // Set up the fragment for status
        FragmentManager fm = getSupportFragmentManager();
        fragStat = (FragmentStatus) fm.findFragmentById(
                R.id.f_status);
        if(fragStat == null) // It might already be there!
        {
            fragStat = new FragmentStatus();
            fm.beginTransaction()
                    .add(R.id.f_status, fragStat)
                    .commit();
        }

        leaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent returnData = new Intent();
                setResult(RESULT_OK, returnData);
                finish();
            }
        });

        rvTake.setHasFixedSize(true);
        rvDrop.setHasFixedSize(true);

        // Set up the recycler views
        adapterArea = new AreaAdapter();
        adapterPlayer = new PlayerAdapter();
        rvLayoutTake = new LinearLayoutManager(this);
        rvLayoutDrop = new LinearLayoutManager(this);
        rvTake.setAdapter(adapterArea);
        rvTake.setLayoutManager(rvLayoutTake);
        rvDrop.setAdapter(adapterPlayer);
        rvDrop.setLayoutManager(rvLayoutDrop);

        // Fill the buy list
        int insertPosition = 0;

        for (Item i : a.getItems())
        {
            areaItems.add(i);

            // ... we need to notify the adapter where the new item was inserted.
            adapterArea.notifyItemInserted(insertPosition);

            insertPosition++;
        }

        // Fill the sell list
        insertPosition = 0;

        for (Equipment e : p.getEquipment())
        {
            playerItems.add(e);

            // ... we need to notify the adapter where the new item was inserted.
            adapterArea.notifyItemInserted(insertPosition);

            insertPosition++;
        }
    }

    private class AreaViewHolder extends RecyclerView.ViewHolder
    {
        private Item item;

        private TextView name;
        private TextView desc;
        private TextView value;
        private Button listButton;

        public AreaViewHolder(LayoutInflater li, ViewGroup parent)
        {
            super(li.inflate(R.layout.list_entry, parent, false));

            // Get references to the UI elements in each list row.
            name = (TextView) itemView.findViewById(R.id.itemName);
            desc = (TextView) itemView.findViewById(R.id.itemDesc);
            value = (TextView) itemView.findViewById(R.id.itemValue);
            listButton = (Button) itemView.findViewById(R.id.listButton);

            listButton.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    if (item instanceof Equipment)
                    {
                        p.addEquipment((Equipment)item);
                        playerItems.add((Equipment)item);

                        adapterPlayer.notifyItemInserted(playerItems.size()-1);
                    }
                    else
                    {
                        Food f = (Food)item;
                        p.setHealth(Math.min(100.0,p.getHealth() + f.getHealth()));
                    }
                    areaItems.remove(item);
                    a.getItems().remove(item);

                    adapterArea.notifyItemRemoved(getAdapterPosition());
                    fragStat.updateFragment(gameData);
                }
            });
        }

        public void bind(Item item)
        {
            this.item = item;

            name.setText(item.getName());
            desc.setText(item.getDescription());
            value.setText("$" +item.getValue());
            listButton.setText("Take");
        }
    }

    public class AreaAdapter extends RecyclerView.Adapter<AreaViewHolder>
    {
        @Override
        public AreaViewHolder onCreateViewHolder(ViewGroup container, int viewType)
        {
            return new AreaViewHolder(LayoutInflater.from(WildernessActivity.this), container);
        }

        @Override
        public void onBindViewHolder(AreaViewHolder vh, int position)
        {
            vh.bind(areaItems.get(position));
        }

        @Override
        public int getItemCount()
        {
            return areaItems.size();
        }
    }

    private class PlayerViewHolder extends RecyclerView.ViewHolder
    {
        private Equipment e;

        private TextView name;
        private TextView desc;
        private TextView value;
        private Button listButton;

        public PlayerViewHolder(LayoutInflater li, ViewGroup parent)
        {
            super(li.inflate(R.layout.list_entry, parent, false));

            // Get references to the UI elements in each list row.
            name = (TextView) itemView.findViewById(R.id.itemName);
            desc = (TextView) itemView.findViewById(R.id.itemDesc);
            value = (TextView) itemView.findViewById(R.id.itemValue);
            listButton = (Button) itemView.findViewById(R.id.listButton);

            listButton.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    p.sellEquipment(e);
                    p.setCash(p.getCash() - e.getValue());
                    playerItems.remove(e);
                    areaItems.add(e);
                    a.getItems().add(e);
                    adapterPlayer.notifyItemRemoved(getAdapterPosition());
                    adapterArea.notifyItemInserted(areaItems.size()-1);
                    fragStat.updateFragment(gameData);
                }
            });
        }

        public void bind(Equipment e)
        {
            this.e = e;

            name.setText(e.getName());
            desc.setText(e.getDescription());
            value.setText("$" + e.getValue());
            listButton.setText("Drop");
        }
    }

    public class PlayerAdapter extends RecyclerView.Adapter<PlayerViewHolder>
    {
        @Override
        public PlayerViewHolder onCreateViewHolder(ViewGroup container, int viewType)
        {
            return new PlayerViewHolder(LayoutInflater.from(WildernessActivity.this), container);
        }

        @Override
        public void onBindViewHolder(PlayerViewHolder vh, int position)
        {
            vh.bind(playerItems.get(position));
        }

        @Override
        public int getItemCount()
        {
            return playerItems.size();
        }
    }
}
