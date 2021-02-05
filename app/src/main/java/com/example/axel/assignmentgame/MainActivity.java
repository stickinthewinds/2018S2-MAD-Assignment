package com.example.axel.assignmentgame;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button playBtn;
    private GameData gameData;

    public static Intent getIntent(Context c)
    {
        Intent intent = new Intent(c, MainActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        playBtn = (Button) findViewById(R.id.playBtn);

        playBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override public void onClick(View v)
            {
                gameData = GameData.destroySingleton();
                startActivity(NavigationActivity.getIntent(MainActivity.this));
            }
        });
    }
}