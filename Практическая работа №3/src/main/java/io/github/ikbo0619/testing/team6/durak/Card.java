package io.github.ikbo0619.testing.team6.durak;

import io.github.ikbo0619.testing.team6.durak.cardstatic.Static;

import java.util.Arrays;
import java.util.Random;

public class Card implements Comparable {

    private String rank;
    private String suit;
    private String color;

    private Random r = new Random();

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
        } else {
            throw new IllegalArgumentException("Invalid rank or suit");
        }
    }

    @Override
    public String toString() {
        String ret = "<" + rank + ", " + suit + ">";
        return ret;
    }

    @Override
    public int compareTo(Object o) {
        Card otherCard = (Card) o;

        int thisValue = Static.values.get(rank);
        int otherValue = Static.values.get(otherCard.rank);

        return thisValue - otherValue;
    }

    public int trueCompareTo(Object o, String t) {
        Card otherCard = (Card) o;

        boolean thisCardIsTrump = this.isTrump(t);
        boolean otherCardIsTrump = otherCard.isTrump(t);

        int valueDifference = this.compareTo(o);

        if (thisCardIsTrump && otherCardIsTrump) {
            return valueDifference;
        } else if (thisCardIsTrump && !otherCardIsTrump) {
            return 1;
        } else if (!thisCardIsTrump && otherCardIsTrump) {
            return -1;
        } else if (sameSuit(o)) {
            return valueDifference;
        } else {
            // Different suit; cannot occur based on game rules
            throw new IllegalArgumentException("Different suit");
        }
    }

    public int trueCompareTo(Object o) {
        return trueCompareTo(o, Durak.TRUMP);
    }

    public boolean isTrump(String t) {
        return suit.equals(t);
    }

    public boolean isTrump() {
        return suit.equals(Durak.TRUMP);
    }

    public boolean sameSuit(Object o) {
        Card otherCard = (Card) o;

        String thisSuit = this.suit;
        String otherSuit = otherCard.suit;

        return thisSuit.equals(otherSuit);
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
        return 1;
    }
}
