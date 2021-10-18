package io.github.ikbo0619.testing.team6.durak;

import java.util.ArrayList;
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

    public Durak() {
        run();
    }

    public void run() {
        boolean running = true;

        while (running) {
            setup();
            game();
            System.out.println("The game has ended.");
            System.out.println("Play again? y/n");

            boolean validResponse = false;
            while (!validResponse) {
                String response = sc.nextLine();
                if (response.equals("y")) {
                    validResponse = true;
                    running = true;
                } else if (response.equals("n")) {
                    validResponse = true;
                    running = false;
                } else {
                    validResponse = false;
                }
            }
        }
    }

    public void setup() {
        System.out.println("Welcome to Durak!");

        System.out.println("\nEnter the name of Player 1...\n");
        String oneName = sc.nextLine();
        System.out.println("\nEnter the name of Player 2...\n");
        String twoName = sc.nextLine();

        System.out.println("\nPlease wait!\n");
        System.out.println("Creating game...\n");

        System.out.println("Shuffling the cards...\n");
        deck = new Deck();

        System.out.println("Dealing the cards...\n");
        one = new Player(deck, oneName);
        two = new Player(deck, twoName);

        System.out.println("Determining trump card...\n");
        Card trumpCard = deck.draw();
        String trumpSuit = trumpCard.getSuit();
        TRUMP = trumpSuit;

        System.out.println("The trump is: " + TRUMP + "!\n");

        System.out.println("Reinserting trump card...\n");
        deck.reinsert(trumpCard);

        System.out.println("Resetting round count...\n");
        round = 1;

        System.out.println("The game is ready.\n");
    }

    public void game() {
        System.out.println("Determining initial attacker...\n");
        if (r.nextInt(2) < 1) {
            setAttacker(one);
            setDefender(two);
        } else {
            setAttacker(two);
            setDefender(one);
        }



        System.out.println("The initial attacker is: " + attacker + ".");
        System.out.println("The initial defender is: " + defender + ".\n\n");

        boolean gameOver = false;

        while (!gameOver) {

            boolean thisRound = round();

            if (victoryAchieved()) {
                gameOver = true;
            } else {
                attacker.replenish();
                defender.replenish();
                round++;
                if (thisRound) {
                } else {
                    switchRoles();
                }
            }
        }

        System.out.println("Game over!\n");
        System.out.println("The winner is " + determineWinner() + "!\n");
        return;
    }

    public boolean victoryAchieved() {
        return ((one.victoryAchieved() || two.victoryAchieved()));
    }

    public Player determineWinner() {
        if (victoryAchieved()) {
            if (one.victoryAchieved()) {
                return one;
            } else {
                return two;
            }
        } else {
            throw new IllegalArgumentException();
        }
    }

    public boolean round() {
        String roundName = "ROUND " + round;
        String headerLine = "==================== " + roundName + " ====================" + "\n";
        String headerContent = "Attacker: " + attacker + " | " + "Defender: " + defender + "\n";
        String header = "\n\n\n" + headerLine + headerContent + headerLine + "\n\n\n";

        roundInitiated = false;

        System.out.println(header);
        System.out.println(roundName + " has begun!\n");

        System.out.println(attacker + ", initiate the attack!");

        int initialAttack = playerInput(attacker);
        Card initialAttackCard = attacker.useCard(initialAttack);
        announceCardPlayed(attacker, initialAttackCard);

        if (victoryAchieved()) {
            return true;
        }

        Field roundField = new Field(initialAttackCard);
        currentField = roundField;
        roundInitiated = true;

        while (!roundField.isCompleted()) {
            boolean defenderTurn = defenderResponse(roundField);
            if (defenderTurn || victoryAchieved()) {
                roundInitiated = false;
                currentField = null;
                return false;
            }

            boolean attackerTurn = attackerResponse(roundField);
            if (attackerTurn || victoryAchieved()) {
                roundInitiated = false;
                currentField = null;
                return true;
            }
        }

        return true;
    }

    public void announceCardPlayed(Player p, Card c) {
        if (p.isAttacker()) {
            System.out.println("\n\n" + p + " has played " + c + ", initiating a new pair!");
        } else {
            System.out.println("\n\n" + p + " has played " + c + " in response!");
        }
    }

    public void turnPrompt(Player player) {
        boolean isAttacker = player.isAttacker();

        String prompt = new String("\n\n"); // StartHeaderline + Precontent + Content + Tail + EndHeaderline
        String promptStartLine = "\n============ PROMPT ============\n\n";
        String promptEndLine = "\n========== END PROMPT ==========\n\n";
        String preContent = new String("CURRENT TRUMP: " + TRUMP + "\n\n");
        preContent += "CARDS REMAINING IN DECK: " + deck.size() + "\n\n";
        preContent += "CARDS REMAINING IN HAND: " + player.cardsInHand() + "\n\n";
        String fieldString;
        String content = new String();
        String tail = new String("=== MESSAGE ===\n\n");

        content += player.cardList();
        content += "=== OTHER OPTIONS ===\n\n";

        if (isAttacker) {
            if (roundInitiated) {
                preContent += "# ATTACK CONTINUATION #\n\n";
                content += "0 | Beaten\n\n";
            } else {
                preContent += "# ATTACK INITIATION #\n\n";
                content += "<none>\n\n";
            }
            tail += player + ", you're attacking!\n";
        } else {
            preContent += "# DEFENSE #\n\n";
            content += "0 | Take\n\n";
            tail += player + ", you're defending!\n";
        }

        tail += "Make your move by hitting the corresponding key.\n";

        if (currentField == null) {
            fieldString = "<Empty Field>\n\n";
        } else {
            fieldString = "" + currentField;
        }

        prompt += promptStartLine + fieldString + preContent + content + tail + promptEndLine;

        System.out.println(prompt);
    }

    public int playerInput(Player p) {
        boolean isAttacker = p.isAttacker();

        turnPrompt(p);

        int playerSelection = -1;
        boolean properInput = false;
        while (!properInput) {
            playerSelection = sc.nextInt();
            if (isAttacker) {
                if (roundInitiated) {
                    properInput = ((playerSelection >= 0) && (playerSelection <= p.cardsInHand()));
                } else {
                    properInput = ((playerSelection >= 1) && (playerSelection <= p.cardsInHand()));
                }
            } else {
                properInput = ((playerSelection >= 0) && (playerSelection <= p.cardsInHand()));
            }
            if (!properInput) {
                System.out.println("Invalid input. Please enter an acceptable value.");
            }
        }

        return playerSelection;
    }

    public boolean defenderResponse(Field f) {
        int defenderResponse = -1;
        boolean properDefenderResponse = false;
        while (!properDefenderResponse) {
            try {
                defenderResponse = playerInput(defender);
                if (defenderResponse != 0) {
                    Card defenderResponseCard = defender.getCard(defenderResponse);
                    f.respond(defenderResponseCard);
                    properDefenderResponse = true;
                    defender.useCard(defenderResponse);
                    announceCardPlayed(defender, defenderResponseCard);
                    return false;
                } else {
                    properDefenderResponse = true;
                    System.out.println("\n\n" + defender + " has chosen to take all cards in the field and end the round!");
                    ArrayList<Card> takenCards = f.fetchAllCards();
                    for (Card card : takenCards) {
                        defender.takeCard(card);
                    }
                    f.endField();
                    return true;
                }
            } catch (IllegalArgumentException e) {
                System.out.println("\n\nInvalid defender!");
                properDefenderResponse = false;
            }
        }
        return true;
    }

    public boolean attackerResponse(Field f) {
        int attackerResponse = -1;
        boolean properAttackerResponse = false;
        while (!properAttackerResponse) {
            try {
                attackerResponse = playerInput(attacker);
                if (attackerResponse != 0) {
                    Card attackerResponseCard = attacker.getCard(attackerResponse);
                    f.attack(attackerResponseCard);
                    properAttackerResponse = true;
                    attacker.useCard(attackerResponse);
                    announceCardPlayed(attacker, attackerResponseCard);
                    return false;
                } else {
                    System.out.println("\n\n" + attacker + " has chosen to end the round!");
                    properAttackerResponse = true;
                    f.endField();
                    return true;
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid attack card!");
                properAttackerResponse = false;
            }
        }
        return true;
    }

    public boolean whichAttacker() {
        return one.isAttacker();
    }

    public void setAttacker(Player p) {
        attacker = p;
        p.makeAttacker();
    }

    public void setDefender(Player p) {
        defender = p;
        p.makeDefender();
    }

    public void switchRoles() {
        Player temp = attacker;
        attacker = defender;
        defender = temp;
        one.switchRole();
        two.switchRole();
    }

    public Player getAttacker() {
        return attacker;
    }

    public Player getDefender() {
        return defender;
    }

    public boolean roundInitiated() {
        return roundInitiated;
    }
}
