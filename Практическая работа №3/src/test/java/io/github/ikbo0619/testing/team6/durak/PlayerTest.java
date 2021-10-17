package io.github.ikbo0619.testing.team6.durak;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PlayerTest {

    @Test
    public void createPlayersWithDeckAndName() {
        Deck deck = new Deck();

        Player player1 = new Player(deck, "Player Vasya");
        Player player2 = new Player(deck, "Player Tema");

        Assertions.assertEquals("Player Vasya", player1.toString());
        Assertions.assertEquals("Player Tema", player2.toString());
    }

    @Test
    public void addCardToHand() {
        Deck deck = new Deck();

        Player player1 = new Player(deck, "Player Vasya");
        Player player2 = new Player(deck, "Player Tema");

        Assertions.assertEquals(6, player1.cardsInHand());

        player2.drawCards(3);
        Assertions.assertEquals(9, player2.cardsInHand());
    }

    @Test
    public void discardCardFromHand() {
        Deck deck = new Deck();

        Player player3 = new Player(deck, "Player Vasya1");
        Player player4 = new Player(deck, "Player Tema2");

        Assertions.assertEquals(6, player3.cardsInHand());
        player3.discard(player3.getCard(1));
        Assertions.assertEquals(5, player3.cardsInHand());

        player4.discard(player4.getCard(1));
        player4.discard(player4.getCard(1));
        player4.discard(player4.getCard(1));
        player4.drawCards(2);
        Assertions.assertEquals(5, player4.cardsInHand());
    }

    @Test
    public void checkVictory() {
        Deck deck = new Deck();
        while (deck.size()!=0) deck.draw();

        Player player5 = new Player(deck, "Player Tema3");

        Assertions.assertTrue(player5.victoryAchieved());
    }

    @Test
    public void checkAttacker() {
        Deck deck = new Deck();

        Player player6 = new Player(deck, "Player Tema4");

        player6.makeAttacker();

        Assertions.assertTrue(player6.isAttacker());
    }

    @Test
    public void checkSwitchRole() {
        Deck deck = new Deck();

        Player player6 = new Player(deck, "Player Tema4");

        player6.makeAttacker();
        Assertions.assertTrue(player6.isAttacker());
        player6.switchRole();
        Assertions.assertFalse(player6.isAttacker());

    }

    @Test
    public void getCardById() {
        Deck deck = new Deck();

        Player player6 = new Player(deck, "Player Tema4");

        Assertions.assertNotNull(player6.getCard(6));
        player6.discard(player6.getCard(6));
        Assertions.assertThrowsExactly(IndexOutOfBoundsException.class, () -> player6.getCard(6));
    }
}
