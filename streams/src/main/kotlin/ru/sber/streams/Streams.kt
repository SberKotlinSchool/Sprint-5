package ru.sber.streams


// 1. Используя withIndex() посчитать сумму элементов листа, индекс которых кратен 3. (нулевой индекс тоже входит)
fun getSumWithIndexDivisibleByThree(list: List<Long>): Long {
    return list.withIndex().filter { it.index % 3 == 0 }.sumOf { it.value }
}

// 2. Используя функцию generateSequence() создать последовательность, возвращающую числа Фибоначчи.  0, 1, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89, 144, 233, 377...
fun generateFibonacciSequence(): Sequence<Int> {
    return generateSequence(Pair(0, 1), { Pair(it.second, it.first + it.second) }).map { it.first }
}

// 3. Получить города, в которых есть покупатели.
fun Shop.getCustomersCities(): Set<City> = customers.map { it.city }.toSet()

// 4. Получить все когда-либо заказанные продукты.
fun Shop.allOrderedProducts(): Set<Product> = customers.flatMap { it.orders.flatMap { it.products } }.toSet()

// 5. Получить покупателя, который сделал больше всего заказов.
fun Shop.getCustomerWithMaximumNumberOfOrders(): Customer? = customers.maxWithOrNull(Comparator.comparing { it.orders.size })

// 6. Получить самый дорогой продукт, когда-либо приобртенный покупателем.
fun Customer.getMostExpensiveProduct(): Product? = orders.flatMap { it.products }.maxWithOrNull(Comparator.comparing { it.price })

// 7. Получить соответствие в мапе: город - количество заказанных и доставленных продуктов в данный город.
fun Shop.getNumberOfDeliveredProductByCity(): Map<City, Int> {
    // у каждого клиента соответствие города и кол-ва продуктов в доставленных заказах
    // группируем по городу и получаем соответствие город - колво продуктов
    // суммируем
    return customers.map { it.city to it.orders.filter { it.isDelivered }.sumOf { it.products.size } }
        .groupBy { it.first }
        .map { it.key to it.value.sumOf { it.second } }.toMap()
}

// 8. Получить соответствие в мапе: город - самый популярный продукт в городе. city-product, city-product:kolvo, city-
fun Shop.getMostPopularProductInCity(): Map<City, Product> {

    // город - все товары
    // город - товар-колво
    // город - макс колво
    return customers.map { it.city to it.orders.flatMap { it.products } }.groupBy( { it.first }, { it.second } )
        .map { it.key to it.value.flatten().groupBy({ it }, { it }).map { it.key to it.value.size } }
        .map { it.first to it.second.reduce { acc, pair -> maxOf(acc, pair, Comparator.comparing { it.second }) }.first }
        .toMap()
}

// 9. Получить набор товаров, которые заказывали все покупатели.
fun Shop.getProductsOrderedByAll(): Set<Product> {
    // покупатель - продукты
    // продукт - set<покупатель>
    // List<Pair>
    return customers.map {it to it.orders.flatMap { it.products }}.flatMap { it.second.map { p -> p to it.first } }
        .groupBy({ it.first }, {it.second}).map { it.key to it.value.toSet() }.filter { it.second.size == customers.size }
        .map { it.first }.toSet()
}


data class Sales(val year: Int, val price: Int)

val myList = listOf(
    Sales(2017, 10),
    Sales(2017, 19),
    Sales(2020, 15),
    Sales(2021, 100),
    Sales(2020, 20),
)

fun main () {
    val reduced = myList.groupBy({ it.year }, { it })
        .mapValues { it.value.reduce{ left, right ->
            Sales(left.year, (left.price + right.price)) } }
        .values
        .sortedBy { it.year }
    reduced.forEach { println("${it.year}: ${it.price}") }
}

