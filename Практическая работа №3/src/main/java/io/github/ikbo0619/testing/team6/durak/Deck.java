package io.github.ikbo0619.testing.team6.durak;

import io.github.ikbo0619.testing.team6.durak.cardstatic.Static;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;

public class Deck {
    private final Stack<Card> cards;

    public Deck() {
        cards = new Stack<>();

        ArrayList<Card> allCards = new ArrayList<>();
        for (String rank : Static.ranks)
            for (String suit : Static.suits) {
                allCards.add(new Card(rank, suit));
                Collections.shuffle(allCards);
            }

        for (Card card : allCards)
            cards.push(card);
    }

    public Card draw() {
        return isEmpty() ? null : cards.pop();
    }

    public boolean isEmpty() {
        return cards.empty();
    }

    public int size() {
        return cards.size();
    }

    public void reinsert(Card t) {
        cards.add(0, t);
    }

    @Override
    public String toString() {
        String ret = "[Bottom]\n";
        for (Card card : cards)
            ret += card + "\n";
        ret += "[Top]\n";
        return ret;
    }
}
