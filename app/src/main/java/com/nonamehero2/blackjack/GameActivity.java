package com.nonamehero2.blackjack;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
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
        /*
            Checks for any error with the number returned by the intent.
            If there is any error, the user is returned to the main screen
         */
        if(bet == 0){
            Toast.makeText(getApplicationContext(), "An error has occurred.", Toast.LENGTH_LONG).show();
            Intent ret = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(ret);
        }

        TextView bet_tv = (TextView)findViewById(R.id.bet_game_textView);
        bet_tv.setText(Integer.toString(bet));

        final Player user = new Player(bet);
        final Player dealer = new Player();
        final Deck deck = new Deck();

        findViewById(R.id.hit_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Picks a random card
                Card rand = deck.randomCard();
                user.addCard(rand);

                //Displays the card in the user's hand

                //Check hand to see it is over 21
                if(user.getCardTotal() == 21){
                    //Natural 21 - Win
                }else if(user.getCardTotal() > 21){
                    //Lose - subtract bet from total
                    user.subtractBet();
                }
            }
        });

        findViewById(R.id.stay_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //
            }
        });
    }
}
