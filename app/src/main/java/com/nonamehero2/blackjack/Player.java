package com.nonamehero2.blackjack;

/**
 * Created by Zachary Taylor on 1/31/2017.
 */

public class Player {
    //Fields
    private final int length = 5;
    private Card[] hand = new Card[5];
    private int money;
    private int currentBet;
    private int cardTotal;
    private int position;

    public Player(int currentBet) {
        this.money = 10000;
        this.cardTotal = 0;
        this.currentBet = currentBet;
        this.position = 0;
    }
    public Player(){
        this.money = 0;
        this.cardTotal = 0;
        this.currentBet = 0;
        this.position = 0;
    }

    //Getters and Setters
    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public int getCurrentBet() {
        return currentBet;
    }

    public void setCurrentBet(int currentBet) {
        this.currentBet = currentBet;
    }

    public int getCardTotal() {
        return cardTotal;
    }

    public Card[] getHand() {
        return hand;
    }

    public void setHand(Card[] hand) {
        this.hand = hand;
    }

    public int getPosition(){return position;}

    //Methods
    public void calculateTotal(){
        int aceCount = 0;
        if(hand == null){
            return;
        }
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
    /*
        This method is used to subtract the bet from the
        player's total, and returns a boolean value to check
        if the total is greater than the bet
     */
    public boolean subtractBet (){
        if(money >= currentBet){
            money = money - currentBet;
            return true;
        }else{
            return false;
        }
    }

    public void addCard(Card card){
        if(position < length){
            hand[position] = card;
            position++;
        }else{
            throw new IllegalArgumentException("hand must be not exceed 5");
        }
    }
    public void resetHand(){
        position = 0;
    }
}
