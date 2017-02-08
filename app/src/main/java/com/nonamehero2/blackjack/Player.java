package com.nonamehero2.blackjack;

import android.util.Log;

import java.io.Serializable;

/**
 * Created by Zachary Taylor on 1/31/2017.
 */

public class Player implements Serializable{
    //Fields
    static final int LENGTH = 5;
    private Card[] hand = new Card[5];
    private int money;
    private int currentBet;
    private int cardTotal;
    private int position;

    //This is the constructor for the player class when the user needs a bet.
    public Player(int currentBet) {
        //blanks the cards
        resetHand();
        this.money = 10000;
        this.cardTotal = 0;
        this.currentBet = currentBet;
        this.position = 0;
    }

    //This is general constructor for the player class.
    public Player(int currentBet, int money, int cardTotal, int position, Card[] hand){
        this.money = money;
        this.cardTotal = cardTotal;
        this.currentBet = currentBet;
        this.position = position;

        for(int i = 0; i < LENGTH; i++){
            if (i < position){
                this.hand[i] = hand[i];
            }else{
                hand[i] = new Card(0, 0);
            }
        }
    }

    //This is the default constructor for the player class.
    public Player(){
        resetHand();
        this.money = 0;
        this.cardTotal = 0;
        this.currentBet = 0;
        this.position = 0;
    }

    //Getters
    public Card[] getHand() {
        return hand;
    }

    public int getCurrentBet() {
        return currentBet;
    }

    public int getMoney() {
        return money;
    }

    public int getCardTotal() {
        return cardTotal;
    }

    public int getPosition(){return position;}


    //Setters
    public void setHand(Card[] hand) {
        this.hand = hand;
    }

    public void setCurrentBet(int currentBet) {
        this.currentBet = currentBet;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    //Methods
    //Adds a card to the users hand.
    public void addCard(Card card){
        if(position < LENGTH){
            hand[position] = card;
            position++;
        }else{
            throw new IllegalArgumentException("hand must be not exceed 5");
        }
    }

    //Resets the cards in the hand and sets the position to 0.
    public void resetHand() {
        for (int i = 0; i < LENGTH; i++) {
            hand[i] = new Card(0, 0);
        }
        calculateTotal();
        position = 0;
    }

    //Calculates the players score.
    public void calculateTotal(){
        int aceCount = 0;
        cardTotal = 0;
        for (Card card:hand) {
            if(card != null){
                if(card.getCardNum() == 1){
                    aceCount++;
                }

                if(card.getCardNum() > 10){
                    cardTotal += 10;
                }else {
                    cardTotal += card.getCardNum();
                }
            }
        }

        for(int i = 0; i < aceCount; i++){
            if(cardTotal + 10 <= 21){
                cardTotal += 10;
            }
        }
    }

    //Adds the bet with a factor that changes based on how the player won.
    public void addBet(double factor) {
        money = money + (int) (currentBet * factor);
    }
}
