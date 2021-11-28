class Hand {
    var cards = ArrayList<Card>()
        private set

    fun add(card: Card) = cards.add(card)

    fun size(): Int = cards.size

    fun numberToDraw() =
        if (size() > 6) 0
        else 6 - size()

    fun getCardByIndex(index: Int) = cards[index]

    fun useCardByIndex(index: Int) = cards.removeAt(index)

    override fun toString() = buildString {
        for (c in cards) append("$c\n")
    }
}
