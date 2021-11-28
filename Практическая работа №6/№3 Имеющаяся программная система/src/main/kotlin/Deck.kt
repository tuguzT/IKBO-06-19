import java.util.*
import kotlin.concurrent.thread

class Deck {
    var cards = Stack<Card>()
        private set

    init {
        val allCards = ArrayList<Card>().apply {
            for (rank in Rank.values()) for (suit in Suits.values())
                add(Card(rank, suit))
            shuffle()
        }

        try { require(allCards.size == Rank.values().size * Suits.values().size) }
        catch (e: Exception) { println("Deck initialization failed!") }

        for (card in allCards)
            cards.push(card)

        thread(block = { while (true) {
            try { require(size() <= Rank.values().size * Suits.values().size) }
            catch (e: Exception) { println("Deck must not contain more cards than was initialized!") }
        }})
    }

    fun draw(): Card? {
        if (isEmpty())
            return null

        val sizePrev = size()
        val card = cards.pop()

        try { require(sizePrev > size()) }
        catch (e: Exception) { println("Drawing failed!") }

        return card
    }

    fun isEmpty() = cards.empty()

    fun size(): Int = cards.size

    fun reinsert(trump: Card) {
        val sizePrev = size()
        cards.add(0, trump)

        try { require(sizePrev < size()) }
        catch (e: Exception) { println("Reinsertion failed!") }
    }

    override fun toString() =
        buildString {
            append("[Bottom]\n")
            for (card in cards)
                append("$card\n")
            append("[Top]\n")
        }
}
