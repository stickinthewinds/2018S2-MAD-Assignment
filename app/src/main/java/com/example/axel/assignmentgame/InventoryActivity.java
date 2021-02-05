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

public class InventoryActivity extends AppCompatActivity
{
    private List<Equipment> playerItems = new ArrayList<>();
    private List<Item> areaItems = null;

    private Button leaveBtn;
    private TextView txtInv;
    private FragmentStatus fragStat;

    private RecyclerView rv;
    private ItemAdapter adapter;
    private LinearLayoutManager rvLayout;
    private GameData gameData;
    Player p;

    public static Intent getIntent(Context c)
    {
        Intent intent = new Intent(c, InventoryActivity.class);
        return intent;
    }

    @Override
    public void onCreate(Bundle b)
    {
        super.onCreate(b);
        setContentView(R.layout.activity_inventory);

        // Get the game data
        gameData = GameData.getInstance();
        p = gameData.getPlayer();
        areaItems = gameData.getLocation().getItems();

        // Get references to all major UI elements.
        txtInv = (TextView) findViewById(R.id.inv);
        rv = (RecyclerView) findViewById(R.id.listUse);
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

        // Set up the recycler view
        adapter = new ItemAdapter();
        rvLayout = new LinearLayoutManager(this);
        rv.setAdapter(adapter);
        rv.setLayoutManager(rvLayout);

        // Fill the list
        int insertPosition = 0;

        for (Equipment e : p.getEquipment())
        {
            playerItems.add(e);
            adapter.notifyItemInserted(insertPosition);

            insertPosition++;
        }
    }

    private class ItemViewHolder extends RecyclerView.ViewHolder
    {
        private Equipment e;

        private TextView name;
        private TextView desc;
        private TextView value;
        private Button useBtn;

        public ItemViewHolder(LayoutInflater li, ViewGroup parent)
        {
            super(li.inflate(R.layout.list_entry, parent, false));

            // Get references to the UI elements in each list row.
            name = (TextView) itemView.findViewById(R.id.itemName);
            desc = (TextView) itemView.findViewById(R.id.itemDesc);
            value = (TextView) itemView.findViewById(R.id.itemValue);
            useBtn = (Button) itemView.findViewById(R.id.listButton);

            useBtn.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    if (e.getUse().equals("scope"))
                    {
                        // Adjust inventory of player without selling
                        p.getEquipment().remove(e);
                        playerItems.remove(e);

                        // Fix player mass
                        p.setEquipmentMass(p.getEquipmentMass() - e.getMass());

                        // Use the item
                        startActivity(SmellActivity.getIntent(InventoryActivity.this));

                        adapter.notifyItemRemoved(getAdapterPosition());
                        fragStat.updateFragment(gameData);
                    }
                    else if (e.getUse().equals("improbability"))
                    {
                        // Adjust inventory of player without selling
                        p.getEquipment().remove(e);
                        playerItems.remove(e);

                        // Fix player mass
                        p.setEquipmentMass(p.getEquipmentMass() - e.getMass());
                        p.setEquipmentMass(p.getEquipmentMass() - e.getMass());

                        // Use the item
                        GameData.generateMap();

                        adapter.notifyItemRemoved(getAdapterPosition());
                        fragStat.updateFragment(gameData);
                        GameToasts.makeToast(InventoryActivity.this, "Map regenerated.");
                    }
                    else if (e.getUse().equals("kenobi"))
                    {
                        // Adjust inventory of player
                        p.getEquipment().remove(e);
                        playerItems.remove(e);

                        adapter.notifyItemRemoved(getAdapterPosition());

                        // Use the item
                        for (Item i: areaItems)
                        {
                            if (i instanceof Equipment)
                            {
                                p.addEquipment((Equipment)i);
                                playerItems.add((Equipment)i);

                                adapter.notifyItemInserted(playerItems.size() - 1);
                            }
                            else
                            {
                                p.setHealth(Math.min(100.0,p.getHealth() + ((Food) i).getHealth()));
                            }
                        }

                        areaItems.clear();

                        fragStat.updateFragment(gameData);
                    }
                    else
                    {
                        GameToasts.makeToast(InventoryActivity.this, "Nothing happened.");
                    }
                }
            });
        }

        public void bind(Equipment e)
        {
            this.e = e;

            name.setText(e.getName());
            desc.setText(e.getDescription());
            value.setText("$" + e.getValue());
            useBtn.setText("Use");
        }
    }

    public class ItemAdapter extends RecyclerView.Adapter<ItemViewHolder>
    {
        @Override
        public ItemViewHolder onCreateViewHolder(ViewGroup container, int viewType)
        {
            return new ItemViewHolder(LayoutInflater.from(InventoryActivity.this), container);
        }

        @Override
        public void onBindViewHolder(ItemViewHolder vh, int position)
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
