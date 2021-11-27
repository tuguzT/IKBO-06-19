import counting.*
import java.lang.IllegalArgumentException
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract

/**
 * Class that includes logic necessary for program to work
 */
class App {
    /**
     * Recursive function that evaluates result
     * of a hard-coded mathematical expression
     */
    fun evaluate(expression: Operation) {
        try { require(expression.toString() == "((x * x - 2.0 * x) + 1.0 / 5.0)") }
        catch (e: Exception) { println("Expression does not match!") }

        val input = readln()

        try { validateInput(input) }
        catch (e: Exception) {
            println("""
                Неверный ввод – $e
                Введите число:
                """.trimIndent())
            evaluate(expression)
            return
        }

        val x = input.toDouble()
        val answer = expression.evaluate(x)

        try { validateOutput(answer) }
        catch (e: Exception) {
            println("Неверный вывод – $e")
            return
        }

        try { require(answer == ((x * x - 2 * x) + (1.0 / 5.0))) }
        catch (e: Exception) { println("Evaluation of division is wrong!") }

        println("\nОтвет: $answer")
    }

    /**
     * Contract for validating input
     */
    @OptIn(ExperimentalContracts::class)
    private fun validateInput(input: String?) {
        contract { returns() implies (input != null) }

        if (input.isNullOrBlank())
            throw IllegalArgumentException("Неопределённый ввод...")

        if (!input.matches(Regex("^(-?)(0|([1-9][0-9]*))(\\.[0-9]+)?$")))
            throw IllegalArgumentException("Введённая строка не является числом!")
    }

    /**
     * Contract for validating output
     */
    @OptIn(ExperimentalContracts::class)
    private fun validateOutput(output: Any?) {
        contract { returns() implies (output != null) }

        if (output.toString().isBlank()) {
            throw IllegalArgumentException("Неопределённый вывод...")
        }
        if (!output.toString().matches(Regex("^(-?)(0|([1-9][0-9]*))(\\.[0-9]+)?$")))
            throw IllegalArgumentException("Высчитанный ответ не является числом!")
    }
}

/**
 * Entry point of the program
 */
fun main() {
    val variable = Variable("x")

    try { require(variable.variable == "x") }
    catch (e: Exception) {
        println("Variable is not 'x', but ${variable.variable}!")
    }

    val expression = Add(
        Subtract(
            Multiply(
                variable,
                variable
            ),
            Multiply(
                Const(2.0),
                variable
            )
        ),
        Divide(
            Const(1.0),
            Const(5.0)
        )
    )

    println(
        """
        Данная программа по входному числу высчитывает
        значение функции ${expression},
        где ${variable.variable} - само входное значение.
        
        Введите значение ${variable.variable}: 
        """.trimIndent()
    )
    App().evaluate(expression)
}
