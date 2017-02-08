package com.nonamehero2.blackjack;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

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

    private enum gameState {NO_WIN, DEALER_STAND, PLAYER_STAND, PLAYER_WIN, DEALER_WIN, DRAW, NATURAL_WIN}
    public final static String FILENAME = "blackjack.txt";

    private gameState state;
    private int turnCount;
    private Deck deck;
    private ImageView [] playerImageViews;
    private ImageView [] dealerImageViews;
    private Player user;
    private Player dealer;
    private int bet = 0;

    final public static String PLAYER = "PLAYER_KEY";
    final public static String GAME_OVER = "Game Over";

    //String Keys for the Bundles
    final private String USER = "user";
    final private String DEALER = "dealer";
    final private String BET = "bet";
    final private String TURNCOUNT = "turnCount";
    final private String STATE = "state";

    final public static String MAIN = "mainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        if(savedInstanceState == null) {

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


            //Sets imageviews to blank card
            for (ImageView card: playerImageViews) {
              card.setImageResource(R.drawable.card00);
            }

            for (ImageView card : dealerImageViews) {
                card.setImageResource(R.drawable.card00);
            }

            user = new Player(bet);
            dealer = new Player();
            deck = new Deck();

            if(readFile()){
                updateCards();
                if(user.getPosition() == 1) {
                    checkScores(true);
                }else{
                    checkScores(false);
                }
            }else {
                startGame();
            }
        }else{
            state = (gameState) savedInstanceState.getSerializable(STATE);
            user = (Player) savedInstanceState.getSerializable(USER);
            dealer = (Player) savedInstanceState.getSerializable(DEALER);
            turnCount = (int)savedInstanceState.getSerializable(TURNCOUNT);
            bet = (int) savedInstanceState.getSerializable(BET);

            updateCards();

            // Debugging Information
            Log.i("=============", "Accessing bundle");
            Log.i("===============", "state: " + state);
            Log.i("=============", "user: " + user.getHand()[0]);
            Log.i("=================", "turncount:" + Integer.toString(turnCount));
            Log.i("=================", "bet:" + Integer.toString(bet));
        }

        int total = 0;
        Intent intent = getIntent();
        if(intent.hasExtra(BetActivity.BET_KEY)&& (state != gameState.DEALER_WIN || state != gameState.PLAYER_WIN)) {
            bet = intent.getIntExtra(BetActivity.BET_KEY, 0);
            total = intent.getIntExtra(BetActivity.TOTAL, 0);
        }
        if(intent.hasExtra(MainActivity.USER)){
            user = (Player) intent.getSerializableExtra(MainActivity.USER);
            dealer = (Player) intent.getSerializableExtra(MainActivity.DEALER);
            bet = user.getCurrentBet();
            total = user.getMoney();
        }

        user.setMoney(total);
        user.setCurrentBet(bet);

        TextView betView = (TextView) findViewById(R.id.bet_game_textView);
        betView.setText(String.format("$%s", user.getCurrentBet()));

        TextView moneyView = (TextView) findViewById(R.id.money_amount_textview);
        moneyView.setText(String.format("$%s", user.getMoney()));

        Log.i("==============", "Total.Game: " + Integer.toString(total));
        Log.i("=================", "Bet: "+ Integer.toString(bet));


        findViewById(R.id.hit_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(user.getPosition() >= 5){
                    state = gameState.PLAYER_STAND;
                    checkScores(false);
                }else {
                    dealCard(user);
                    checkScores(false);
                }
            }
        });

        findViewById(R.id.stay_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                state = gameState.PLAYER_STAND;
                checkScores(false);
            }
        });
    }

    //This function does the initial deal for the game by drawing two cards for both players.
    private void startGame() {

        //Initial cards
        dealCard(user);
        //dealCard(dealer);
        dealCard(user);
        //dealCard(dealer);

        dealer.addCard(new Card(0,10));
        dealer.addCard(new Card(0,1));


        updateCards();
        state = gameState.NO_WIN;

        //Check for natural 21 for dealer and player
        checkScores(true);
    }

    //Ends the game if the game is in a final state.
    private void endGame() {
        updateCards();
        Card [] hand = dealer.getHand();
        Log.i ("=======", "Dealer Hand");
        for(int i = 0; i < Player.LENGTH; i++){
            Log.i ("=======", hand [i].toString());
        }
        Log.i ("=======", "Total = " + dealer.getCardTotal());

        hand = user.getHand();
        Log.i ("=======", "Player Hand");
        for(int i = 0; i < Player.LENGTH; i++){
            Log.i ("=======", hand [i].toString());
        }

        if(state == gameState.DEALER_WIN){
            user.addBet(-1.0);
            if(user.getMoney() <= bet){
                //resets the player.
                user = new Player(bet);
                popupMenu(GAME_OVER, "You are out out money.");
            }else {
                popupMenu("Dealer Wins", String.format("You lose your bet. %s to %s", user.getCardTotal(), dealer.getCardTotal()));
            }
            //Toast.makeText(this,"Dealer Wins, player looses bet", Toast.LENGTH_LONG).show();
            Log.i("======================", "Dealer Wins");
            toggleButtons(false);
        } else if( state == gameState.PLAYER_WIN){
            user.addBet(2.0);
            popupMenu("Player Wins", String.format("You win 2x your bet. %s to %s", user.getCardTotal(),dealer.getCardTotal()));
            //Toast.makeText(this,"Player Wins, player wins twice bet", Toast.LENGTH_LONG).show();
            Log.i("======================", "Player Reg Win");
            toggleButtons(false);
        }
        else if(state == gameState.NATURAL_WIN){
            user.addBet(2.5);
            popupMenu("Player Wins", String.format("You win 2.5x your bet. %s to %s", user.getCardTotal(),dealer.getCardTotal()));

            //Toast.makeText(this,"Natural Win for player, player wins twice and half the bet", Toast.LENGTH_LONG).show();
            Log.i("======================", "Player Nat Win");
            toggleButtons(false);
        }else if(state == gameState.DRAW){
            popupMenu("Draw", String.format("You don't win anything. %s to %s", user.getCardTotal(),dealer.getCardTotal()));
            //Toast.makeText(this,"Draw, house wins. JK no loss for player", Toast.LENGTH_LONG).show();
            Log.i("======================", "Draw");
            toggleButtons(false);
        }

        TextView moneyView = (TextView) findViewById(R.id.money_amount_textview);
        moneyView.setText("$" + user.getMoney());
        state = gameState.NO_WIN;

    }

    //this function updates the imageviews for the cards.
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

    //This deals the cards to the player but doesn't update imageview.
    private void dealCard(Player targetPlayer){
        Card rand;
        rand = deck.randomCard();
        targetPlayer.addCard(rand);
    }

    //This function is the main logic behind the scoring system.
    private void checkScores(boolean isFirstCheck) {
        user.calculateTotal();
        dealer.calculateTotal();

        if(user.getCardTotal() > 21 && dealer.getCardTotal() > 21){
            state = gameState.DRAW;
        } else if(user.getCardTotal() > 21 || dealer.getCardTotal() > 21){
            if(user.getCardTotal() > 21){
                state = gameState.DEALER_WIN;
            }else{
                state = gameState.PLAYER_WIN;
            }
        } else if (user.getCardTotal() == 21 && dealer.getCardTotal() == 21){
            state = gameState.DRAW;
        } else if (user.getCardTotal() == 21 || dealer.getCardTotal() == 21){
            if(user.getCardTotal() == 21){
                state = gameState.PLAYER_WIN;
                if(isFirstCheck){
                    state = gameState.NATURAL_WIN;
                }
            }else{
                state = gameState.DEALER_WIN;
            }
        } else if (state == gameState.PLAYER_STAND){
            if(user.getCardTotal() < dealer.getCardTotal()){
                state = gameState.DEALER_WIN;
            }else{
                if(dealer.getPosition() >= 5){
                    if(dealer.getCardTotal() > user.getCardTotal()){
                        state = gameState.DEALER_WIN;
                    } else {
                        state = gameState.PLAYER_WIN;
                    }
                }else {
                    dealCard(dealer);
                    checkScores(false);
                }
            }
        }


        endGame();
    }

    //Toggles the buttons
    private void toggleButtons(boolean isEnabled){
        Button b = (Button) findViewById(R.id.hit_button);
        b.setEnabled(isEnabled);
        b= (Button) findViewById(R.id.stay_button);
        b.setEnabled(isEnabled);
    }

    //This is the end game popmenu where the player decides what to do.
    private void popupMenu(String title, String message){
        AlertDialog.Builder popup = new AlertDialog.Builder(GameActivity.this);

        popup.setTitle(title);
        popup.setMessage(message);

        if(title != GAME_OVER){
            popup.setPositiveButton("Conintue\nPlaying", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    if(bet > user.getMoney()){
                        bet = user.getMoney();
                    }
                    deck = new Deck();
                    dealer = new Player();
                    user.resetHand();
                    startGame();
                    toggleButtons(true);
                }
            });

            popup.setNegativeButton("Change\nBet", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Intent intent = new Intent(getApplicationContext(), BetActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.putExtra(PLAYER, user);
                    startActivity(intent);
                }
            });
        }

        popup.setNeutralButton("Main\nMenu", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                File file = new File(GameActivity.FILENAME);
                if(file.delete()){
                    Log.i("=================", file.toString() + " was deleted");
                }else{
                    Log.i("=================",  file.toString() + " was NOT deleted");
                }
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.putExtra(MAIN, MAIN);
                startActivity(intent);
            }
        });

        AlertDialog dialog = popup.create();
        dialog.setCancelable(false);
        dialog.show();

    }

    //This is for saving and resuming the game.
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putSerializable(USER, user);
        outState.putSerializable(DEALER, dealer);
        outState.putSerializable(BET, bet);
        outState.putSerializable(TURNCOUNT, turnCount);
        outState.putSerializable(STATE, state);
    }

    private void writeFile(){
        try{
            FileOutputStream fos = openFileOutput(FILENAME, Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(user);
            oos.writeObject(dealer);
            oos.writeObject(state);

            oos.close();
            fos.close();

        }catch (Exception e){
            Log.i("==============", e.getMessage());
        }
    }
    private boolean readFile(){
        boolean flag = true;
        try{
            FileInputStream fis = openFileInput(FILENAME);
            ObjectInputStream ois = new ObjectInputStream(fis);
            user = (Player) ois.readObject();
            dealer = (Player) ois.readObject();
            state = (gameState) ois.readObject();

            Log.i("================", "state: "+ state.toString());

            fis.close();
            ois.close();

        }catch (Exception e){
            flag = false;
        }
        return flag;
    }

    @Override
    protected void onStop() {
        super.onStop();
        writeFile();
    }
}
