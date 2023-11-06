package ru.sber.streams

import io.mockk.InternalPlatformDsl.toArray


// 1. Используя withIndex() посчитать сумму элементов листа, индекс которых кратен 3. (нулевой индекс тоже входит)
fun getSumWithIndexDivisibleByThree(list: List<Long>) =
        list.withIndex().filter { (index, _) -> index % 3 == 0 }.sumOf { it.value }

// 2. Используя функцию generateSequence() создать последовательность, возвращающую числа Фибоначчи.
fun generateFibonacciSequence(): Sequence<Int> {
    var n1 = 1
    return generateSequence(0) {
        val f = it + n1
        n1 = it
        f
    }
}

// 3. Получить города, в которых есть покупатели.
fun Shop.getCustomersCities(): Set<City> = this.customers.filter { it.orders.isNotEmpty() }.map { it.city }.toSet()

// 4. Получить все когда-либо заказанные продукты.
fun Shop.allOrderedProducts(): Set<Product> = this.customers.map { it.orders }.flatten().map { it.products }.flatten().toSet()

// 5. Получить покупателя, который сделал больше всего заказов.
fun Shop.getCustomerWithMaximumNumberOfOrders(): Customer? = this.customers.maxByOrNull { it.orders.size }

// 6. Получить самый дорогой продукт, когда-либо приобртенный покупателем.
fun Customer.getMostExpensiveProduct(): Product? = this.orders.map { it.products }.flatten().toSet().maxByOrNull { it.price }

// 7. Получить соответствие в мапе: город - количество заказанных и доставленных продуктов в данный город.
fun Shop.getNumberOfDeliveredProductByCity(): Map<City, Int> = this.customers
        .groupBy({ it.city }, { it.orders.filter { order -> order.isDelivered }.map { order -> order.products.size }})
        .mapValues { it.value.flatten().sum() }

// 8. Получить соответствие в мапе: город - самый популярный продукт в городе.
fun Shop.getMostPopularProductInCity(): Map<City, Product> = this.customers
        .groupBy( { it.city }, { it.orders.flatMap { order -> order.products } })
        .mapValues { it.value.flatten().groupingBy { product -> product }.eachCount().maxByOrNull { entry -> entry.value }?.key!! }

// 9. Получить набор товаров, которые заказывали все покупатели.
fun Shop.getProductsOrderedByAll(): Set<Product> = this.customers.map { customer -> customer.orders.flatMap { order -> order.products }.toSet() }
        .reduce { acc, products ->  acc.intersect(products) }