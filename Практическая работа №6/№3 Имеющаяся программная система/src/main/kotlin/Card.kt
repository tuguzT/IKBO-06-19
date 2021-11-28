import kotlin.concurrent.thread

enum class Rank(val value: Int) {
    C6(6), C7(7), C8(8), C9(9), C10(10),
    Jack(11), Queen(12), King(13), Ace(14),
}

enum class Suits { Hearts, Diamonds, Clubs, Spades }

class Card(val rank: Rank, val suits: Suits) : Comparable<Card> {
    init {
        thread(block = { while (true) {
            try { require(Rank.values().contains(rank)) }
            catch (e: Exception) { println("Card's 'rank' is invalid!") }

            try { require(rank.value >= 6) }
            catch (e: Exception) { println("Card's 'rank' value is too small for Durak game!") }

            try { require(rank.value <= 14) }
            catch (e: Exception) { println("Card's 'rank' value is too big for Durak game!") }

            try { require(Suits.values().contains(suits)) }
            catch (e: Exception) { println("Card's 'suits' is invalid!") }
        }})
    }

    override fun compareTo(other: Card): Int {
        val thisValue = rank.value
        val otherValue = other.rank.value

        return thisValue - otherValue
    }

    fun trueCompareTo(otherCard: Card): Int {
        val thisCardIsTrump = this.isTrump()
        val otherCardIsTrump = otherCard.isTrump()
        val valueDifference = this.compareTo(otherCard)

        return when {
            (thisCardIsTrump && otherCardIsTrump) || sameSuit(otherCard) -> valueDifference
            thisCardIsTrump -> 1
            otherCardIsTrump -> -1
            else -> 0
        }
    }

    private fun isTrump() = suits == Durak.TRUMP

    private fun sameSuit(otherCard: Card) = suits == otherCard.suits

    override fun toString() = "<$rank, $suits>"
}
