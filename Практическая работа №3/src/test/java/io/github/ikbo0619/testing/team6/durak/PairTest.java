package io.github.ikbo0619.testing.team6.durak;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class PairTest {
    @Test
    public void createANewPair() {
        Card card1 = new Card("7", "Diamonds");

        Pair pair = new Pair(card1);

        Assertions.assertEquals(card1, pair.getAttacker());
        Assertions.assertFalse(pair.isCompleted());
    }

    @Test
    public void checkNewDefender() {
        Card card1 = new Card("7", "Diamonds");
        Card card2 = new Card("9", "Hearts");

        Pair pair = new Pair(card1);
        pair.response(card2);

        Assertions.assertEquals(card2, pair.getDefender());
    }

    @Test
    public void isCardValidDefender() {
        Card card1 = new Card("8", "Diamonds");
        Card card2 = new Card("7", "Diamonds");
        Card card3 = new Card("9", "Diamonds");

        Pair pair = new Pair(card1);

        Durak durak = new Durak();

        Assertions.assertFalse(pair.isValidDefender(card2));
        Assertions.assertTrue(pair.isValidDefender(card3));
    }

    @Test
    public void fetchAllCardsIntoArrayList() {
        Card card1 = new Card("7", "Diamonds");
        Card card2 = new Card("9", "Hearts");

        Pair pair = new Pair(card1);

        Assertions.assertEquals(new ArrayList<Card>().add(card1),
                pair.isValidDefender(card2));

        pair.response(card2);

        List<Card> testList = new ArrayList<>();
        testList.add(card1);
        testList.add(card2);

        Assertions.assertEquals(testList, pair.fetchAllCards());
    }
}
