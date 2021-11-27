import java.lang.IllegalArgumentException
import kotlin.concurrent.thread
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract

class App : () -> Unit {
    override fun invoke() {
        print("\nВведите количество стульев в приемной: ")
        val seatCount = input()

        print("\nВведите примерную частоту, с которой клиенты приходят стричься: ")
        val frequency = input() * 1_000

        val barberShop = BarberShop(seatCount)
        barberShop.invalidateBarberShop()

        val barber = Barber(barberShop)
        val customersGate = CustomersGate(barberShop, frequency)

        thread(block = barber)
        thread(block = customersGate)
    }

    private fun input(): Int {
        val input = readln()

        try { validateInput(input) }
        catch (e: Exception) {
            print("""
                Неверный ввод – $e
                Введите число: 
                """.trimIndent())
            return input()
        }

        try { require(input.toInt() != 0) }
        catch (e: Exception) { println("Input number must not be zero!") }

        return input.toInt()
    }

    /**
     * Contract for validating input
     */
    @OptIn(ExperimentalContracts::class)
    private fun validateInput(input: String?) {
        contract { returns() implies (input != null) }

        if (input.isNullOrBlank())
            throw IllegalArgumentException("Неопределённый ввод...")

        if (!input.matches(Regex("^[-+]?[1-9]\\d*\$")))
            throw IllegalArgumentException("Введённая строка не является целым числом!")
    }
}

fun main() {
    println(
        """
        Данная программа является решением проблемы спящего парикмахера,
        у которого есть одно рабочее место и приёмная с несколькими стульями.
        """.trimIndent()
    )
    App().invoke()
}
