package jmol.jasper.MonopolyBoard.Data;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MonopolyCardDeckTest {
    private Card firstCardOfTheDeck = new CardBuilder().build();
    private Card secondCardOfTheDeck = new CardBuilder().build();
    private Card thirdCardOfTheDeck = new CardBuilder().build();
    private Card cardFromAnotherDeck = new CardBuilder().build();
    private final int ALL_CARDS = 3;


    @Test
    void testDrawCard() {
        // GIVEN a deck with three cards.
        MonopolyCardDeck monopolyCardDeck = getDeckWithThreeCards();

        // WHEN we draw 2 cards.
        drawCards(monopolyCardDeck, 2);

        // THEN drawing a third card should not return null;
        assertTrue(monopolyCardDeck.drawCard()!= null);

        // AND drawing a fourth card should return null;
        assertEquals(null, monopolyCardDeck.drawCard());
    }

    @Test
    void testDiscardCards() {
        // GIVEN a deck with three cards.
        MonopolyCardDeck monopolyCardDeck = getDeckWithThreeCards();

        // WHEN we draw all cards.
        drawCards(monopolyCardDeck, ALL_CARDS);

        // THEN we should be able to discard all cards of the deck;
        assertTrue(monopolyCardDeck.discardCard(firstCardOfTheDeck));
        assertTrue(monopolyCardDeck.discardCard(secondCardOfTheDeck));
        assertTrue(monopolyCardDeck.discardCard(thirdCardOfTheDeck));
    }

    @Test
    void testDiscardSameCard() {
        // GIVEN a deck with three cards.
        MonopolyCardDeck monopolyCardDeck = getDeckWithThreeCards();

        // WHEN we draw all cards and discard the first card;
        drawCards(monopolyCardDeck, ALL_CARDS);
        monopolyCardDeck.discardCard(firstCardOfTheDeck);

        // THEN we should not be able to discard the first card again;
        assertFalse(monopolyCardDeck.discardCard(firstCardOfTheDeck));
    }

    @Test
    void testDiscardToFullDeck() {
        // GIVEN a deck with three cards.
        MonopolyCardDeck monopolyCardDeck = getDeckWithThreeCards();

        // WHEN we draw no cards.

        // THEN we should not be able to discard a card to the deck;
        assertFalse(monopolyCardDeck.discardCard(firstCardOfTheDeck));
    }

    @Test
    void testDiscardForeignCard() {
        // GIVEN a deck with three cards.
        MonopolyCardDeck monopolyCardDeck = getDeckWithThreeCards();

        // WHEN discarding all cards.
        discardAllCards(monopolyCardDeck);

        // THEN discarding a foreign card should not be possible.
        assertFalse(monopolyCardDeck.discardCard(cardFromAnotherDeck));
    }

    @Test
    void testAddDiscardedCardsToDeck() {
        // GIVEN a deck with three cards.
        MonopolyCardDeck monopolyCardDeck = getDeckWithThreeCards();

        // WHEN we draw, discard and reshuffle the cards.
        drawCards(monopolyCardDeck, ALL_CARDS);
        discardAllCards(monopolyCardDeck);
        monopolyCardDeck.putCardsBackInPlay();

        // THEN we should be able to draw three cards;
        assertTrue(monopolyCardDeck.drawCard()!= null);
        assertTrue(monopolyCardDeck.drawCard()!= null);
        assertTrue(monopolyCardDeck.drawCard()!= null);
    }

    @Test
    void testDoNotAddDiscardedCardsToDeck() {
        // GIVEN a deck with three cards.
        MonopolyCardDeck monopolyCardDeck = getDeckWithThreeCards();

        // WHEN we draw and reshuffle the cards, but not discard them.
        drawCards(monopolyCardDeck, ALL_CARDS);
        monopolyCardDeck.putCardsBackInPlay();

        // THEN drawing a card should return null;
        assertEquals(null, monopolyCardDeck.drawCard());
    }

    @Test
    void testShuffleCards() {
        // GIVEN a deck with three cards.
        MonopolyCardDeck monopolyCardDeck = getDeckWithThreeCards();

        // WHEN we draw on card and shuffle the deck.
        drawCards(monopolyCardDeck, 1);
        monopolyCardDeck.shuffleCards();

        // THEN we should be able to draw two more cards.
        assertTrue(monopolyCardDeck.drawCard() != null);
        assertTrue(monopolyCardDeck.drawCard() != null);
        assertEquals(null, monopolyCardDeck.drawCard());
    }

    private MonopolyCardDeck getDeckWithThreeCards() {
        return new MonopolyCardDeck(new Card[]{firstCardOfTheDeck, secondCardOfTheDeck, thirdCardOfTheDeck});
    }

    private void drawCards(MonopolyCardDeck monopolyCardDeck, int numberOfCards) {
        for (int i = 0; i < numberOfCards; i++) {
            monopolyCardDeck.drawCard();
        }
    }

    private void discardAllCards(MonopolyCardDeck monopolyCardDeck) {
        monopolyCardDeck.discardCard(firstCardOfTheDeck);
        monopolyCardDeck.discardCard(secondCardOfTheDeck);
        monopolyCardDeck.discardCard(thirdCardOfTheDeck);
    }
}
