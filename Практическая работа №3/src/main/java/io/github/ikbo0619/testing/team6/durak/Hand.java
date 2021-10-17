package io.github.ikbo0619.testing.team6.durak;

import java.util.ArrayList;

public class Hand {
    private final ArrayList<Card> cards;

    public Hand() {
        cards = new ArrayList<>();
    }

    public Hand(int n) {
        cards = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            Card thisCard = new Card();
            cards.add(thisCard);
        }
    }

    public void add(Card c) {
        cards.add(c);
    }

    public void remove(Card c) {
        cards.remove(c);
    }

    public int size() {
        return cards.size();
    }

    public boolean needsToDraw() {
        return size() > 6;
    }

    public int numberToDraw() {
        return needsToDraw() ? 0 : 6 - size();
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    public Card getCardByIndex(int i) {
        return cards.get(i);
    }

    public Card useCardByIndex(int i) {
        return cards.remove(i);
    }

    @Override
    public String toString() {
        String ret = "";
        for (Card c : cards)
            ret += c + "\n";
        return ret;
    }
}
