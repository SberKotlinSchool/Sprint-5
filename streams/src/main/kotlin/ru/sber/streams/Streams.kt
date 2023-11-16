package ru.sber.streams


// 1. Используя withIndex() посчитать сумму элементов листа, индекс которых кратен 3. (нулевой индекс тоже входит)
fun getSumWithIndexDivisibleByThree(list: List<Long>): Long = list.withIndex().sumOf {
    if (it.index % 3 == 0) it.value else 0
}

// 2. Используя функцию generateSequence() создать последовательность, возвращающую числа Фибоначчи.
fun generateFibonacciSequence(): Sequence<Int> = generateSequence(0 to 1) {
    it.second to it.first + it.second
}.map { it.first }

// 3. Получить города, в которых есть покупатели.
fun Shop.getCustomersCities(): Set<City> = this.customers.map { it.city }.toSet()

// 4. Получить все когда-либо заказанные продукты.
fun Shop.allOrderedProducts(): Set<Product> = this.customers.flatMap { it.orders }.flatMap { it.products }.toSet()

// 5. Получить покупателя, который сделал больше всего заказов.
fun Shop.getCustomerWithMaximumNumberOfOrders(): Customer? = this.customers.maxByOrNull { it.orders.size }

// 6. Получить самый дорогой продукт, когда-либо приобртенный покупателем.
fun Customer.getMostExpensiveProduct(): Product? =
    this.orders.flatMap { it.products }.distinct().maxByOrNull { it.price }

// 7. Получить соответствие в мапе: город - количество заказанных и доставленных продуктов в данный город.
fun Shop.getNumberOfDeliveredProductByCity(): Map<City, Int> = this.customers.groupBy { it.city }
    .mapValues {
        it.value.flatMap { customer -> customer.orders.filter { order -> order.isDelivered } }
            .flatMap { order -> order.products }.size
    }

// 8. Получить соответствие в мапе: город - самый популярный продукт в городе.
fun Shop.getMostPopularProductInCity(): Map<City, Product> = this.customers.groupBy { it.city }.mapValues {
    it.value.flatMap { customer -> customer.orders }.flatMap { order -> order.products }
        .groupingBy { product -> product }.eachCount()
}.mapValues { it.value.maxByOrNull { entry -> entry.value }!!.key }

// 9. Получить набор товаров, которые заказывали все покупатели.
fun Shop.getProductsOrderedByAll(): Set<Product> =
    this.customers.map { it.orders.flatMap { order -> order.products }.toSet() }.reduce { result, current ->
        current.intersect(result)
    }

