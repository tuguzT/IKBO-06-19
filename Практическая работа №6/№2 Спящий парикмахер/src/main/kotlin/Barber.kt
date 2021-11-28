class Barber(private val barberShop: BarberShop) : () -> Unit {
    var dayPayment: Int = 0
        private set
    private val barberString = "\n> BARBER\n|"

    init {
        try { require(dayPayment == 0) }
        catch (e: Exception) { println("Variable 'dayPayment' must not be equal to zero!") }
    }

    private fun invariantPayment() {
        try { require(dayPayment > 0) }
        catch (e: Exception) { println("Variable 'dayPayment' must be more than zero!") }
    }

    override fun invoke() {
        println("$barberString Парикмахер спит в ожидании клиентов!")

        while (true) {
            val customer = barberShop.getCustomer()

            println("$barberString Парикмахер работает..."
                    + "\n| Причёска: ${customer.hairstyle}"
                    + "\n| Клиент: ${customer.name}"
            )

            try { Thread.sleep(customer.hairstyle.time) }
            catch (e: InterruptedException) { println("$barberString Не достригли бедолагу!") }

            val payment = customer.hairstyle.price
            println("$barberString Парикмахер заработал $$payment")

            if (dayPayment != 0)
                println("| Парикмахер за день успел заработать $${dayPayment + payment}")
            dayPayment += payment

            if (barberShop.customersWaiting()) {
                println("$barberString Парикмахер берёт следующего клиента из очереди:")
                val customerFromQueue = barberShop.getFromQueue()

                barberShop.serveCustomer(customerFromQueue)
            } else {
                println("$barberString Никого нет, парикмахер чистит место и спит")
                barberShop.cleanWorkplace()
            }

            invariantPayment()
        }
    }
}
