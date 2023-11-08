package ru.sber.streams


// 1. Используя withIndex() посчитать сумму элементов листа, индекс которых кратен 3. (нулевой индекс тоже входит)
fun getSumWithIndexDivisibleByThree(list: List<Long>): Long {
    return list.withIndex().filter { it.index % 3 == 0 }.sumOf { it.value }
}

// 2. Используя функцию generateSequence() создать последовательность, возвращающую числа Фибоначчи.
fun generateFibonacciSequence(): Sequence<Int> {
    return generateSequence(Pair(0, 1)) { (prev, current) ->
        Pair(current, prev + current)
    }.map { it.first }
}

// 3. Получить города, в которых есть покупатели.
fun Shop.getCustomersCities(): Set<City> {
    return customers.map { it.city }.toSet()
}

// 4. Получить все когда-либо заказанные продукты.
fun Shop.allOrderedProducts(): Set<Product> {
    return customers.flatMap { it.orders }
        .flatMap { it.products }
        .toSet()
}

// 5. Получить покупателя, который сделал больше всего заказов.
fun Shop.getCustomerWithMaximumNumberOfOrders(): Customer? {
    return customers.maxByOrNull { it.orders.size }
}

// 6. Получить самый дорогой продукт, когда-либо приобртенный покупателем.
fun Customer.getMostExpensiveProduct(): Product? {
    return orders.flatMap { it.products }
        .maxByOrNull { it.price }
}

// 7. Получить соответствие в мапе: город - количество заказанных и доставленных продуктов в данный город.
fun Shop.getNumberOfDeliveredProductByCity(): Map<City, Int> {
    val cityProductCount = mutableMapOf<City, Int>()
    customers.forEach { customer ->
        val city = customer.city
        val deliveredProductsCount = customer.orders
            .filter { it.isDelivered }
            .flatMap { it.products }
            .size
        cityProductCount[city] = cityProductCount.getOrDefault(city, 0) + deliveredProductsCount
    }
    return cityProductCount
}

// 8. Получить соответствие в мапе: город - самый популярный продукт в городе.
fun Shop.getMostPopularProductInCity(): Map<City, Product> {
    return customers.groupBy(
        keySelector = Customer::city,
        valueTransform = Customer::orders
    )
        .mapValues { listOrdersForCity ->
            listOrdersForCity.value
                .flatten()
                .flatMap(Order::products)
                .groupingBy { it }
                .eachCount()
                .maxByOrNull(Map.Entry<Product, Int>::value)!!
                .key

        }
}


// 9. Получить набор товаров, которые заказывали все покупатели.
fun Shop.getProductsOrderedByAll(): Set<Product> {
    val allProducts = customers.flatMap { it.orders }
        .flatMap { it.products }
        .toSet()
    return customers.fold(allProducts) { products, customer ->
        products.intersect(customer.orders.flatMap { it.products }.toSet())
    }
}

