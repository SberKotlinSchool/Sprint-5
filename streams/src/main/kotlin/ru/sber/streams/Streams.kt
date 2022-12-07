package ru.sber.streams

import java.util.Comparator


// 1. Используя withIndex() посчитать сумму элементов листа, индекс которых кратен 3. (нулевой индекс тоже входит)
fun getSumWithIndexDivisibleByThree(list: List<Long>): Long {
    return list.withIndex().filterIndexed { index, _ -> index % 3 == 0 }.sumOf { it.value }
}

// 2. Используя функцию generateSequence() создать последовательность, возвращающую числа Фибоначчи.
fun generateFibonacciSequence(): Sequence<Int> {
    val x = IntArray(2) { it }
    return generateSequence(0) {
        if (it == 0)
            1
        else {
            val result = x[0] + it
            x[0] = x[1]
            x[1] = result
            result
        }
    }
}

// 3. Получить города, в которых есть покупатели.
fun Shop.getCustomersCities(): Set<City> =
    customers.map { it.city }.toSet()

// 4. Получить все когда-либо заказанные продукты.
fun Shop.allOrderedProducts(): Set<Product> =
    customers.map { customer -> customer.orders.map { order -> order.products }.flatten() }.flatten().toSet()

// 5. Получить покупателя, который сделал больше всего заказов.
fun Shop.getCustomerWithMaximumNumberOfOrders(): Customer? =
    customers.maxWithOrNull(compareBy { it.orders.size })

// 6. Получить самый дорогой продукт, когда-либо приобртенный покупателем.
fun Customer.getMostExpensiveProduct(): Product? =
    orders.map { it.products }
        .flatten()
        .maxWithOrNull( compareBy { it.price })

// 7. Получить соответствие в мапе: город - количество заказанных и доставленных продуктов в данный город.
fun Shop.getNumberOfDeliveredProductByCity(): Map<City, Int> =
    customers.groupBy({ it.city }, { it.orders.filter { order -> order.isDelivered }.map { order -> order.products.size } })
        .mapValues { it.value.flatten().sum() }

// 8. Получить соответствие в мапе: город - самый популярный продукт в городе.
fun Shop.getMostPopularProductInCity(): Map<City, Product> =
    customers.groupBy({ it.city }, { it.orders })
        .mapValues {
            it.value.flatten()
                .map { order -> order.products }.flatten()
                .groupingBy { product -> product }.eachCount()
                .maxByOrNull { entry -> entry.value }!!.key
        }

// 9. Получить набор товаров, которые заказывали все покупатели.
fun Shop.getProductsOrderedByAll(): Set<Product> {
    val x = customers.groupBy({ it }, { it.orders }).mapValues { it.value.flatten().map { order -> order.products }.flatten().toSet() }
    return x.map { it.value.filter { product -> x.values.all { products -> products.contains(product) } } }.flatten().toSet()
}

