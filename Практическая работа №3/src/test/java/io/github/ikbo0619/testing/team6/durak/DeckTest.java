package io.github.ikbo0619.testing.team6.durak;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class DeckTest {
    @Test
    public void createANewDeck() {
        Deck deck = new Deck();
        Assertions.assertEquals(deck.size(), 36);
    }

    @Test
    public void draw() {
        Deck deck = new Deck();
        deck.draw();
        Assertions.assertEquals(deck.size(), 35);

        for (int i = 0; i < 35; i++)
            deck.draw();
        Assertions.assertDoesNotThrow(deck::draw);

        Assertions.assertNull(deck.draw());
        Assertions.assertTrue(deck.isEmpty());
    }

    @Test
    public void reinsert() {
        Deck deck = new Deck();
        deck.reinsert(new Card());

        Assertions.assertFalse(deck.isEmpty());
        Assertions.assertEquals(deck.size(), 37);
    }
}
