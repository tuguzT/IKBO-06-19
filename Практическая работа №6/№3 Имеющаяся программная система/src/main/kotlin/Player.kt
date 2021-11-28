class Player(private val deck: Deck, private val name: String) {
    private val hand = Hand()
    var attacker = false

    init { drawCards(6) }

    private fun draw(deck: Deck) {
        if (!deck.isEmpty())
            hand.add(deck.draw()!!)
    }

    private fun drawCards(number: Int) {
        try { require(number >= 0 && number <= Rank.values().size * Suits.values().size) }
        catch (e: Exception) { println("Invalid number of cards to draw!") }

        for (i in 0 until number)
            draw(deck)
    }

    fun cardsInHand() = hand.size()

    fun takeCard(card: Card) = hand.add(card)

    fun replenish() = drawCards(hand.numberToDraw())

    fun victoryAchieved() = cardsInHand() <= 0 && deck.isEmpty()

    fun switchRole() {
        attacker = !attacker
    }

    fun getCard(index: Int): Card {
        try { require(index > 0 && index <= cardsInHand()) }
        catch (e: Exception) { println("Invalid index of card to get!") }

        return hand.getCardByIndex(index - 1)
    }

    fun useCard(index: Int): Card {
        try { require(index > 0 && index <= cardsInHand()) }
        catch (e: Exception) { println("Invalid index of card to use!") }

        return hand.useCardByIndex(index - 1)
    }

    fun cardList() = buildString {
        append("\n=== YOUR CARDS ===\n\n")
        hand.cards.forEachIndexed { index, card ->
            append("${index + 1} ~ $card\n")
        }
        append("\n")
    }

    override fun toString() = "'$name'"
}
