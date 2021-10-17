package io.github.ikbo0619.testing.team6.durak;

import java.util.Random;
import java.util.Scanner;

public class Durak {
    public static String TRUMP;
    private Player one;
    private Player two;
    private Deck deck;
    private int round;
    private Player attacker;
    private Player defender;
    private Field currentField;
    private boolean roundInitiated;

    public Scanner sc = new Scanner(System.in);
    public Random r = new Random();

    public Durak() {}

    public void run() {}

    public void setup() {}

    public void game() {}

    public boolean victoryAchieved() {
        return false;
    }

    public Player determineWinner() {
        return null;
    }

    public boolean round() {
        return false;
    }

    public void announceCardPlayed(Player p, Card c) {}

    public void turnPrompt(Player player) {}

    public int playerInput(Player p) {
        return 0;
    }

    public boolean defenderResponse(Field f) {
        return false;
    }

    public boolean attackerResponse(Field f) {
        return false;
    }

    public boolean whichAttacker() {
        return false;
    }

    public void setAttacker(Player p) {}

    public void setDefender(Player p) {}

    public void switchRoles() {}

    public Player getAttacker() {
        return null;
    }

    public Player getDefender() {
        return null;
    }

    public boolean roundInitiated() {
        return false;
    }
}
