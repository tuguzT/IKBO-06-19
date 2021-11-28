class Pair(card: Card) {
    private val attacker = card
    private var defender: Card? = null

    var completed = false
        private set

    fun response(defender: Card) {
        if (isValidDefender(defender)) {
            this.defender = defender
            completed = !completed
        }
        throw IllegalArgumentException("Invalid defender")
    }

    private fun isValidDefender(defender: Card): Boolean {
        try {
            if (defender.trueCompareTo(attacker) > 0)
                return true
        } catch (e: IllegalArgumentException) {
            return false
        }
        return false
    }

    fun fetchAllCards() = ArrayList<Card>().apply {
        add(attacker)
        if (completed)
            add(defender!!)
    }

    override fun toString() = buildString {
        append("{ ")
        for (card in fetchAllCards())
            append("$card ")
        append("}\n")
    }
}
