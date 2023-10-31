package ru.sber.streams


// 1. Используя withIndex() посчитать сумму элементов листа, индекс которых кратен 3. (нулевой индекс тоже входит)
fun getSumWithIndexDivisibleByThree(list: List<Long>): Long {
    return list.withIndex().filter { it.index % 3 == 0 }
        .sumOf { it.value }
}

// 2. Используя функцию generateSequence() создать последовательность, возвращающую числа Фибоначчи.
fun generateFibonacciSequence(): Sequence<Int> {
    return generateSequence(0 to 1) { (first, second) ->
        second to first + second
    }
        .map { (first) -> first }
}

// 3. Получить города, в которых есть покупатели.
fun Shop.getCustomersCities(): Set<City> = customers.mapTo(mutableSetOf(), Customer::city)

// 4. Получить все когда-либо заказанные продукты.
fun Shop.allOrderedProducts(): Set<Product> = customers.flatMapTo(mutableSetOf()) {
    it.orders.flatMap(Order::products)
}

// 5. Получить покупателя, который сделал больше всего заказов.
fun Shop.getCustomerWithMaximumNumberOfOrders(): Customer? = customers.maxByOrNull { it.orders.size }

// 6. Получить самый дорогой продукт, когда-либо приобртенный покупателем.
fun Customer.getMostExpensiveProduct(): Product? = orders.flatMap(Order::products)
    .maxByOrNull(Product::price)

// 7. Получить соответствие в мапе: город - количество заказанных и доставленных продуктов в данный город.
fun Shop.getNumberOfDeliveredProductByCity(): Map<City, Int> =
    customers.groupBy(Customer::city) { customer ->
        customer.orders.filter(Order::isDelivered).sumOf { it.products.size }
    }
        .mapValues { it.value.sum() }

// 8. Получить соответствие в мапе: город - самый популярный продукт в городе.
fun Shop.getMostPopularProductInCity(): Map<City, Product> =
    customers.groupBy(Customer::city) { it.orders.flatMap(Order::products) }
        .mapValues { entry -> entry.value.flatten().groupingBy { it }.eachCount().maxBy { it.value }.key }

// 9. Получить набор товаров, которые заказывали все покупатели.
fun Shop.getProductsOrderedByAll(): Set<Product> {
    val products = customers.map { customer ->
        customer.orders.flatMapTo(mutableSetOf(), Order::products)
    }
    return products.fold(mutableSetOf()) { acc, elements ->
        (if (acc.isEmpty()) acc + elements else acc.intersect(elements)).toMutableSet()
    }
}


