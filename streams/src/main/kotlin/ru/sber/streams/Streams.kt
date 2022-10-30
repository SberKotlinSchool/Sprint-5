package ru.sber.streams


// 1. Используя withIndex() посчитать сумму элементов листа, индекс которых кратен 3. (нулевой индекс тоже входит)
fun getSumWithIndexDivisibleByThree(list: List<Long>): Long {
    return list.withIndex().sumOf { if (it.index % 3 == 0) it.value else 0; }
}

// 2. Используя функцию generateSequence() создать последовательность, возвращающую числа Фибоначчи.
fun generateFibonacciSequence(): Sequence<Int> {
    var x = 1;
    return generateSequence(0) {
        val res = x;
        x += it
        res
    }


    return generateSequence(Pair(0, 1)) { Pair(it.second, it.first + it.second) }
        .map { it.first }
}

// 3. Получить города, в которых есть покупатели.
fun Shop.getCustomersCities() : Set<City> {
    return this.customers
        .map { it.city }
        .toSet()
}

// 4. Получить все когда-либо заказанные продукты.
fun Shop.allOrderedProducts(): Set<Product> = this.customers
    .flatMap { it.orders }
    .flatMap { it.products }
    .toSet()

// 5. Получить покупателя, который сделал больше всего заказов.
fun Shop.getCustomerWithMaximumNumberOfOrders(): Customer? = this.customers
    .sortedBy { it.orders.size }
    .last()

// 6. Получить самый дорогой продукт, когда-либо приобртенный покупателем.
fun Customer.getMostExpensiveProduct(): Product? = this.orders
    .flatMap { it.products }
    .maxByOrNull { it.price }

// 7. Получить соответствие в мапе: город - количество заказанных и доставленных продуктов в данный город.
fun Shop.getNumberOfDeliveredProductByCity(): Map<City, Int> = customers.map { it.city to it.orders
    .filter { it.isDelivered }
    .flatMap { it.products }
    .count() }
    .groupBy { it.first }
    .map { it.key to it.value.sumOf { it.second } }.toMap()

// 8. Получить соответствие в мапе: город - самый популярный продукт в городе.
fun Shop.getMostPopularProductInCity(): Map<City, Product> = customers
    .map { it.city to  it.orders.flatMap { it.products } }
    .groupBy { it.first }
    .map{ it.key to it.value.flatMap {it.second }
        .groupBy { it }
        .maxByOrNull { it.value.size }
    }
    .map { it.first to it.second!!.key }.toMap()

// 9. Получить набор товаров, которые заказывали все покупатели.
fun Shop.getProductsOrderedByAll(): Set<Product> = customers
    .flatMap { it.orders.flatMap { it.products } }
    .map { product -> product to customers.map { it.orders.filter { it.products.contains(product) } } }
    .filter { it.second.minOf { it.size } != 0 }
    .groupBy { it.first }
    .map { it.key }
    .toSet()

