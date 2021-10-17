package io.github.ikbo0619.testing.team6.durak;

import io.github.ikbo0619.testing.team6.durak.cardstatic.Static;

public class Card implements Comparable {
    private String rank;
    private String suit;
    private String color;
    public Card() {

    }

    public Card(String r, String s) {

    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public int compareTo(Object o) {
        return 0;
    }

    public int trueCompareTo(Object o, String t) {
        return 0;
    }

    public int trueCompareTo(Object o) {
        return 0;
    }

    public boolean isTrump(String t) {
        return false;
    }

    public boolean sameSuit(Object o) {
        return false;
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
