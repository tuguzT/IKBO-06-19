package io.github.ikbo0619.testing.team6.durak;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class FieldTest {
    @Test
    public void notCompletedOnCreation() {
        Field field = new Field();
        Assertions.assertFalse(field.isCompleted());

        Card card = new Card();
        field = new Field(card);
        Assertions.assertFalse(field.isCompleted());
    }

    @Test
    public void fetchAllCards() {
        Field field = new Field();
        Assertions.assertTrue(field.fetchAllCards().isEmpty());

        Card card = new Card();
        field.respond(card);
        Assertions.assertTrue(field.fetchAllCards().isEmpty());
    }

    @Test
    public void toggleCompleted() {
        Field field = new Field();
        Assertions.assertFalse(field.isCompleted());

        field.toggleCompleted();
        Assertions.assertTrue(field.isCompleted());
    }

    @Test
    public void responseWithCard() {
        Card attackCard = new Card("7", "Hearts");
        Card defenseCard = new Card("7", "Clubs");

        Field field = new Field(attackCard);
        Assertions.assertDoesNotThrow(() -> field.isValidAttack(attackCard));
        Assertions.assertDoesNotThrow(() -> field.isValidAttack(defenseCard));
        Assertions.assertThrows(IllegalArgumentException.class, () -> field.respond(defenseCard));
    }

    @Test
    public void attackWithCard() {
        Card attackCard = new Card("7", "Hearts");
        Card defenseCard = new Card("7", "Clubs");

        Field field = new Field(attackCard);
        Assertions.assertDoesNotThrow(() -> field.isValidAttack(attackCard));
        Assertions.assertDoesNotThrow(() -> field.isValidAttack(defenseCard));
        Assertions.assertThrows(IllegalArgumentException.class, () -> field.attack(attackCard));
    }

    @Test
    public void endField() {
        Field field = new Field(new Card("9", "Hearts"));

        Assertions.assertDoesNotThrow(() -> field.respond(new Card("10", "Hearts")));
        Assertions.assertFalse(field.endField());
        Assertions.assertTrue(field.isCompleted());

        Field newField = new Field(new Card("6", "Diamonds"));
        Assertions.assertThrows(IllegalArgumentException.class, () -> newField.attack(new Card("1", "Hearts")));
        Assertions.assertDoesNotThrow(() -> newField.respond(new Card("Ace", "Diamonds")));
        Assertions.assertFalse(newField.endField());
        Assertions.assertTrue(newField.isCompleted());
    }
}
