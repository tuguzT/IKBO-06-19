package io.github.ikbo0619.testing.team6.durak;

import java.util.ArrayList;

public class Field {
    private ArrayList<Pair> pairs;
    private ArrayList<String> playedRanks;
    private boolean completed;

    public Field() {}

    public Field(Card attackCard) {}

    public void attack(Card attackCard) {}

    public void respond(Card defenseCard) {}

    public boolean isValidAttack(Card attackCard) {
        return false;
    }

    public boolean canAttack() {
        return false;
    }

    public boolean anyOpenPairs() {
        return false;
    }

    public boolean isCompleted() {
        return false;
    }

    public Pair currentOpenPair() {
        return null;
    }

    public void toggleCompleted() {}

    public boolean endField() {
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
