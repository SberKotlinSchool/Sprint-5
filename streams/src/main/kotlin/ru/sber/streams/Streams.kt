package ru.sber.streams



// 1. Используя withIndex() посчитать сумму элементов листа, индекс которых кратен 3. (нулевой индекс тоже входит)
fun getSumWithIndexDivisibleByThree(list: List<Long>): Long {
    return list
        .withIndex()
        .toList()
        .filter { (i, _) -> i % 3 == 0 }
        .sumOf { (_, n) -> n }

}

// 2. Используя функцию generateSequence() создать последовательность, возвращающую числа Фибоначчи.
fun generateFibonacciSequence(): Sequence<Int> {
    return sequence {
        var terms = Pair(0, 1)
        // this sequence is infinite
        while (true) {
            yield(terms.first)
            terms = Pair(terms.second, terms.first + terms.second)
        }
    }
}

// 3. Получить города, в которых есть покупатели.
fun Shop.getCustomersCities(): Set<City> = customers.map { x -> x.city }.toSet()

// 4. Получить все когда-либо заказанные продукты.
fun Shop.allOrderedProducts(): Set<Product> = customers
                                              .flatMap { x -> x.orders }
                                              .flatMap { x-> x.products }
                                              .toSet()


// 5. Получить покупателя, который сделал больше всего заказов.
fun Shop.getCustomerWithMaximumNumberOfOrders(): Customer? = customers
                                                            .associateWith { x -> x.orders.size }
                                                            .entries
                                                            .sortedByDescending { (_, value) -> value }[0]
                                                            .key

// 6. Получить самый дорогой продукт, когда-либо приобртенный покупателем.
fun Customer.getMostExpensiveProduct(): Product? = orders
                                                    .flatMap { x-> x.products }
                                                    .associateWith { x -> x.price}
                                                    .entries
                                                    .sortedByDescending { (_, value) -> value }[0]
                                                    .key

// 7. Получить соответствие в мапе: город - количество заказанных и доставленных продуктов в данный город.
fun Shop.getNumberOfDeliveredProductByCity(): Map<City, Int> = customers
                                                            .groupBy{it.city}
                                                            .entries
                                                            .associateWith { x -> Pair(x.key,x.value
                                                                .flatMap { o -> o.orders
                                                                    .filter { f -> f.isDelivered }}
                                                                    .map { m->m.products.size }
                                                                    .fold(0){acc, next -> acc + next})}
                                                            .map { x-> x.value }.toMap()

// 8. Получить соответствие в мапе: город - самый популярный продукт в городе.
fun Shop.getMostPopularProductInCity(): Map<City, Product> = customers.associateWith { x -> Pair(x.city, x.orders.flatMap { o -> o.products }) }
                                                                .entries.groupBy { x -> x.key.city }
                                                                .entries
                                                                .associateWith { x -> Pair(x.key, x.value) }
                                                                .map { p ->
                                                                    Pair(p.key.key, p.value.second.flatMap { l -> l.value.second }
                                                                        .groupingBy { it }
                                                                        .eachCount()
                                                                        .entries
                                                                        .sortedByDescending { (_, value) -> value }[0].key)
                                                                }.associate { mapIto -> Pair(mapIto.first, mapIto.second) }

// 9. Получить набор товаров, которые заказывали все покупатели.
fun Shop.getProductsOrderedByAll(): Set<Product> = customers
                                                .associateWith { x-> Pair(x, x.orders) }
                                                .entries
                                                .groupBy { x-> x.key }
                                                .entries
                                                .associateWith {
                                                    x -> Pair(x.key, x.value.map { m-> m.value.second.flatMap { f -> f.products}
                                                        .fold(listOf<Product>()){acc, next -> acc + next}}[0].toSet())}
                                                .flatMap { x-> x.value.second.toList()}
                                                .groupingBy { it }
                                                .eachCount()
                                                .filter { x-> x.value == customers.size }
                                                .map{x -> x.key}
                                                .toSet()

