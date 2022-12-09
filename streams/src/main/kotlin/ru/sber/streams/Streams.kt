package ru.sber.streams

// 1. Используя withIndex() посчитать сумму элементов листа, индекс которых кратен 3. (нулевой индекс тоже входит)
fun getSumWithIndexDivisibleByThree(list: List<Long>): Long {
    var result: Long = 0
    for ((index, value) in list.withIndex()) {
        if (index % 3 == 0) {
            result += value
        }
    }
    return result
}

// 2. Используя функцию generateSequence() создать последовательность, возвращающую числа Фибоначчи.
fun generateFibonacciSequence(): Sequence<Int> {
    return generateSequence(Pair(0, 1), { Pair(it.second, it.first + it.second) }).map { it.first }
}

// 3. Получить города, в которых есть покупатели.
fun Shop.getCustomersCities(): Set<City> = customers.map { it.city }.toSet()

// 4. Получить все когда-либо заказанные продукты.
fun Shop.allOrderedProducts(): Set<Product> = customers.flatMap { it.orders.flatMap { it.products } }.toSet()

//5. Получить покупателя, который сделал больше всего заказов.
fun Shop.getCustomerWithMaximumNumberOfOrders(): Customer? = customers.maxBy { it.orders.size }

// 6. Получить самый дорогой продукт, когда-либо приобретенный покупателем.
fun Customer.getMostExpensiveProduct(): Product? = orders.filter { it.isDelivered }.flatMap { it.products }.maxBy { it.price }

// TODO7. Получить соответствие в мапе: город - количество заказанных и доставленных продуктов в данный город.
fun Shop.getNumberOfDeliveredProductByCity(): Map<City, Int> = customers.groupBy { it.city }.mapValues { it.value.sumOf { it.orders.filter { it.isDelivered }.sumOf { it.products.size } } }

// 8. Получить соответствие в мапе: город - самый популярный продукт в городе.
fun Shop.getMostPopularProductInCity(): Map<City, Product?> {
    val deliveredProducts: Map<City, List<Product>> = customers.groupBy { it.city }.mapValues { it.value.flatMap { it.orders.filter { it.isDelivered } }.flatMap { it.products } }

    val mapValues2: Map<City, Map.Entry<Product, Int>?> = deliveredProducts.mapValues { it.value.groupingBy { it }.eachCount().filter { it.value > 1 }.maxBy { it.value } }

    return mapValues2.mapValues { it.value?.key }
}

// 9. Получить набор товаров, которые заказывали все покупатели.
fun Shop.getProductsOrderedByAll(): Set<Product> =  customers.flatMap { it.orders }.flatMap { it.products }.toSet()
