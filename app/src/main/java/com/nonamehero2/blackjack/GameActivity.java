package com.nonamehero2.blackjack;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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

        ImageView [] playerImageViews = new ImageView[5];
        playerImageViews[0] = (ImageView) findViewById(R.id.player_imageview1);
        playerImageViews[1] = (ImageView) findViewById(R.id.player_imageview2);
        playerImageViews[2] = (ImageView) findViewById(R.id.player_imageview3);
        playerImageViews[3] = (ImageView) findViewById(R.id.player_imageview4);
        playerImageViews[4] = (ImageView) findViewById(R.id.player_imageview5);

        ImageView [] dealerImageViews = new ImageView[5];
        dealerImageViews[0] = (ImageView) findViewById(R.id.dealer_imageview1);
        dealerImageViews[1] = (ImageView) findViewById(R.id.dealer_imageview2);
        dealerImageViews[2] = (ImageView) findViewById(R.id.dealer_imageview3);
        dealerImageViews[3] = (ImageView) findViewById(R.id.dealer_imageview4);
        dealerImageViews[4] = (ImageView) findViewById(R.id.dealer_imageview5);

        for (ImageView card: playerImageViews) {
            card.setImageResource(R.drawable.card00);
        }

        for (ImageView card: dealerImageViews) {
            card.setImageResource(R.drawable.card00);
        }

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
        int resID;

        Card rand;
        for(int i = 0; i < 3; i++ ) {
            rand = deck.randomCard();
            user.addCard(rand);
            resID = getResources().getIdentifier(rand.toString(), "drawable", getPackageName());
            playerImageViews[i].setImageResource(resID);
        }
        //Check for natural 21 for player
        TextView tv = (TextView)findViewById(R.id.player_total_textview);
        tv.setText(Integer.toString(user.getCardTotal()));

        rand = deck.randomCard();
        dealer.addCard(rand);
        dealer.addCard(deck.randomCard());
        resID = getResources().getIdentifier(rand.toString(), "drawable", getPackageName());
        dealerImageViews[0].setImageResource(resID);

        //Check for natural 21 for dealer

        findViewById(R.id.hit_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("=====================", "Hit button");
                //Picks a random card
                Card rand = deck.randomCard();
                user.addCard(rand);

                //Displays the card in the user's hand

                //Check hand to see if it is 21 - Win
                if(user.getCardTotal() == 21){

                //Check hand to see if it is over 21
                }else if(user.getCardTotal() > 21){
                    //Lose - subtract bet from total
                    user.subtractBet();
                    user.resetHand();
                    dealer.resetHand();
                }
                //Check to see if the player's hand has 5 cards
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
