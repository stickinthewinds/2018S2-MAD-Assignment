package com.example.axel.assignmentgame;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class OverviewActivity extends AppCompatActivity
{
    private List<Area> areas = new ArrayList<>();

    private Button leaveBtn;
    private TextView txtOver;
    private FragmentStatus fragStat;
    private FragmentArea fragArea;

    private RecyclerView rv;
    private AreaAdapter adapter;
    private LinearLayoutManager rvLayout;
    private GameData gameData;
    Player p;

    public static Intent getIntent(Context c)
    {
        Intent intent = new Intent(c, OverviewActivity.class);
        return intent;
    }

    @Override
    public void onCreate(Bundle b)
    {
        super.onCreate(b);
        setContentView(R.layout.activity_overview);

        // Get the game data
        gameData = GameData.getInstance();
        p = gameData.getPlayer();

        // Get references to all major UI elements.
        txtOver = (TextView) findViewById(R.id.overview);
        rv = (RecyclerView) findViewById(R.id.listOverview);
        leaveBtn = (Button) findViewById(R.id.leaveBtn);

        // Set up the fragment for status
        FragmentManager fm = getSupportFragmentManager();
        fragStat = (FragmentStatus) fm.findFragmentById(
                R.id.f_status);
        fragArea = (FragmentArea) fm.findFragmentById(
                R.id.f_area);
        if(fragStat == null) // It might already be there!
        {
            fragStat = new FragmentStatus();
            fm.beginTransaction()
                    .add(R.id.f_status, fragStat)
                    .commit();
        }
        if(fragArea == null)
        {
            fragArea = new FragmentArea();
            fm.beginTransaction()
                    .add(R.id.f_area, fragArea)
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

        // Set up the recycler views
        adapter = new AreaAdapter();
        rvLayout = new GridLayoutManager(OverviewActivity.this, gameData.getCols(), GridLayoutManager.HORIZONTAL,false);
        rv.setAdapter(adapter);
        rv.setLayoutManager(rvLayout);

        // Fill the list
        int insertPosition = 0;
        Area[][] grid = gameData.getGrid();

        for (int ii = 0; ii < gameData.getRows(); ii++)
        {
            for (int jj = 0; jj < gameData.getCols(); jj++)
            {
                areas.add(grid[ii][jj]);
                adapter.notifyItemInserted(insertPosition);
                insertPosition++;
            }
        }
    }

    private class AreaViewHolder extends RecyclerView.ViewHolder
    {
        private Area a;

        private ImageView location;
        private ImageView playerLoc;
        private ImageView starLocation;

        public AreaViewHolder(LayoutInflater li, ViewGroup parent)
        {
            super(li.inflate(R.layout.overview_entry, parent, false));
            int size = parent.getMeasuredHeight() / gameData.getCols() + 2;
            ViewGroup.LayoutParams lp = itemView.getLayoutParams();
            lp.width = size;
            lp.height = size;

            // Get references to the UI elements in each list row.
            location = (ImageView) itemView.findViewById(R.id.location);
            playerLoc = (ImageView) itemView.findViewById(R.id.playerLocation);
            starLocation = (ImageView) itemView.findViewById(R.id.starLocation);
        }

        public void bind(Area a)
        {
            this.a = a;

            // Set the type of location
            if (a.isTown())
                location.setImageResource(R.drawable.ic_town);
            else
                location.setImageResource(R.drawable.ic_wilderness);

            // Set star if location is starred
            if (a.isStarred())
                starLocation.setImageResource(R.mipmap.ic_star_on);

            if (a.equals(gameData.getLocation()))
                playerLoc.setImageResource(R.mipmap.ic_player);

            // Darken area if unexplored
            if (!a.isExplored())
                location.setColorFilter(R.color.colorBlack);
            else
                location.clearColorFilter();


        }
    }

    public class AreaAdapter extends RecyclerView.Adapter<AreaViewHolder>
    {
        @Override
        public AreaViewHolder onCreateViewHolder(ViewGroup container, int viewType)
        {
            return new AreaViewHolder(LayoutInflater.from(OverviewActivity.this), container);
        }

        @Override
        public void onBindViewHolder(AreaViewHolder vh, int position)
        {
            vh.bind(areas.get(position));
        }

        @Override
        public int getItemCount()
        {
            return areas.size();
        }
    }
}
