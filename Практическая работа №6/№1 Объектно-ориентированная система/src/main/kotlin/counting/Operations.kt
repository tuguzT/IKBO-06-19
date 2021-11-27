package counting

/**
 * [Operation] class responsible for summing
 * contents of two objects of [Input] type
 * @param first left argument of an [Operation]
 *              called [Add] as an object of [Input] type
 * @param second right argument of an [Operation]
 *               called [Add] as an object of [Input] type
 */
class Add(first: Input, second: Input) : Operation(first, second) {
    /**
     * Function which logic is responsible for calculation of an [Operation] called [Add]
     * @param lhs left calculated argument of an [Operation] called [Add]
     * @param rhs right calculated argument of an [Operation] called [Add]
     */
    override fun evaluate(lhs: Double, rhs: Double): Double {
        checkInput(lhs, rhs)
        val answer = lhs + rhs

        try { require(answer == listOf(lhs, rhs).sum()) }
        catch (e: Exception) { println("Evaluation of sum is wrong!") }
        isNaN(answer)

        output(this, answer)
        return answer
    }

    override fun toString() = "($first + $second)"
}

/**
 * [Operation] class responsible for subtracting
 * contents of two objects of [Input] type
 * @param first left argument of an [Operation]
 *              called [Subtract] as an object of [Input] type
 * @param second right argument of an [Operation]
 *               called [Subtract] as an object of [Input] type
 */
class Subtract(first: Input, second: Input) : Operation(first, second) {
    /**
     * Function which logic is responsible for calculation of an [Operation] called [Subtract]
     * @param lhs left calculated argument of an [Operation] called [Subtract]
     * @param rhs right calculated argument of an [Operation] called [Subtract]
     */
    override fun evaluate(lhs: Double, rhs: Double): Double {
        checkInput(lhs, rhs)
        val answer = lhs - rhs

        try { require(answer == listOf(rhs, -lhs).fold(0.0) { acc, value -> acc - value }) }
        catch (e: Exception) { println("Evaluation of subtraction is wrong!") }
        isNaN(answer)

        output(this, answer)
        return answer
    }

    override fun toString() = "($first - $second)"
}

/**
 * [Operation] class responsible for multiplying
 * contents of two objects of [Input] type
 * @param first left argument of an [Operation]
 *              called [Multiply] as an object of [Input] type
 * @param second right argument of an [Operation]
 *               called [Multiply] as an object of [Input] type
 */
class Multiply(first: Input, second: Input) : Operation(first, second) {
    /**
     * Function which logic is responsible for calculation of an [Operation] called [Multiply]
     * @param lhs left calculated argument of an [Operation] called [Multiply]
     * @param rhs right calculated argument of an [Operation] called [Multiply]
     */
    override fun evaluate(lhs: Double, rhs: Double): Double {
        checkInput(lhs, rhs)
        val answer = lhs * rhs

        try { require(answer == listOf(lhs, rhs).fold(1.0) { acc, value -> acc * value }) }
        catch (e: Exception) { println("Evaluation of multiplication is wrong!") }
        isNaN(answer)

        output(this, answer)
        return answer
    }

    override fun toString() = "$first * $second"
}

/**
 * [Operation] class responsible for dividing
 * contents of two objects of [Input] type
 * @param first left argument of an [Operation]
 *              called [Divide] as an object of [Input] type
 * @param second right argument of an [Operation]
 *               called [Divide] as an object of [Input] type
 */
class Divide(first: Input, second: Input) : Operation(first, second) {
    /**
     * Function which logic is responsible for calculation of an [Operation] called [Divide]
     * @param lhs left calculated argument of an [Operation] called [Divide]
     * @param rhs right calculated argument of an [Operation] called [Divide]
     */
    override fun evaluate(lhs: Double, rhs: Double): Double {
        checkInput(lhs, rhs)
        val answer = lhs / rhs

        try { require(answer != listOf(lhs, rhs).fold(1.0) { acc, value -> acc * value }) }
        catch (e: Exception) { println("Evaluation of division is wrong!") }
        isNaN(answer)

        output(this, answer)
        return answer
    }

    override fun toString() = "$first / $second"
}

/**
 * Function providing output of an [Operation]
 * @param operation object to which [answer] was calculated
 * @param answer calculated for an [Operation]
 */
private fun output(operation: Operation, answer: Double) {
    println("$operation = $answer")
}

/**
 * Pre-condition for checking input values
 */
private fun checkInput(lhs: Double, rhs: Double) {
    isNaN(lhs)
    isNaN(rhs)
}
