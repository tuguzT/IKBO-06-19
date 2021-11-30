import kotlin.concurrent.thread

class Field(card: Card) {
    private var pairs = ArrayList<Pair>()
    private var playedRanks = ArrayList<Rank>()

    var completed = false
        private set

    init {
        val initialPair = Pair(card)
        pairs.add(initialPair)

        val initialCardRank = card.rank
        playedRanks.add(initialCardRank)

        thread(block = { while (true) {
            try { require(pairs.isNotEmpty()) }
            catch (e: Exception) { println("List of pairs must not be empty!") }

            try { require(playedRanks.isNotEmpty()) }
            catch (e: Exception) { println("List of played ranks must not be empty!") }
        }})
    }

    fun attack(card: Card): Boolean =
        if (!anyOpenPairs() && isValidAttack(card)) {
            pairs.add(Pair(card))
            true
        } else false

    fun respond(card: Card): Boolean {
        return currentOpenPair()?.response(card) == null
    }

    private fun isValidAttack(card: Card): Boolean {
        val thisRank = card.rank

        for (rank in playedRanks)
            if (thisRank == rank) return true
        return false
    }

    private fun anyOpenPairs(): Boolean {
        for (pair in pairs)
            if (!pair.completed)
                return true
        return false
    }

    private fun currentOpenPair(): Pair? {
        var ret: Pair? = null
        for (pair in pairs)
            if (!pair.completed)
                ret = pair

        return ret
    }

    fun endField(): Boolean {
        completed = !completed
        return anyOpenPairs()
    }

    fun fetchAllCards(): ArrayList<Card> {
        val list = ArrayList<Card>().apply {
            for (pair in pairs)
                addAll(pair.fetchAllCards())
        }

        try { require(list.isNotEmpty()) }
        catch (e: Exception) { println("Generated list must not be empty!") }

        return list
    }

    override fun toString() = buildString {
        append("+++ Field +++\n\n")
        for (pair in pairs) append("$pair\n")
        append("+++ Field +++\n\n")
    }
}
