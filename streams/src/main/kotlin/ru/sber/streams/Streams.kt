package ru.sber.streams

import io.mockk.InternalPlatformDsl.toArray
import java.util.stream.Collectors


// 1. Используя withIndex() посчитать сумму элементов листа, индекс которых кратен 3. (нулевой индекс тоже входит)
fun getSumWithIndexDivisibleByThree(list: List<Long>): Long {

    var counter = 0L;
    for ((index, value) in list.withIndex()) {
        if (index % 3 == 0) {
            counter += value
        }
    }

    return counter
}

// 2. Используя функцию generateSequence() создать последовательность, возвращающую числа Фибоначчи.
fun generateFibonacciSequence(): Sequence<Int> {
    return generateSequence(Pair(0, 1)) { (currentValue, nextValue) ->
        Pair(nextValue, currentValue + nextValue)
    }.map { it.component1() }
}

// 3. Получить города, в которых есть покупатели.
fun Shop.getCustomersCities(): Set<City> =
    customers.stream().map { customer -> customer.city }.collect(Collectors.toSet())

// 4. Получить все когда-либо заказанные продукты.
fun Shop.allOrderedProducts(): Set<Product> =
    customers.stream().map { customer -> customer.orders }.flatMap { orders -> orders.stream() }
        .map { order -> order.products }.flatMap { products -> products.stream() }.collect(Collectors.toSet())

// 5. Получить покупателя, который сделал больше всего заказов.
fun Shop.getCustomerWithMaximumNumberOfOrders(): Customer? = customers.maxByOrNull { customer -> customer.orders.size }

// 6. Получить самый дорогой продукт, когда-либо приобртенный покупателем.
fun Customer.getMostExpensiveProduct(): Product? =
    orders.stream().map { order -> order.products }.flatMap { product -> product.stream() }.collect(Collectors.toList())
        .maxByOrNull { product -> product.price }

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
fun Shop.getMostPopularProductInCity(): Map<City, Product> {
    return customers.flatMap { customer ->
        customer.orders.map { order ->
            customer.city to order.products.groupingBy { it }.eachCount()
        }
    }.groupBy({ it.first }) { (_, productCounts) ->
        findKeyWithMaxValue(productCounts)
    }.mapValues { it.value.get(0)!! }
}

fun findKeyWithMaxValue(map: Map<Product, Int>): Product? {
    return map.maxByOrNull { it.value }?.key
}

// 9. Получить набор товаров, которые заказывали все покупатели.
fun Shop.getProductsOrderedByAll(): Set<Product> = customers
    .map { customer -> customer.orders.flatMap { it.products }.toSet() }
    .takeIf { it.isNotEmpty() }
    ?.reduce { acc, products -> acc.intersect(products) }
    ?: emptySet()


