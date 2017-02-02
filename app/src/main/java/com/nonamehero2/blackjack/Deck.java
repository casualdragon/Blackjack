package com.nonamehero2.blackjack;

/**
 * Created by Amy on 2/1/2017.
 */

public class Deck {
    //Fields
    private Card [][] deck = new Card[3][13];
    private final int row = 3;
    private final int column = 13;

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

}
