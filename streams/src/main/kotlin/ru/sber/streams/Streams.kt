package ru.sber.streams


// 1. Используя withIndex() посчитать сумму элементов листа, индекс которых кратен 3. (нулевой индекс тоже входит)
fun getSumWithIndexDivisibleByThree(list: List<Long>): Long {
    var sum = 0L
    for (wi in list.withIndex()) {
        sum += if (wi.index % 3 == 0) wi.value else 0
    }
    return sum
}

// 2. Используя функцию generateSequence() создать последовательность, возвращающую числа Фибоначчи.
fun generateFibonacciSequence() =
    generateSequence(Pair(0, 1)) { Pair(it.second, it.first + it.second) }.map { it.first }

// 3. Получить города, в которых есть покупатели.
fun Shop.getCustomersCities(): Set<City> = customers.map { it.city }.toSet()


// 4. Получить все когда-либо заказанные продукты.
fun Shop.allOrderedProducts(): Set<Product> = customers.flatMap { it.orders }.flatMap { it.products }.toSet()

// 5. Получить покупателя, который сделал больше всего заказов.
fun Shop.getCustomerWithMaximumNumberOfOrders(): Customer? =
    customers.asSequence().map {
        it to it.orders.count()
    }.maxByOrNull { p -> p.second }?.first

// 6. Получить самый дорогой продукт, когда-либо приобртенный покупателем.
fun Customer.getMostExpensiveProduct(): Product? =
    orders.flatMap { o -> o.products.map { p -> p to p.price } }
        .maxByOrNull { pair -> pair.second }?.first


// 7. Получить соответствие в мапе: город - количество заказанных и доставленных продуктов в данный город.
fun Shop.getNumberOfDeliveredProductByCity(): Map<City, Int> = customers
    .groupBy { c -> c.city }
    .mapValues { e ->
        e.value.flatMap { it.orders }.filter { it.isDelivered }.sumOf { it.products.size }
    }

// 8. Получить соответствие в мапе: город - самый популярный продукт в городе.
fun Shop.getMostPopularProductInCity(): Map<City, Product> =
    customers.groupBy { c -> c.city }
        .mapValues { e ->
            e.value.flatMap { c -> c.orders }.flatMap { o -> o.products }
                .groupBy { o2 -> o2 }
                .mapValues { m -> m.value.count() }
                .maxByOrNull { it.value }?.key!!
        }


// 9. Получить набор товаров, которые заказывали все покупатели.
fun Shop.getProductsOrderedByAll(): Set<Product> = customers
    .map { c -> c.orders.flatMap { o -> o.products }.toSet() }
    .reduce { acc, pl ->
        acc.intersect(pl)
    }


