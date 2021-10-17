package io.github.ikbo0619.testing.team6.durak;

import java.util.ArrayList;

public class Field {
    private final ArrayList<Pair> pairs;
    private final ArrayList<String> playedRanks;
    private boolean completed;

    public Field() {
        pairs = new ArrayList<>();
        playedRanks = new ArrayList<>();
        completed = false;
    }

    public Field(Card card) {
        this();

        Pair initialPair = new Pair(card);
        pairs.add(initialPair);

        String initialCardRank = card.getRank();
        playedRanks.add(initialCardRank);
    }

    public void attack(Card card) {
        if (canAttack() && isValidAttack(card)) {
            Pair newAttackPair = new Pair(card);
            pairs.add(newAttackPair);
        } else {
            throw new IllegalArgumentException("You can't attack.");
        }
    }

    public void respond(Card card) {
        if (anyOpenPairs()) {
            Pair openPair = currentOpenPair();
            openPair.response(card);
        }
    }

    public boolean isValidAttack(Card card) {
        String thisRank = card.getRank();
        for (String rank : playedRanks) {
            if (thisRank.equals(rank)) {
                return true;
            }
        }
        return false;
    }

    public boolean canAttack() {
        return !anyOpenPairs();
    }

    public boolean anyOpenPairs() {
        for (Pair pair : pairs) {
            if (!pair.isCompleted()) {
                return true;
            }
        }
        return false;
    }

    public boolean isCompleted() {
        return completed;
    }

    public Pair currentOpenPair() {
        Pair ret = null;
        for (Pair pair : pairs) {
            if (!pair.isCompleted()) {
                ret = pair;
            }
        }
        if (ret != null) {
            return ret;
        } else {
            throw new IllegalArgumentException("There are no open pairs.");
        }
    }

    public void toggleCompleted() {
        completed = !completed;
    }

    public boolean endField() {
        toggleCompleted();
        return anyOpenPairs();
    }

    public ArrayList<Card> fetchAllCards() {
        ArrayList<Card> result = new ArrayList<>();
        for (Pair pair : pairs) {
            ArrayList<Card> pairCards = pair.fetchAllCards();
            result.addAll(pairCards);
        }
        return result;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("+++ Field +++\n\n");
        for (Pair pair : pairs) {
            result.append(pair).append("\n\n");
        }
        result.append("+++ Field +++\n\n");
        return result.toString();
    }
}
