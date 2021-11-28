import java.util.*
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.thread
import kotlin.concurrent.withLock

class BarberShop(private val seatCount: Int) {
    init {
        thread(block = { while (true) checkQueueSize() })
    }

    fun invariantBarberShop() {
        try { require(readyToService()) }
        catch (e: Exception) { println("Barber shop is not ready to service by default!") }

        try { require(getQueueLength() == 0) }
        catch (e: Exception) { println("Queue is not empty by default!") }
    }

    /**
     * Contract for checking the size of queue
     */
    private fun checkQueueSize() {
        try { require(customerQueue.size <= seatCount) }
        catch (e: Exception) {
            println("Size of customer's queue is more than count of seats available!")
        }
    }

    /**
     * Contract for checking if current customer is null or not
     */
    private fun isCurrentCustomerNull() {
        try { requireNotNull(currentCustomer) }
        catch (e: Exception) { println("Current customer must not be null at that moment!") }
    }

    private var currentCustomer: Customer? = null
    private val customerQueue: Queue<Customer> = LinkedList()
    private val barberShopString = "\n> BARBER SHOP\n|"

    private val lock = ReentrantLock()
    private val condition = lock.newCondition()

    fun getQueueLength() = lock.withLock {
        checkQueueSize()
        customerQueue.size
    }

    fun customersWaiting() = lock.withLock { !customerQueue.isEmpty() }

    fun havePlaceToWait() = lock.withLock { customerQueue.size < seatCount }

    fun readyToService() = lock.withLock { currentCustomer == null }

    fun serveCustomer(customer: Customer) = lock.withLock {
        currentCustomer = customer
        isCurrentCustomerNull()

        condition.signalAll()
    }

    fun cleanWorkplace() = lock.withLock { currentCustomer = null }

    fun addToQueue(customer: Customer) = lock.withLock {
        customerQueue.add(customer)
        checkQueueSize()
    }

    fun getFromQueue(): Customer = lock.withLock { customerQueue.remove() }

    fun getCustomer(): Customer = lock.withLock {
        while (readyToService()) {
            try { condition.await() }
            catch (e: InterruptedException) {
                println("$barberShopString Прервано ожидание клиентов!!!")
            }
        }

        isCurrentCustomerNull()
        return currentCustomer!!
    }
}
