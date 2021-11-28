import java.util.*

class Deck {
    var cards = Stack<Card>()
        private set

    init {
        val allCards = ArrayList<Card>().apply {
            for (rank in Rank.values()) for (suit in Suits.values())
                add(Card(rank, suit))
            shuffle()
        }

        for (card in allCards)
            cards.push(card)
    }

    fun draw(): Card? =
        if (isEmpty()) null
        else cards.pop()

    fun isEmpty() = cards.empty()

    fun size(): Int = cards.size

    fun reinsert(trump: Card?) = cards.add(0, trump)

    override fun toString() =
        buildString {
            append("[Bottom]\n")
            for (card in cards)
                append("$card\n")
            append("[Top]\n")
        }
}
