import kotlin.concurrent.thread

class Hand {
    var cards = ArrayList<Card>()
        private set

    init {
        thread(block = { while (true) {
            try { require(size() <= Rank.values().size * Suits.values().size) }
            catch (e: Exception) { println("Hand must not contain more cards than was initialized!") }
        }})
    }

    fun add(card: Card) {
        val sizePrev = size()
        cards.add(card)

        try { require(sizePrev < size()) }
        catch (e: Exception) { println("Card addition failed!") }
    }

    fun size(): Int = cards.size

    fun numberToDraw(): Int {
        val number = if (size() > 6) 0 else 6 - size()

        try { require(number >= 0) }
        catch (e: Exception) { println("Number of cards to draw must not be less than zero!") }

        try { require(number <= 6) }
        catch (e: Exception) { println("Number of cards to draw must not be more than 6!") }

        return number
    }

    fun getCardByIndex(index: Int): Card {
        try { require(index >= 0 && index < size()) }
        catch (e: Exception) { println("Invalid index of card to get!") }

        return cards[index]
    }

    fun useCardByIndex(index: Int): Card {
        try { require(index >= 0 && index < size()) }
        catch (e: Exception) { println("Invalid index of card to use!") }

        val sizePrev = size()
        val card = cards.removeAt(index)

        try { require(sizePrev > size()) }
        catch (e: Exception) { println("Card usage failed!") }

        return card
    }

    override fun toString() = buildString {
        for (c in cards) append("$c\n")
    }
}
