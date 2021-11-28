class Player(private val deck: Deck, private val name: String) {
    private val hand = Hand()
    var attacker = false

    init {
        drawCards(6)
    }

    private fun draw(deck: Deck) {
        if (!deck.isEmpty())
            hand.add(deck.draw()!!)
    }

    private fun drawCards(number: Int) {
        for (i in 0 until number)
            draw()
    }

    private fun draw() = draw(deck)

    fun takeCard(card: Card) = hand.add(card)

    fun cardsInHand() = hand.size()

    fun replenish() {
        val toDraw = hand.numberToDraw()
        drawCards(toDraw)
    }

    fun victoryAchieved() = hand.size() <= 0 && deck.isEmpty()

    fun switchRole() {
        attacker = !attacker
    }

    override fun toString() = "'$name'"

    fun getCard(index: Int) = hand.getCardByIndex(index - 1)

    fun useCard(index: Int) = hand.useCardByIndex(index - 1)

    fun cardList() = buildString {
        append("\n=== YOUR CARDS ===\n\n")

        hand.cards.forEachIndexed { index, card ->
            append("${index + 1} ~ $card\n")
        }

        append("\n")
    }
}
