package ru.sber.streams

// 1. Используя withIndex() посчитать сумму элементов листа, индекс которых кратен 3. (нулевой индекс тоже входит)
fun getSumWithIndexDivisibleByThree(list: List<Long>): Long {
    return list.withIndex().filter { it.index % 3 == 0 }.sumOf { it.value }
}

// 2. Используя функцию generateSequence() создать последовательность, возвращающую числа Фибоначчи.
fun generateFibonacciSequence(): Sequence<Int> {
    return generateSequence(0 to 1) { (first, second) -> second to first + second }.map { it.first }
}

// 3. Получить города, в которых есть покупатели.
fun Shop.getCustomersCities(): Set<City> = customers.map { it.city }.toSet()

// 4. Получить все когда-либо заказанные продукты.
fun Shop.allOrderedProducts(): Set<Product> =
    customers.flatMap { customer -> customer.orders.flatMap { it.products } }.toSet()


// 5. Получить покупателя, который сделал больше всего заказов.
fun Shop.getCustomerWithMaximumNumberOfOrders(): Customer? = customers.maxByOrNull { it.orders.size }

// 6. Получить самый дорогой продукт, когда-либо приобртенный покупателем.
fun Customer.getMostExpensiveProduct(): Product? = orders.flatMap { it.products }.maxByOrNull { it.price }

// 7. Получить соответствие в мапе: город - количество заказанных и доставленных продуктов в данный город.
fun Shop.getNumberOfDeliveredProductByCity(): Map<City, Int> {
    val map = customers.associate { it.city to 0 }.toMutableMap()
    for (key in map.keys) {
        customers.filter { customer -> customer.city == key }.forEach { customer ->
            map[key] =
                map[key]!!.plus(customer.orders.filter { order -> order.isDelivered }.sumOf { it.products.size })
        }
    }
    return map
}

// 8. Получить соответствие в мапе: город - самый популярный продукт в городе.
fun Shop.getMostPopularProductInCity(): Map<City, Product> =
    customers.groupBy { it.city }.mapValues { list ->
        list.value.flatMap { it.orders }
            .flatMap { it.products }
            .groupBy { it }
            .maxByOrNull { it.value.size }!!.key
    }

// 9. Получить набор товаров, которые заказывали все покупатели.
fun Shop.getProductsOrderedByAll(): Set<Product> {
    val countCustomers = customers.size
    val map = customers.flatMap { it.orders }.flatMap { it.products }.associateWith { 0 }.toMutableMap()
    customers.forEach { customer ->
        customer.orders.forEach {
            it.products.forEach { product ->
                map[product] = map[product]!! + 1
            }
        }
    }

    return map.keys.filter { map[it] == countCustomers }.toSet()
}