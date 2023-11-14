package ru.sber.streams

// 1. Используя withIndex() посчитать сумму элементов листа, индекс которых кратен 3. (нулевой индекс тоже входит)
fun getSumWithIndexDivisibleByThree(list: List<Long>): Long {
    return list.withIndex()
        .filter { it.index % 3 == 0 }
        .sumOf { it.value }
}

// 2. Используя функцию generateSequence() создать последовательность, возвращающую числа Фибоначчи.
fun generateFibonacciSequence(): Sequence<Int> {
    return sequence {
        var terms = Pair(0, 1)
        while (true) {
            yield(terms.first)
            terms = Pair(terms.second, terms.first + terms.second)
        }
    }
}

// 3. Получить города, в которых есть покупатели.
fun Shop.getCustomersCities(): Set<City> {
    return this.customers
        .map { it.city }
        .toSet()
}

// 4. Получить все когда-либо заказанные продукты.
fun Shop.allOrderedProducts(): Set<Product> {
    return this.customers
        .flatMap { it.orders }
        .flatMap { it.products }
        .toSet()
}

// 5. Получить покупателя, который сделал больше всего заказов.
fun Shop.getCustomerWithMaximumNumberOfOrders(): Customer? {
    return this.customers.maxByOrNull { it.orders.size }
}

// 6. Получить самый дорогой продукт, когда-либо приобртенный покупателем.
fun Customer.getMostExpensiveProduct(): Product? {
    return this.orders
        .flatMap { it.products }
        .maxByOrNull { it.price }
}

// 7. Получить соответствие в мапе: город - количество заказанных и доставленных продуктов в данный город.
fun Shop.getNumberOfDeliveredProductByCity(): Map<City, Int> {
    return this.customers
        .groupingBy { it.city }
        .fold(0) { accumulator, customer ->
            accumulator + customer.orders
                .filter { it.isDelivered }
                .flatMap { it.products }.size
        }
}

// 8. Получить соответствие в мапе: город - самый популярный продукт в городе.
fun Shop.getMostPopularProductInCity(): Map<City, Product> {
    return this.customers
        .groupBy({it.city}, {it.orders.flatMap { order -> order.products }})
        .mapValues {entry ->  entry.value.flatten()
            .groupingBy { it }
            .eachCount()
            .maxByOrNull { it.value }!!
            .key
        }
}

// 9. Получить набор товаров, которые заказывали все покупатели.
fun Shop.getProductsOrderedByAll(): Set<Product> {
    return this.customers
        .associateBy({it}, {it.orders.flatMap { order -> order.products }.toSet()})
        .values
        .reduce { accumulator, products -> accumulator.intersect(products) }
}

