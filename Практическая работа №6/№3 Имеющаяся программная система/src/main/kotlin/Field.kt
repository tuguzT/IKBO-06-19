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
    }

    fun attack(card: Card) {
        if (!anyOpenPairs() && isValidAttack(card))
            pairs.add(Pair(card))
        else
            throw IllegalArgumentException("You can't attack.")
    }

    fun respond(card: Card) {
        if (anyOpenPairs())
            currentOpenPair().response(card)
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

    private fun currentOpenPair(): Pair {
        var ret: Pair? = null

        for (pair in pairs)
            if (!pair.completed)
                ret = pair

        return ret ?: throw IllegalArgumentException("There are no open pairs.")
    }

    fun endField(): Boolean {
        completed = !completed
        return anyOpenPairs()
    }

    fun fetchAllCards() = ArrayList<Card>().apply {
        for (pair in pairs)
            addAll(pair.fetchAllCards())
    }

    override fun toString() = buildString {
        append("+++ Field +++\n\n")

        for (pair in pairs) append("$pair\n")

        append("+++ Field +++\n\n")
    }
}
