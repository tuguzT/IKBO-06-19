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

    public Field(Card attackCard) {
        this();

        Pair initialPair = new Pair(attackCard);
        pairs.add(initialPair);

        String initialCardRank = attackCard.getRank();
        playedRanks.add(initialCardRank);
    }

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
        return completed;
    }

    public Pair currentOpenPair() {
        return null;
    }

    public void toggleCompleted() {
        completed = !completed;
    }

    public boolean endField() {
        return false;
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
        return "";
    }
}
