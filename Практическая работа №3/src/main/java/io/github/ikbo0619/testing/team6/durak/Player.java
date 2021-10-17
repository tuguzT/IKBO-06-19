package io.github.ikbo0619.testing.team6.durak;

import java.util.ArrayList;

public class Player {

    private Hand hand;
    private String name;
    private final int id;
    private Deck deck;
    private static int count = 0;
    private boolean attacker;

    public Player() {
        count++;
        id = count;
        name = "Player " + id;
        hand = new Hand();
        deck = new Deck();
        attacker = false;
    }

    public Player(Deck d) {
        count++;
        id = count;
        name = "Player " + id;
        hand = new Hand();
        deck = d;
        drawCards(6);
        attacker = false;

    }

    public Player(Deck d, String n) {
        count++;
        id = count;
        name = n;
        hand = new Hand();
        deck = d;
        drawCards(6);
        attacker = false;
    }

    public void draw(Deck d) {
        if (!d.isEmpty()) {
            Card thisCard = d.draw();
            hand.add(thisCard);
        }
    }

    public void draw() {
        draw(deck);
    }

    public void drawCards(Deck d, int n) {
        for (int i = 0; i < n; i++) {
            draw(deck);
        }
    }

    public void drawCards(int n) {
        for (int i = 0; i < n; i++) {
            draw();
        }
    }

    public void takeCard(Card c) {
        hand.add(c);
    }

    public void discard(Card c) {
        hand.remove(c);
    }

    public int cardsInHand() {
        return hand.size();
    }

    public void replenish() {
        int toDraw = hand.numberToDraw();
        drawCards(toDraw);
    }

    // Проверка победы
    public boolean victoryAchieved() {
        return ((hand.size() <= 0) && (deck.isEmpty()));
    }

    public boolean isAttacker() {
        return attacker;
    }

    public void makeAttacker() {
        attacker = true;
    }

    public void makeDefender() {
        attacker = false;
    }

    public void switchRole() {
        attacker = !attacker;
    }

    @Override
    public String toString() {
        return name;
    }

    public Card getCard(int i) {
        return hand.getCardByIndex(i - 1);
    }

    public Card useCard(int i) {
        return hand.useCardByIndex(i - 1);
    }

    public String cardList() {
        String ret = "\n=== YOUR CARDS ===\n";
        ArrayList<Card> cards = hand.getCards();
        int i = 1;
        for (Card card : cards) {
            ret += i + " ~ " + card + "\n";
            i += 1;
        }
        ret += "\n\n";
        return ret;
    }

}
