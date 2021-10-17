package io.github.ikbo0619.testing.team6.durak;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CardTest {
    @Test
    public void createANewCard() {
        Card card = new Card();

        Assertions.assertNotNull(card.getRank());
    }

    @Test
    public void createANewCardWithArguments() {
        Card card = new Card("6", "Hearts");

        Assertions.assertEquals("6", card.getRank());
        Assertions.assertEquals("Hearts", card.getSuit());
        Assertions.assertEquals("Red", card.getColor());
    }

    @Test
    public void checkCompare() {
        Card card1 = new Card("7", "Diamonds");
        Card card2 = new Card("9", "Diamonds");

        Assertions.assertEquals(-2, card1.compareTo(card2));
    }

    @Test
    public void checkTrueCompare() {
        Card card1 = new Card("7", "Diamonds");
        Card card2 = new Card("9", "Hearts");

        Card card3 = new Card("7", "Hearts");
        Card card4 = new Card("9", "Hearts");

        Assertions.assertEquals(-2, card3.trueCompareTo(card4, "Hearts"));
        Assertions.assertEquals(1, card1.trueCompareTo(card2, "Diamonds"));
        Assertions.assertEquals(-1, card1.trueCompareTo(card2, "Hearts"));
        Assertions.assertEquals(-2, card3.trueCompareTo(card4, "Diamonds"));
    }

    @Test
    public void cardIsTrump() {
        Card card = new Card("7", "Diamonds");

        Assertions.assertFalse(card.isTrump("Hearts"));
        Assertions.assertTrue(card.isTrump("Diamonds"));
    }

    @Test
    public void isOtherCardHasSameSuit() {
        Card card1 = new Card("7", "Diamonds");
        Card card2 = new Card("8", "Hearts");
        Card card3 = new Card("9", "Diamonds");

        Assertions.assertFalse(card1.sameSuit(card2));
        Assertions.assertTrue(card1.sameSuit(card3));
    }
}
