package ru.sber.streams


// 1. Используя withIndex() посчитать сумму элементов листа, индекс которых кратен 3. (нулевой индекс тоже входит)
fun getSumWithIndexDivisibleByThree(longs: List<Long>): Long {
    return longs.withIndex().filter { it.index % 3 == 0 }.sumOf { it.value }
}

// 2. Используя функцию generateSequence() создать последовательность, возвращающую числа Фибоначчи.
fun generateFibonacciSequence(): Sequence<Int> {
    return generateSequence(Pair(0, 1)) { Pair(it.second, it.first + it.second) }.map { it.first }
}

// 3. Получить города, в которых есть покупатели.
fun Shop.getCustomersCities(): Set<City> = this.customers.map { it.city }.toSet()

// 4. Получить все когда-либо заказанные продукты.
fun Shop.allOrderedProducts(): Set<Product> = this.customers.flatMap { it.orders }.flatMap { it.products }.toSet()

// 5. Получить покупателя, который сделал больше всего заказов.
fun Shop.getCustomerWithMaximumNumberOfOrders(): Customer? = this.customers.maxByOrNull { it.orders.count() }

// 6. Получить самый дорогой продукт, когда-либо приобртенный покупателем.
fun Customer.getMostExpensiveProduct(): Product? = this.orders.flatMap { it.products }.maxByOrNull { it.price }

// 7. Получить соответствие в мапе: город - количество заказанных и доставленных продуктов в данный город.
fun Shop.getNumberOfDeliveredProductByCity(): Map<City, Int> = this.customers
        .map { it.city to it.orders
            .filter { or -> or.isDelivered }
            .flatMap { order -> order.products }
            .count()
        }
        .groupingBy { it.first }
        .reduce { key, acc, el -> key to acc.second + el.second }.values.toMap()

// 8. Получить соответствие в мапе: город - самый популярный продукт в городе.
fun Shop.getMostPopularProductInCity(): Map<City, Product> {
    return customers.map { it.city to
            it.orders
                .flatMap { o -> o.products }
                .groupBy { p -> p.name }
    }
        .groupBy { it.first }
        .map { it.key to it.value
            .flatMap { it.second.values }
            .groupBy { it.first() }
            .map { it.key to it.value.flatMap { it }.count() }
            .sortedBy { it.second }.last()
        }
        .map { it.first to it.second.first }.toMap()
}

// 9. Получить набор товаров, которые заказывали все покупатели.
fun Shop.getProductsOrderedByAll(): Set<Product> {
    return customers.map { c -> c.orders
        .flatMap { it.products }
        .map { it to c }
    }
        .flatMap { it }
        .groupingBy { it.first }
        .eachCount()
        .filter { it.value == customers.size }
        .map { it.key }.toSet()
}

