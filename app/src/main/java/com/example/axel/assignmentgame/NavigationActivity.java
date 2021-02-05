package com.example.axel.assignmentgame;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class NavigationActivity extends AppCompatActivity
{
    private static final int REQUEST_CODE_PLAY = 0;

    private Button northBtn;
    private Button southBtn;
    private Button eastBtn;
    private Button westBtn;
    private Button optBtn;
    private Button invBtn;
    private Button overBtn;
    private FragmentArea fragArea;
    private FragmentStatus fragStat;
    private  GameData gameData;
    private Player player;

    public static Intent getIntent(Context c)
    {
        Intent intent = new Intent(c, NavigationActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        gameData = GameData.getInstance();
        player = gameData.getPlayer();
        gameData.getLocation().setExplored(true);

        northBtn = (Button) findViewById(R.id.northBtn);
        southBtn = (Button) findViewById(R.id.southBtn);
        eastBtn = (Button) findViewById(R.id.eastBtn);
        westBtn = (Button) findViewById(R.id.westBtn);
        optBtn = (Button) findViewById(R.id.optBtn);
        invBtn = (Button) findViewById(R.id.invBtn);
        overBtn = (Button) findViewById(R.id.overBtn);

        // Set up the fragment for status and area
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

        // Hardcoded equipment
        Equipment equip = new Equipment("lol noob", "Sell this", 5, 45.0, "none");
        player.addEquipment(equip);

        northBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int cLoc = player.getColLocation();

                if (cLoc > 0)
                {
                    gameData.getLocation().setDescription(fragArea.getAreaDesc());
                    player.movePlayer('n');
                    gameData.getLocation().setExplored(true);
                    fragArea.updateFragment(gameData);
                }
                else
                {
                    GameToasts.makeToast(NavigationActivity.this, "Can't go that way.");
                }
                fragStat.updateFragment(gameData);
            }
        });

        southBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int cLoc = player.getColLocation();

                if (cLoc < (gameData.getCols() - 1))
                {
                    gameData.getLocation().setDescription(fragArea.getAreaDesc());
                    player.movePlayer('s');
                    gameData.getLocation().setExplored(true);
                    fragArea.updateFragment(gameData);
                }
                else
                {
                    GameToasts.makeToast(NavigationActivity.this, "Can't go that way.");
                }
                fragStat.updateFragment(gameData);
            }
        });

        eastBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int rLoc = player.getRowLocation();

                if (rLoc < (gameData.getRows() - 1))
                {
                    gameData.getLocation().setDescription(fragArea.getAreaDesc());
                    player.movePlayer('e');
                    gameData.getLocation().setExplored(true);
                    fragArea.updateFragment(gameData);
                }
                else
                {
                    GameToasts.makeToast(NavigationActivity.this, "Can't go that way.");
                }
                fragStat.updateFragment(gameData);
            }
        });

        westBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int rLoc = player.getRowLocation();

                if (rLoc > 0)
                {
                    gameData.getLocation().setDescription(fragArea.getAreaDesc());
                    player.movePlayer('w');
                    gameData.getLocation().setExplored(true);
                    fragArea.updateFragment(gameData);
                }
                else
                {
                    GameToasts.makeToast(NavigationActivity.this, "Can't go that way.");
                }
                fragStat.updateFragment(gameData);
            }
        });

        optBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (gameData.getLocation().isTown())
                {
                    startActivityForResult(MarketActivity.getIntent(NavigationActivity.this), REQUEST_CODE_PLAY);
                }
                else
                {
                    startActivityForResult(WildernessActivity.getIntent(NavigationActivity.this), REQUEST_CODE_PLAY);
                }
            }
        });

        invBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(InventoryActivity.getIntent(NavigationActivity.this), REQUEST_CODE_PLAY);
            }
        });

        overBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gameData.getLocation().setDescription(fragArea.getAreaDesc());
                startActivityForResult(OverviewActivity.getIntent(NavigationActivity.this), REQUEST_CODE_PLAY);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent returnData)
    {
        if(resultCode == RESULT_OK && requestCode == REQUEST_CODE_PLAY)
        {
            fragStat.updateFragment(gameData);
            fragArea.updateFragment(gameData);
        }
    }
}
