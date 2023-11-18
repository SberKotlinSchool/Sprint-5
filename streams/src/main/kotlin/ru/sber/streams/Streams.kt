package ru.sber.streams


// 1. Используя withIndex() посчитать сумму элементов листа, индекс которых кратен 3. (нулевой индекс тоже входит)
fun getSumWithIndexDivisibleByThree(list: List<Long>): Long {
    return list.withIndex().filter { it.index % 3 == 0 }.sumOf { it.value }
}

// 2. Используя функцию generateSequence() создать последовательность, возвращающую числа Фибоначчи.  0, 1, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89, 144, 233, 377...
fun generateFibonacciSequence(): Sequence<Int> {
    return generateSequence(Pair(0, 1), { Pair(it.second, it.first + it.second) }).map { it.first }
}

// 3. Получить города, в которых есть покупатели.
fun Shop.getCustomersCities(): Set<City> = customers.map { it.city }.toSet()

// 4. Получить все когда-либо заказанные продукты.
fun Shop.allOrderedProducts(): Set<Product> = customers.flatMap { it.orders.flatMap { it.products } }.toSet()

// 5. Получить покупателя, который сделал больше всего заказов.
fun Shop.getCustomerWithMaximumNumberOfOrders(): Customer? = customers.maxWithOrNull(Comparator.comparing { it.orders.size })

// 6. Получить самый дорогой продукт, когда-либо приобртенный покупателем.
fun Customer.getMostExpensiveProduct(): Product? = orders.flatMap { it.products }.maxWithOrNull(Comparator.comparing { it.price })

// 7. Получить соответствие в мапе: город - количество заказанных и доставленных продуктов в данный город.
fun Shop.getNumberOfDeliveredProductByCity(): Map<City, Int> =
    customers
        .groupBy { it.city }
        .mapValues { (key, value) ->
            value
                .flatMap { it.orders }
                .filter { it.isDelivered }
                .sumOf { it.products.size }
        }

// 8. Получить соответствие в мапе: город - самый популярный продукт в городе. city-product, city-product:kolvo, city-
fun Shop.getMostPopularProductInCity(): Map<City, Product> {

    // город - все товары
    // город - товар-колво
    // город - макс колво
    return customers
        .groupBy({ it.city })
        .mapValues { (key, value) ->
            value
                .flatMap { it.orders }
                .flatMap { it.products }
                .groupingBy { it }
                .eachCount()
                .maxByOrNull { it.value }!!.key
        }
}

// 9. Получить набор товаров, которые заказывали все покупатели.
fun Shop.getProductsOrderedByAll(): Set<Product> =
    customers
        .map {it to it.orders.flatMap { it.products }} // Покупатель - List<Продукт>
        .flatMap { it.second.map { product -> product to it.first } } // Покупатель - Продукт
        .groupingBy { it.first }.eachCount() // Продукт - кол-во покупателей
        .filter { it.value == customers.size }
        .map { it.key }.toSet()
