package com.nonamehero2.blackjack;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

/**
 * Created by Amy on 2/1/2017.
 */

/**
 * Back of image credit jeffshee
 * http://opengameart.org/content/colorful-poker-card-back
 * Licensed under Creative Commons CC BY 3.0
 * https://creativecommons.org/licenses/by/3.0/
 *
 * Card Images credit
 * https://code.google.com/archive/p/vector-playing-cards/
 * Card images are in public domain.
 */

public class GameActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        Intent intent = getIntent();
        int bet = intent.getIntExtra(BetActivity.BET_KEY, 0);
        if(bet != 0){

        }else{
            Toast.makeText(getApplicationContext(), "An error has occurred.", Toast.LENGTH_LONG).show();
            Intent ret = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(ret);
        }
    }
}
