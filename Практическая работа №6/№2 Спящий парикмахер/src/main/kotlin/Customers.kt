import kotlin.concurrent.thread

enum class HairStyle(val price: Int, val time: Long) {
    Iroquois(25, 3000),
    Polubox(10, 1800),
    Kotovsky(5, 1000),
    Gorshok(15, 2000),
    Afro(50, 5000),
    Dreads(40, 4500),
}

data class Customer(val name: String, val hairstyle: HairStyle) {
    init {
        thread(block = { while (true) {
            invariantHairStyle(hairstyle)

            try { require(name.isNotBlank()) }
            catch (e: Exception) { println("Variable 'name' must not be blank!") }
        }})
    }
}

class CustomersFactory {
    init {
        thread(block = { while (true) {
            try { require(names.isNotEmpty()) }
            catch (e: Exception) { println("List of 'names' must not be empty!") }

            try { require(patronymics.isNotEmpty()) }
            catch (e: Exception) { println("List of 'patronymics' must not be empty!") }

            try { require(hairstyles.isNotEmpty()) }
            catch (e: Exception) { println("List of 'hairstyles' must not be empty!") }
        }})
    }

    companion object {
        val names = listOf(
            "Иван", "Василий", "Фёдор", "Макар", "Семён", "Пафнутий", "Пётр"
        )
        val patronymics = listOf(
            "Альфредович", "Поликарпович", "Егорович", "Назарович", "Фомич", "Никитич"
        )
        val hairstyles = HairStyle.values()
    }

    fun getCustomer(): Customer {
        val person = Pair(names.random(), patronymics.random())

        try { require(person.first.isNotBlank()) }
        catch (e: Exception) { println("Variable 'person.first' must not be blank!") }

        try { require(person.second.isNotBlank()) }
        catch (e: Exception) { println("Variable 'person.second' must not be blank!") }

        val hairstyle = hairstyles.random()
        invariantHairStyle(hairstyle)

        return Customer("${person.first} ${person.second}", hairstyle)
    }
}

fun invariantHairStyle(hairstyle: HairStyle) {
    try { require(hairstyle.toString().isNotBlank()) }
    catch (e: Exception) { println("Variable 'hairstyle' must not be blank!") }

    try { require(hairstyle.time > 0) }
    catch (e: Exception) { println("Variable 'hairstyle.time' is less or equal to zero!") }

    try { require(hairstyle.price > 0) }
    catch (e: Exception) { println("Variable 'hairstyle.price' is less or equal to zero!") }
}
