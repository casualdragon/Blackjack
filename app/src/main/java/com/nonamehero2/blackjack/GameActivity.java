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

    private Deck deck;
    private ImageView [] playerImageViews;
    private ImageView [] dealerImageViews;
    private Player user;
    private Player dealer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        playerImageViews = new ImageView[5];
        playerImageViews[0] = (ImageView) findViewById(R.id.player_imageview1);
        playerImageViews[1] = (ImageView) findViewById(R.id.player_imageview2);
        playerImageViews[2] = (ImageView) findViewById(R.id.player_imageview3);
        playerImageViews[3] = (ImageView) findViewById(R.id.player_imageview4);
        playerImageViews[4] = (ImageView) findViewById(R.id.player_imageview5);

        dealerImageViews = new ImageView[5];
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

        user = new Player(bet);
        dealer = new Player();
        deck = new Deck();


        //Initial cards
        dealCard(user);
        dealCard(dealer);
        dealCard(user);
        dealCard(dealer);

        updateCards();

        //Check for natural 21 for player



        //Check for natural 21 for dealer

        findViewById(R.id.hit_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dealCard(user);
                dealCard(dealer);

                updateCards();
                updateScores();
            }
        });

        findViewById(R.id.stay_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //
            }
        });
    }


    //updates imageviews for the cards
    private void updateCards(){
        int id;
        Card rand;
        if(playerImageViews != null && dealer != null){
            for (int i = 0; i < Player.LENGTH; i++) {
                rand =  user.getHand()[i];
                id = getResources().getIdentifier(rand.toString(), "drawable", getPackageName());
                playerImageViews[i].setImageResource(id);

            }
            TextView tv = (TextView)findViewById(R.id.player_total_textview);
            tv.setText(Integer.toString(user.getCardTotal()));
        }

        if(dealerImageViews != null && dealer != null){
            for (int i = 0; i < Player.LENGTH; i++) {
                rand =  dealer.getHand()[i];
                id = getResources().getIdentifier(rand.toString(), "drawable", getPackageName());
                dealerImageViews[i].setImageResource(id);

            }
            TextView tv = (TextView)findViewById(R.id.dealer_total_textview);
            tv.setText(Integer.toString(dealer.getCardTotal()));
        }
    }

    //deals a card to the players
    //does not update imageviews
    //this might be moved to deck class;
    private void dealCard(Player targetPlayer){
        int resID;
        Card rand;
        rand = deck.randomCard();
        targetPlayer.addCard(rand);
    }

    private void updateScores(){



    }


}
