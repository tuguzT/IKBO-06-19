class Pair(card: Card) {
    private val attacker = card
    private var defender: Card? = null

    var completed = false
        private set

    fun response(defender: Card) {
        if (defender.trueCompareTo(attacker) > 0) {
            this.defender = defender
            completed = !completed
        }
    }

    fun fetchAllCards(): ArrayList<Card> {
        val list = ArrayList<Card>().apply {
            add(attacker)
            if (completed) {
                try { requireNotNull(defender) }
                catch (e: Exception) { println("Defender must not be null!") }

                add(defender!!)
            }
        }

        try { require(list.size <= 2) }
        catch (e: Exception) { println("List of fetched cards is invalid!") }

        return list
    }

    override fun toString() = buildString {
        append("{ ")
        for (card in fetchAllCards())
            append("$card ")
        append("}\n")
    }
}
