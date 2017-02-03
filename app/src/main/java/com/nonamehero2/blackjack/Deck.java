package com.nonamehero2.blackjack;

import java.util.Random;

/**
 * Created by Amy on 2/1/2017.
 */

public class Deck {
    //Fields
    private final int row = 3;
    private final int column = 13;
    private Card [][] deck = new Card[row][column];


    //Constructor
    public Deck(){
        for(int i = 0; i < row; i++){
            //The row is the suit of the card
            for (int j = 0; j < column; j++) {
                //The column is the number of the card
                deck[i][j] = new Card(i, j);
            }
        }
    }

    //Produces a random card
    public Card randomCard(){
        Random random = new Random();
        int suit = random.nextInt(row+1);
        int card = random.nextInt(column+1);
        return deck[suit][card];
    }
}
