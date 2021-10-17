package io.github.ikbo0619.testing.team6.durak;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class HandTest {
    @Test
    public void createANewHand() {
        Hand hand_empty = new Hand();
        Assertions.assertNotNull(hand_empty.getCards());

        Hand hand_sized = new Hand(3);
        Assertions.assertNotNull(hand_sized.getCardByIndex(0));
    }

    @Test
    public void checkHandCardsAccess() {
        int size = 3;
        Hand hand_sized = new Hand(size);
        Assertions.assertEquals(hand_sized.size(), size);

        Assertions.assertThrows(Exception.class, () -> hand_sized.useCardByIndex(size));
        Assertions.assertDoesNotThrow(() -> hand_sized.useCardByIndex(0));

        Assertions.assertDoesNotThrow(() -> hand_sized.remove(new Card()));
        Assertions.assertDoesNotThrow(() -> hand_sized.remove(new Card("8", "Diamonds")));

        Card card = new Card("King", "Clubs");
        hand_sized.add(card);
        Assertions.assertNotEquals(hand_sized.size(), size - 1);
        Assertions.assertDoesNotThrow(() -> hand_sized.remove(card));
    }

    @Test
    public void needsToDraw() {
        Hand hand_sized = new Hand(7);
        Assertions.assertTrue(hand_sized.needsToDraw());

        hand_sized.useCardByIndex(5);
        Assertions.assertFalse(hand_sized.needsToDraw());
    }

    @Test
    public void numberToDraw() {
        Hand hand_sized = new Hand(7);
        Assertions.assertEquals(hand_sized.numberToDraw(), 0);

        hand_sized.useCardByIndex(5);
        hand_sized.useCardByIndex(5);
        Assertions.assertEquals(hand_sized.numberToDraw(), 1);
    }
}
