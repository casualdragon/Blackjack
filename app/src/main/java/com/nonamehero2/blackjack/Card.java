package com.nonamehero2.blackjack;

/**
 * Created by Zachary Taylor on 1/27/2017.
 * Suit is a numerical representation of the 4 suits from 0 to 3.
 * CardNum is the value of the card in order from 0 to 12.
 * This setup is designed so it can be appened to a string and produce
 * a filename for that card. ex. suit 0 card 10 would be a 10 of hearts
 * and represented as "card010.png"
 */


public class Card {
    public Card(int suit, int cardNum) {
        if(suit >= 0 && suit <= 3){
            this.suit = suit;
        }else{
            throw new IllegalArgumentException("suit must be between 0 and 3");
        }
        if(cardNum >= 0 && cardNum <= 12){
            this.cardNum = cardNum;
        }else{
            throw new IllegalArgumentException("cardNum must be between 0 and 12");
        }
    }

    public int getSuite() { return suit; }

    public int getCardNum() {
        return cardNum;
    }

    private int suit;
    private int cardNum;
}
