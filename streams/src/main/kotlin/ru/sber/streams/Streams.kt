package ru.sber.streams

import java.util.stream.Collectors


// 1. Используя withIndex() посчитать сумму элементов листа, индекс которых кратен 3. (нулевой индекс тоже входит)
fun getSumWithIndexDivisibleByThree(list: List<Long>): Long =
     list.withIndex().filter { it.index % 3 == 0 }.sumOf { it.value }

// 2. Используя функцию generateSequence() создать последовательность, возвращающую числа Фибоначчи.
fun generateFibonacciSequence(): Sequence<Int> =
    generateSequence(Pair(0, 1)) { Pair(it.second, it.first + it.second) }
        .map { it.first }

// 3. Получить города, в которых есть покупатели.
fun Shop.getCustomersCities(): Set<City> =
    this.customers.stream().map { it.city }.collect(Collectors.toSet())

// 4. Получить все когда-либо заказанные продукты.
fun Shop.allOrderedProducts(): Set<Product> =
    this.customers.asSequence().flatMap { it.orders }
        .flatMap { it.products }.toSet()

// 5. Получить покупателя, который сделал больше всего заказов.
fun Shop.getCustomerWithMaximumNumberOfOrders(): Customer? =
    this.customers.stream().max { one, two ->
        one.orders.size - two.orders.size }.get()

// 6. Получить самый дорогой продукт, когда-либо приобртенный покупателем.
fun Customer.getMostExpensiveProduct(): Product? =
    this.orders.asSequence().flatMap { it.products }
        .maxWithOrNull { one, two -> (one.price - two.price).toInt() }

// 7. Получить соответствие в мапе: город - количество заказанных и доставленных продуктов в данный город.
fun Shop.getNumberOfDeliveredProductByCity(): Map<City, Int> =
    this.customers.groupBy({ it.city }, { it ->
        it.orders.filter { it.isDelivered }.sumOf { it.products.size }
    }).map { Pair(it.key, it.value.sum()) }.toMap()

// 8. Получить соответствие в мапе: город - самый популярный продукт в городе.
fun Shop.getMostPopularProductInCity(): Map<City, Product> =
    this.customers.groupBy({ it.city }, { it.orders.flatMap { or -> or.products } })
        .map { map -> Pair(map.key,
                map.value.flatten().groupingBy { it }.eachCount()
                .maxByOrNull { it.value }?.key ?: Product("", 0.0)) }.toMap()

// 9. Получить набор товаров, которые заказывали все покупатели.
fun Shop.getProductsOrderedByAll(): Set<Product> =
    this.customers.map { it -> it.orders.flatMap { it.products }.toSet() }
        .reduce { acc, products -> acc.intersect(products) }

