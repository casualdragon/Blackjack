package com.nonamehero2.blackjack;

import android.util.Log;

import java.util.Random;

/**
 * Created by Amy on 2/1/2017.
 */

public class Deck {
    //Fields
    private final int row = 3;
    private final int column = 14;
    private Card [][] deck = new Card[row][column];
    private Card [] past = new Card [10];
    private int position;


    //Constructor
    public Deck(){
        for(int i = 0; i < row; i++){
            //The row is the suit of the card
            for (int j = 1; j < column; j++) {
                //The column is the number of the card
                deck[i][j] = new Card(i, j);
            }
        }
        position = 0;
        for(int i = 0; i < 10; i++){
            past[i] = new Card(0,1);
        }
    }

    //Produces a random card
    public Card randomCard(){
        boolean flag = true;
        Random random = new Random();
        int suit;
        int card;
        while(true) {
            suit = random.nextInt(row);

            //There is a chance for the card to be 0
            card = random.nextInt(column);
            if(card == 0){
                card = 1;
            }

            for (int i = 0; i < position; i++) {
                if (deck[suit][card] == past[i]) {
                    flag = false;
                }
            }
            if(flag){
                break;
            }

        }
        position++;
        if(position == 10){
            position = 0;
        }
        past[position] = new Card(suit, card);

        //For debugging
        Log.i("======================", "suit:" + Integer.toString(suit));
        Log.i("======================", "card:" + Integer.toString(card));
        return deck[suit][card];
    }
}
