package counting

/**
 * Interface responsible for adding feature to input implementing class's content
 */
interface Input

/**
 * Class representing constant in a mathematical expression which can be entered
 * by hard-coding evaluate() function of the class [App]
 * @param constant name of a constant in a mathematical expression
 */
class Const(val constant: Double) : Input {
    init {
        isNaN(constant)
        isMatches(constant, shouldMatch = true,
            Regex("^(-?)(0|([1-9][0-9]*))(\\.[0-9]+)?$"))
    }
    override fun toString(): String = "$constant"
}

/**
 * Class representing variable in a mathematical expression which can be entered
 * by hard-coding evaluate() function of the class [App]
 * @param variable name of a variable in a mathematical expression
 */
class Variable(val variable: String) : Input {
    init {
        isMatches(variable, shouldMatch = false,
            Regex("^(-?)(0|([1-9][0-9]*))(\\.[0-9]+)?$"))

        try { require(variable.isNotBlank()) }
        catch (e: Exception) { println("Variable must not be blank!") }
    }
    override fun toString(): String = variable
}

/**
 * Contract for checking whether a variable matches given regex or not
 * @param shouldMatch variable to check whether a variable should match or not
 */
fun isMatches(any: Any, shouldMatch: Boolean, regex: Regex) {
    try { require(any.toString().matches(regex) && shouldMatch) }
    catch (e: Exception) { println("Param does not match given pattern!") }
}

/**
 * Contract for checking whether a given [number] is NaN
 * @param number to check
 */
fun isNaN(number: Double): Double {
    try { require(!number.isNaN()) }
    catch (e: Exception) {
        println("Evaluated number is NaN!")
        return 0.0
    }
    return number
}
