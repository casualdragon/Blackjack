package com.nonamehero2.blackjack;
/**
 * As of now this is a rough design and we need to find some poker chips
 * to use in addition to sprucing it up somehow.
 */


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;

public class MainActivity extends AppCompatActivity {

    private Player user;
    private Player dealer;
    public final static String USER = "user";
    public final static String DEALER = "dealer";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        if(readFile()){
            Intent inte = new Intent(getApplicationContext(), GameActivity.class);
            inte.putExtra(USER, user);
            inte.putExtra(DEALER, dealer);

            startActivity(inte);
        }


        //new Card(0,12).toString();

        TextView playView = (TextView) findViewById(R.id.play_text);
        TextView aboutView = (TextView) findViewById(R.id.about_text);
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

        aboutView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Start Intent for betting here
                Intent intent = new Intent(getApplicationContext(), AboutActivity.class);
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
    private boolean readFile(){
        boolean flag = true;
        try{
            FileInputStream fis = openFileInput(GameActivity.FILENAME);
            ObjectInputStream ois = new ObjectInputStream(fis);
            user = (Player) ois.readObject();
            dealer = (Player) ois.readObject();

            Log.i("=================", "Bet.MainActivity: " + user.getCurrentBet());

            fis.close();
            ois.close();

        }catch (Exception e){
            flag = false;
        }
        return flag;
    }
}
