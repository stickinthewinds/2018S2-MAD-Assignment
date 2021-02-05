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

public class SmellActivity extends AppCompatActivity
{

    private List<Item> areaItems = new ArrayList<>();
    List<Integer> distR = new ArrayList<>();
    List<Integer> distC = new ArrayList<>();

    private Button leaveBtn;
    private TextView txtSmell;
    private FragmentStatus fragStat;

    private RecyclerView rv;
    private ItemAdapter adapter;
    private LinearLayoutManager rvLayout;
    private GameData gameData;
    Area[][] areas;
    Player p;

    public static Intent getIntent(Context c)
    {
        Intent intent = new Intent(c, SmellActivity.class);
        return intent;
    }

    @Override
    public void onCreate(Bundle b)
    {
        super.onCreate(b);
        setContentView(R.layout.activity_smelloscope);

        // Get the game data
        gameData = GameData.getInstance();
        areas = gameData.getGrid();
        p = gameData.getPlayer();

        // Get players location as ints
        int row = p.getRowLocation(), col = p.getColLocation();
        // Get limits as ints
        int maxRow = gameData.getRows(), maxCol = gameData.getCols();

        // Get references to all major UI elements.
        txtSmell = (TextView) findViewById(R.id.smell);
        rv = (RecyclerView) findViewById(R.id.listItems);
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
                finish();
            }
        });

        // Set up the recycler view
        adapter = new ItemAdapter();
        rvLayout = new LinearLayoutManager(this);
        rv.setAdapter(adapter);
        rv.setLayoutManager(rvLayout);

        // Fill the list
        List<Area> smellAreas = new ArrayList<>();
        List<Integer> tempR = new ArrayList<>();
        List<Integer> tempC = new ArrayList<>();

        for (int ii = row-2; ii < row+2; ii++)
        {
            if (ii >= 0 && ii < maxRow)
            {
                for (int jj = col-2; jj < col+2; jj++)
                {
                    if (jj >= 0 && jj < maxCol)
                    {
                        if (ii != row && jj != col)
                        {
                            smellAreas.add(areas[ii][jj]);
                            tempR.add(ii - row);
                            tempC.add(jj - col);
                        }
                    }
                }
            }
        }

        int insertPosition = 0;

        for (Area a : smellAreas)
        {
            for (Item i : a.getItems())
            {
                areaItems.add(i);
                distR.add(tempR.get(smellAreas.indexOf(a)));
                distC.add(tempC.get(smellAreas.indexOf(a)));

                // ... we need to notify the adapter where the new item was inserted.
                adapter.notifyItemInserted(insertPosition);
                insertPosition++;
            }
        }

    }

        private class ItemViewHolder extends RecyclerView.ViewHolder
        {
            private Item item;

            private TextView name;
            private TextView distRows;
            private TextView distCols;

            public ItemViewHolder(LayoutInflater li, ViewGroup parent)
            {
                super(li.inflate(R.layout.smell_entry, parent, false));

                // Get references to the UI elements in each list row.
                name = (TextView) itemView.findViewById(R.id.itemName);
                distRows = (TextView) itemView.findViewById(R.id.itemRows);
                distCols = (TextView) itemView.findViewById(R.id.itemCols);
            }

            public void bind(Item item, int distR, int distC)
            {
                this.item = item;

                name.setText(item.getName());
                if (distR >= 0)
                {
                    distRows.setText(distR + " east");
                }
                else
                {
                    distRows.setText(Math.abs(distR) + " west");
                }
                if (distC > 0)
                {
                    distCols.setText(distC + " south");
                }
                else
                {
                    distCols.setText(Math.abs(distC) + " north");
                }
                //distCols.setText("$" +item.getValue());
            }
        }

        public class ItemAdapter extends RecyclerView.Adapter<ItemViewHolder>
        {
            @Override
            public ItemViewHolder onCreateViewHolder(ViewGroup container, int viewType)
            {
                return new ItemViewHolder(LayoutInflater.from(SmellActivity.this), container);
            }

            @Override
            public void onBindViewHolder(ItemViewHolder vh, int position)
            {
                vh.bind(areaItems.get(position), distR.get(position), distC.get(position));
            }

            @Override
            public int getItemCount()
            {
                return areaItems.size();
            }
        }
}
