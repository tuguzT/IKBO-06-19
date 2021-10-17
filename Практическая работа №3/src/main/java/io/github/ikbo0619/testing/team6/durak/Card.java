package io.github.ikbo0619.testing.team6.durak;

import io.github.ikbo0619.testing.team6.durak.cardstatic.Static;

import java.util.Arrays;
import java.util.Random;

public class Card implements Comparable<Card> {

    private static final Random r = new Random();
    private final String rank;
    private final String suit;
    private final String color;

    public Card() {
        int randRankIndex = r.nextInt(9);
        rank = Static.ranks[randRankIndex];

        int randSuitIndex = r.nextInt(4);
        suit = Static.suits[randSuitIndex];

        color = Static.colors.get(suit);
    }

    public Card(String r, String s) {
        if (Arrays.asList(Static.ranks).contains(r) && Arrays.asList(Static.suits).contains(s)) {
            rank = r;
            suit = s;
            color = Static.colors.get(suit);
            return;
        }
        throw new IllegalArgumentException("Invalid rank or suit");
    }

    @Override
    public String toString() {
        return "<" + rank + ", " + suit + ">";
    }

    @Override
    public int compareTo(Card otherCard) {
        int thisValue = Static.values.get(rank);
        int otherValue = Static.values.get(otherCard.rank);

        return thisValue - otherValue;
    }

    public int trueCompareTo(Card otherCard, String t) {
        boolean thisCardIsTrump = this.isTrump(t);
        boolean otherCardIsTrump = otherCard.isTrump(t);

        int valueDifference = this.compareTo(otherCard);

        if (thisCardIsTrump && otherCardIsTrump) {
            return valueDifference;
        } else if (thisCardIsTrump) {
            return 1;
        } else if (otherCardIsTrump) {
            return -1;
        } else if (sameSuit(otherCard)) {
            return valueDifference;
        }
        // Different suit; cannot occur based on game rules
        throw new IllegalArgumentException("Different suit");
    }

    public int trueCompareTo(Card otherCard) {
        return trueCompareTo(otherCard, Durak.TRUMP);
    }

    public boolean isTrump(String t) {
        return suit.equals(t);
    }

    public boolean isTrump() {
        return suit.equals(Durak.TRUMP);
    }

    public boolean sameSuit(Card otherCard) {
        return this.suit.equals(otherCard.suit);
    }

    public String getSuit() {
        return suit;
    }

    public String getColor() {
        return color;
    }

    public String getRank() {
        return rank;
    }

    public int getValue() {
        return Static.values.get(rank);
    }
}
