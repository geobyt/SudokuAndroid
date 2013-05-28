package com.example.sudokuandroid;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //set up button listener
        Button startButton = (Button) findViewById(R.id.play_game);
        startButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                startGame();
            }
        });
    }

    private void startGame() {
        Intent launchGame = new Intent(this, PlayGame.class);
        startActivity(launchGame);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
