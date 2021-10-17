package io.github.ikbo0619.testing.team6.durak;

import java.util.ArrayList;

public class Hand {
    private ArrayList<Card> cards;

    public Hand() {}

    public Hand(int n) {}

    public void add(Card c) {}

    public void remove(Card c) {}

    public int size() {
        return 0;
    }

    public boolean needsToDraw() {
        return false;
    }

    public int numberToDraw() {
        return 0;
    }

    public ArrayList<Card> getCards() {
        return null;
    }

    public Card getCardByIndex(int i) {
        return null;
    }

    public Card useCardByIndex(int i) {
        return null;
    }

    @Override
    public String toString() {
        return "";
    }
}
