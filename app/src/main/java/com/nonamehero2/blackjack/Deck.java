package com.nonamehero2.blackjack;

/**
 * Created by Amy on 2/1/2017.
 */

public class Deck {
    private Card [][] deck = new Card[3][13];
    private final int row = 3;
    private final int column = 13;

    //Constructor
    public Deck(){
        for(int i = 0; i < row; i++){
            for (int j = 0; j < column; j++) {
                deck[i][j] = new Card(i, j);
            }
        }
    }
}
