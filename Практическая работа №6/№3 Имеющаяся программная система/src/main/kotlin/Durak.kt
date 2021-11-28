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
            while (true) when (input(false)) {
                "n" -> { running = false; break }
                "y" -> break
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
        val trumpCard = deck.draw()
        TRUMP = trumpCard!!.suits

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

        try { require(one.attacker != two.attacker) }
        catch (e: Exception) { println("Both players cannot share the same role!") }

        println("The initial attacker is: $attacker.\n" +
                "The initial defender is: $defender.")

        while (true) {
            val thisRound = round()

            if (victoryAchieved()) break
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

        val initialAttackCard = attacker.useCard(playerInput(attacker))
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
        while (true) {
            val playerSelection = input(true).toInt()

            val properInput =
                playerSelection <= player.cardsInHand() &&
                playerSelection >= if (!isAttacker || (isAttacker && roundInitiated))
                    0 else 1

            if (properInput) return playerSelection
            else print("Invalid input.\nPlease enter an acceptable value: ")
        }
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

        val matches = input.matches(Regex("^([+-]?[1-9]\\d*|0)\$"))

        if (isInt && !matches)
            throw IllegalArgumentException("Input is not an integer!")
        if (!isInt && matches)
            throw IllegalArgumentException("Input must not be an integer!")
    }

    private fun response(isAttacker: Boolean, field: Field): Boolean {
        val string = if (isAttacker) "attack" else "defend"

        while (true) {
            val player = if (isAttacker) attacker else defender
            val response = playerInput(player)

            if (response != 0) {
                val responseCard = player.getCard(response)
                try { require(
                    if (isAttacker) field.attack(responseCard)
                    else field.respond(responseCard)
                )}
                catch (e: Exception) {
                    println("\nInvalid $string card!")
                    continue
                }
                player.useCard(response)
                announceCardPlayed(player, responseCard)
                return false
            } else {
                print("\n$player has chosen to end the round!")
                if (!isAttacker) {
                    val takenCards = field.fetchAllCards()
                    for (card in takenCards) player.takeCard(card)
                }
                field.endField()
                return true
            }
        }
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
