package io.github.ikbo0619.testing.team6.durak;

import java.util.ArrayList;

public class Pair {
    private Card attacker;
    private Card defender;

    private boolean completed;

    public Pair(Card a) {
        attacker = a;
        completed = false;
    }

    public void response(Card d) {
        setDefender(d);
        toggleCompleted();
    }

    public Card getAttacker() {
        return attacker;
    }

    public Card getDefender() {
        return defender;
    }

    public boolean isCompleted() {
        return completed;
    }

    public boolean isValidDefender(Card d) {
        try {
            if (d.trueCompareTo(attacker) > 0) {
                return true;
            }
        } catch (IllegalArgumentException e) {
            return false;
        }
        return false;
    }

    public void setDefender(Card d) {
        if (isValidDefender(d)) {
            defender = d;
        } else {
            throw new IllegalArgumentException("Invalid defender");
        }
    }

    public void toggleCompleted() {
        completed = !completed;
    }

    public ArrayList<Card> fetchAllCards() {
        ArrayList<Card> ret = new ArrayList<Card>();
        if (completed) {
            ret.add(attacker);
            ret.add(defender);
        } else {
            ret.add(attacker);
        }
        return ret;
    }

    @Override
    public String toString() {
        String ret = new String("{Pair}\n");
        for (Card card : fetchAllCards()) {
            ret += card + "\n";
        }
        ret += "{Pair}\n";
        return ret;
    }
}
