package ru.sber.streams


// 1. Используя withIndex() посчитать сумму элементов листа, индекс которых кратен 3. (нулевой индекс тоже входит)
fun getSumWithIndexDivisibleByThree(list: List<Long>): Long  =  list.withIndex().sumOf {
    if (it.index % 3 == 0) it.value else 0
}

// 2. Используя функцию generateSequence() создать последовательность, возвращающую числа Фибоначчи.
fun generateFibonacciSequence(): Sequence<Int> = generateSequence(0 to 1){
    (first, second) -> second to first + second}.map {it.first}


// 3. Получить города, в которых есть покупатели.
fun Shop.getCustomersCities(): Set<City> = this.customers.map {it.city}.toSet()

// 4. Получить все когда-либо заказанные продукты.
fun Shop.allOrderedProducts(): Set<Product> = this.customers.flatMap { it.orders }.flatMap { it.products }.toSet()

// 5. Получить покупателя, который сделал больше всего заказов.
fun Shop.getCustomerWithMaximumNumberOfOrders(): Customer? = this.customers.maxByOrNull { it.orders.size }

// 6. Получить самый дорогой продукт, когда-либо приобртенный покупателем.
fun Customer.getMostExpensiveProduct(): Product? = orders.flatMap { it.products }.maxByOrNull { it.price }

// 7. Получить соответствие в мапе: город - количество заказанных и доставленных продуктов в данный город.
fun Shop.getNumberOfDeliveredProductByCity(): Map<City, Int> {
    val cityToProductCount = customers
        .flatMap { customer ->
            customer.orders
                .filter { it.isDelivered }
                .map { customer.city to it.products.size }
        }
        .groupBy({ it.first }, { it.second })
        .mapValues { (_, productCounts) -> productCounts.sum() }
        .toMutableMap()

    //без этого куска кода что то не придумать решение. без него, в итоговой мапе нет городов с НЕ доставленными заказами, приходиться добавлять их явно.
    customers.map { it.city }.distinct().forEach { city ->
        cityToProductCount.putIfAbsent(city, 0)
    }

    return cityToProductCount.toMap()
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

