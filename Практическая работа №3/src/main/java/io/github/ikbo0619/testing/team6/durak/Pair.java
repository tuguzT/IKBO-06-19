package io.github.ikbo0619.testing.team6.durak;

import java.util.ArrayList;

public class Pair {
    private Card attacker;
    private Card defender;

    private boolean completed;

    public Pair(Card a) {

    }

    public void response(Card d) {

    }

    public Card getAttacker() {
        return attacker;
    }

    public Card getDefender() {
        return defender;
    }

    public boolean isCompleted() {
        return false;
    }

    public boolean isValidDefender(Card d) {
        return false;
    }

    public ArrayList<Card> fetchAllCards() {
        return null;
    }

    @Override
    public String toString() {
        return "";
    }
}
