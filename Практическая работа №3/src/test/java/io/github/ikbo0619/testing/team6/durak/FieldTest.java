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

        Card defenseCard = new Card();
        field.respond(defenseCard);
        Assertions.assertTrue(field.fetchAllCards().isEmpty());
    }

    @Test
    public void toggleCompleted() {
        Field field = new Field();
        Assertions.assertFalse(field.isCompleted());

        field.toggleCompleted();
        Assertions.assertTrue(field.isCompleted());
    }
}
