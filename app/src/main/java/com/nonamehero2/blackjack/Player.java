package com.nonamehero2.blackjack;

/**
 * Created by nonam on 1/31/2017.
 */

public class Player {
    //Fields
    private Card[] hand;
    private int money;
    private int currentBet;
    private int cardTotal;

    public Player(int currentBet) {
        this.money = 10000;
        this.cardTotal = 0;
        this.currentBet = currentBet;
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

}
