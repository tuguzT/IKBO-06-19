import java.util.*

class CustomersGate(
    private val barberShop: BarberShop,
    private val frequency: Int,
) : () -> Unit {
    init {
        try { require(frequency > 0) }
        catch (e: Exception) { println("Frequency is less or equal to zero!") }
    }

    private val customersFactory = CustomersFactory()
    private val customersGateString = "\n> CUSTOMER'S GATE\n|"

    override fun invoke() {
        while (true) {
            try { require(frequency > 0) }
            catch (e: Exception) { println("Frequency is less or equal to zero!") }

            try { Thread.sleep(Random().nextInt(frequency).toLong()) }
            catch (e: InterruptedException) { println("$customersGateString Клиент неожиданно появился...") }

            val customer = customersFactory.getCustomer()
            println("$customersGateString Приходит ${customer.name}!")

            if (barberShop.customersWaiting()) {
                println("| ${customer.name} видит очередь из ${barberShop.getQueueLength()} человек(а).")

                if (barberShop.havePlaceToWait()) {
                    println("| ${customer.name} видит место, садится ждать.")
                    barberShop.addToQueue(customer)
                } else
                    println("| ${customer.name} видит, что мест нет, и уходит.")
            } else {
                if (barberShop.readyToService()) {
                    println("| ${customer.name} садится в кресло.")
                    barberShop.serveCustomer(customer)
                } else {
                    println("| ${customer.name} видит, что кто-то стрижётся, и садится в очередь.")
                    barberShop.addToQueue(customer)
                }
            }
        }
    }
}
