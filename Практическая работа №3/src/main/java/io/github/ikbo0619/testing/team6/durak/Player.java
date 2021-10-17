package io.github.ikbo0619.testing.team6.durak;

import java.util.ArrayList;

public class Player {

    private Hand hand;
    private String name;
    private final int id = 0;
    private Deck deck;
    private static int count = 0;
    private boolean attacker;


    public Player(Deck d, String n) {
    }

    public void draw(Deck d) {
    }

    public void draw() {
    }

    public void drawCards(int n) {
    }

    public void discard(Card c) {
    }

    public int cardsInHand() {
        return 0;
    }

    // Проверка победы
    public boolean victoryAchieved() {
        return false;
    }

    public boolean isAttacker() {
        return false;
    }

    public void makeAttacker() {
    }

    public void switchRole() {
    }

    @Override
    public String toString() {
        return "";
    }

    public Card getCard(int i) {
        return null;
    }

}
