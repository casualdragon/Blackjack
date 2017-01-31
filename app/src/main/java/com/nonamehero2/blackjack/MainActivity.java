package com.nonamehero2.blackjack;
/**
 * As of now this is a rough design and we need to find some poker chips
 * to use in addition to sprucing it up somehow.
 */


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView playView = (TextView) findViewById(R.id.play_text);
        TextView exitView = (TextView) findViewById(R.id.exit_text);

        //Play button listener
        //Need to open the betting activity when its created.
        playView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Start Intent for betting here
                Intent intent = new Intent(getApplicationContext(), BetActivity.class);
                startActivity(intent);
            }
        });

        //Exit button listener
        //This is functional however it might not be the best way to approach exiting
        //the application.
        exitView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.exit(0);
            }
        });
    }
}
