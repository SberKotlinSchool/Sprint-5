package ru.sber.streams


// 1. Используя withIndex() посчитать сумму элементов листа, индекс которых кратен 3. (нулевой индекс тоже входит)
fun getSumWithIndexDivisibleByThree(list: List<Long>): Long {
    return list.filterIndexed { index, _ -> index % 3L == 0L }.sum()
}

// 2. Используя функцию generateSequence() создать последовательность, возвращающую числа Фибоначчи.
fun generateFibonacciSequence(): Sequence<Int> {
    var next = 1
    return generateSequence(0) {
        val prev = next
        next = it
        it + prev
    }
}

// 3. Получить города, в которых есть покупатели.
fun Shop.getCustomersCities(): Set<City> = this.customers.map { it.city }.toSet()

// 4. Получить все когда-либо заказанные продукты.
fun Shop.allOrderedProducts(): Set<Product> = this.customers
    .map { it.orders }.fold(mutableSetOf<Product>()) { acc, it ->
        it.map { t -> acc.addAll(t.products) }
        acc
    }

// 5. Получить покупателя, который сделал больше всего заказов.
fun Shop.getCustomerWithMaximumNumberOfOrders(): Customer? = this.customers.maxByOrNull { it.orders.size }!!

// 6. Получить самый дорогой продукт, когда-либо приобртенный покупателем.
fun Customer.getMostExpensiveProduct(): Product? =
    this.orders.maxByOrNull { order -> order.products.maxOf { it.price } }
        ?.products?.maxByOrNull { it.price }

// 7. Получить соответствие в мапе: город - количество заказанных и доставленных продуктов в данный город.
fun Shop.getNumberOfDeliveredProductByCity(): Map<City, Int> =
    this.customers.groupBy({ it.city }, { it.orders.filter { i -> i.isDelivered }.sumOf { it.products.size } })
        .mapValues { it.value.sum() }

// 8. Получить соответствие в мапе: город - самый популярный продукт в городе.
fun Shop.getMostPopularProductInCity(): Map<City, Product> =
    this.customers.groupBy({ it.city }, { c -> c.orders.flatMap { it.products } })
        .mapValues {
            it.value.flatten().groupingBy { p -> p }
                .eachCount().maxByOrNull { v -> v.value }!!.key
        }

// 9. Получить набор товаров, которые заказывали все покупатели.
fun Shop.getProductsOrderedByAll(): Set<Product> =
    this.customers.map { c -> c.orders.flatMap { it.products } }
        .fold(mutableListOf<Product>()) { acc, it ->
            if (acc.isEmpty()) {
                it.toMutableList()
            } else {
                val d = acc.intersect(it.toSet())
                d.toMutableList()
            }
        }.toSet()
