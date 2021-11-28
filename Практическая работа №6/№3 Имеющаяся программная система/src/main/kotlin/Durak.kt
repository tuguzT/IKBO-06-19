import java.util.*
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract

fun main() {
    Durak().invoke()
}

class Durak : () -> Unit {
    companion object {
        lateinit var TRUMP: Suits
    }
    private lateinit var one: Player
    private lateinit var two: Player

    private lateinit var deck: Deck
    private var round = 0
    private lateinit var attacker: Player
    private lateinit var defender: Player
    private var currentField: Field? = null
    private var roundInitiated = false

    override fun invoke() {
        var running = true

        while (running) {
            setup()

            game()
            println("The game has ended.")

            println("Play again? y/n")

            var validResponse = false
            while (!validResponse) {
                when (input(false)) {
                    "y" -> {
                        validResponse = true
                        running = true
                    }
                    "n" -> {
                        validResponse = true
                        running = false
                    }
                }
            }
        }
    }

    private fun setup() {
        println("Welcome to Durak!")

        println("\nEnter the name of Player 1...")
        val oneName = input(false)

        println("\nEnter the name of Player 2...")
        val twoName = input(false)

        println("\nPlease wait!\n" +
                "Creating game...\n" +
                "Shuffling the cards...")
        deck = Deck()

        println("Dealing the cards...\n")
        one = Player(deck, oneName)
        two = Player(deck, twoName)

        println("Determining trump card...")
        val trumpCard: Card? = deck.draw()
        val trumpSuit = trumpCard!!.suits
        TRUMP = trumpSuit

        println("The trump is: $TRUMP!\n" +
                "Reinserting trump card...\n")
        deck.reinsert(trumpCard)

        println("Resetting round count...")
        round = 1

        println("The game is ready.")
    }

    private fun game() {
        println("Determining initial attacker...\n")

        val oneToAttack = Random().nextBoolean()
        setPlayer(toAttack = oneToAttack, one)
        setPlayer(toAttack = !oneToAttack, two)

        println("The initial attacker is: $attacker.\n" +
                "The initial defender is: $defender.")
        var gameOver = false

        while (!gameOver) {
            val thisRound = round()

            if (victoryAchieved())
                gameOver = true
            else {
                attacker.replenish()
                defender.replenish()
                round++
                if (!thisRound) switchRoles()
            }
        }

        println("Game over!\n" +
                "The winner is ${determineWinner()}!\n")
    }

    private fun victoryAchieved() = one.victoryAchieved() || two.victoryAchieved()

    private fun determineWinner() =
        if (one.victoryAchieved()) one
        else two

    private fun round(): Boolean {
        val roundName = "ROUND $round"
        val headerLine = "==================== $roundName ====================\n"
        val headerContent = "Attacker: $attacker | Defender: $defender\n"
        val header = "\n\n$headerLine$headerContent$headerLine\n"

        roundInitiated = false

        println("$header$roundName has begun!\n" +
                "${attacker}, initiate the attack!")

        val initialAttack = playerInput(attacker)
        val initialAttackCard = attacker.useCard(initialAttack)
        announceCardPlayed(attacker, initialAttackCard)

        if (victoryAchieved())
            return true

        val roundField = Field(initialAttackCard)
        currentField = roundField
        roundInitiated = true

        while (!roundField.completed) {
            val defenderTurn = response(isAttacker = false, roundField)
            if (defenderTurn || victoryAchieved()) {
                roundInitiated = false
                currentField = null
                return false
            }

            val attackerTurn = response(isAttacker = true, roundField)
            if (attackerTurn || victoryAchieved()) {
                roundInitiated = false
                currentField = null
                return true
            }
        }
        return true
    }

    private fun announceCardPlayed(player: Player, card: Card) = println(
        "\n$player has played $card" +
                if (!player.attacker) " in response!"
                else ", initiating a new pair!"
    )

    private fun turnPrompt(player: Player) {
        var preContent = "CURRENT TRUMP: $TRUMP\n" +
                "CARDS REMAINING IN DECK: ${deck.size()}\n" +
                "CARDS REMAINING IN HAND: ${player.cardsInHand()}\n\n"

        var tail = "=== MESSAGE ===\n\n"
        var content = "${player.cardList()}=== OTHER OPTIONS ===\n\n"

        if (player.attacker) {
            if (roundInitiated) {
                preContent += "# ATTACK CONTINUATION #\n"
                content += "0 | Beaten\n\n"
            } else {
                preContent += "# ATTACK INITIATION #\n"
                content += "<none>\n\n"
            }
            tail += "${player}, you're attacking!\n"
        } else {
            preContent += "# DEFENSE #\n"
            content += "0 | Take\n\n"
            tail += "${player}, you're defending!\n"
        }
        tail += "Make your move by hitting the corresponding key.\n"

        val fieldString =
            if (currentField == null) "<Empty Field>\n\n"
            else "$currentField"

        println("\n============ PROMPT ============\n\n" +
                "$fieldString$preContent$content$tail" +
                "\n========== END PROMPT ==========\n")
    }

    private fun playerInput(player: Player): Int {
        val isAttacker = player.attacker

        turnPrompt(player)
        var playerSelection = -1
        var properInput = false

        while (!properInput) {
            playerSelection = input(true).toInt()

            properInput =
                playerSelection <= player.cardsInHand() &&
                playerSelection >= if (!isAttacker || (isAttacker && roundInitiated))
                    0 else 1

            if (!properInput)
                print("Invalid input.\nPlease enter an acceptable value: ")
        }
        return playerSelection
    }

    private fun input(isInt: Boolean): String {
        val input = readln()

        try { validateInput(isInt, input) }
        catch (e: Exception) {
            print("""
                $e
                Please enter an acceptable value: 
                """.trimIndent())
            return input(isInt)
        }

        return input
    }

    /**
     * Contract for validating input
     */
    @OptIn(ExperimentalContracts::class)
    private fun validateInput(isInt: Boolean, input: String?) {
        contract { returns() implies (input != null) }

        if (input.isNullOrBlank())
            throw IllegalArgumentException("Undefined input...")

        if (isInt && !input.matches(Regex("^([+-]?[1-9]\\d*|0)\$")))
            throw IllegalArgumentException("Input is not an integer!")

        if (!isInt && input.matches(Regex("^([+-]?[1-9]\\d*|0)\$")))
            throw IllegalArgumentException("Input must not be an integer!")
    }

    private fun response(isAttacker: Boolean, field: Field): Boolean {
        val string = if (isAttacker) "attack" else "defend"

        val properResponse = false
        while (!properResponse) {
            try {
                val player = if (isAttacker) attacker else defender
                val response = playerInput(player)

                return if (response != 0) {
                    val responseCard = player.getCard(response)
                    field.apply {
                        if (isAttacker) attack(responseCard)
                        else respond(responseCard)
                    }
                    player.useCard(response)
                    announceCardPlayed(player, responseCard)
                    false
                } else {
                    print("\n$player has chosen to end the round!")
                    if (!isAttacker) {
                        val takenCards = field.fetchAllCards()
                        for (card in takenCards)
                            player.takeCard(card)
                    }
                    field.endField()
                    true
                }
            } catch (e: IllegalArgumentException) { println("\n\nInvalid $string card!") }
        }
        return true
    }

    private fun setPlayer(toAttack: Boolean, player: Player) {
        if (toAttack) attacker = player
        else defender = player

        player.attacker = toAttack
    }

    private fun switchRoles() {
        attacker = defender.also {
            defender = attacker
        }
        one.switchRole()
        two.switchRole()
    }
}
