package ru.sber.streams


// 1. Используя withIndex() посчитать сумму элементов листа, индекс которых кратен 3. (нулевой индекс тоже входит)
fun getSumWithIndexDivisibleByThree(list: List<Long>): Long {
    return list.withIndex().filter { numbers -> numbers.index % 3 == 0 }.sumOf { it.value }
}

// 2. Используя функцию generateSequence() создать последовательность, возвращающую числа Фибоначчи.
fun generateFibonacciSequence(): Sequence<Int> {
    return generateSequence(Pair(0,1)) { Pair(it.second, it.first + it.second) }.map{it.first}
}

// 3. Получить города, в которых есть покупатели.
fun Shop.getCustomersCities(): Set<City> = customers.map { it.city }.toSet()

// 4. Получить все когда-либо заказанные продукты.
fun Shop.allOrderedProducts(): Set<Product> {
    var setlist = mutableSetOf<Product>()
    customers.forEach { cust ->
        cust.orders.forEach { order ->
            order.products.map { setlist.add(it) }
        }
    }
    return setlist
}

// 5. Получить покупателя, который сделал больше всего заказов.
fun Shop.getCustomerWithMaximumNumberOfOrders(): Customer? = customers.maxByOrNull { it.orders.count() }

// 6. Получить самый дорогой продукт, когда-либо приобретенный покупателем.
fun Customer.getMostExpensiveProduct(): Product? {
    val prodList = orders.map { prod ->
        prod.products.maxByOrNull { it.price }
    }.maxByOrNull { it?.price!! }
    return prodList
}

// 7. Получить соответствие в мапе: город - количество заказанных и доставленных продуктов в данный город.
fun Shop.getNumberOfDeliveredProductByCity(): Map<City, Int> {
    val cityMap = customers.map { ord ->
        ord.city to ord.orders.filter { newords ->
            newords.isDelivered
        }.sumOf { counts ->counts.products.count() }
    }.groupBy { valuses -> valuses.first
    }.mapValues { listslast ->
        listslast.value.sumOf { it.second }
                }
    return cityMap
}

// 8. Получить соответствие в мапе: город - самый популярный продукт в городе.
fun Shop.getMostPopularProductInCity(): Map<City, Product> {
    val bestProduct = customers.groupBy { ord -> ord.city
    }.mapValues {val1 ->
        val1.value
            .flatMap { ord -> ord.orders }
            .flatMap { prod -> prod.products }
            .groupingBy { prod2 -> prod2 }.eachCount()
            .maxByOrNull { it.value }!!.key
    }
    return bestProduct
}

// 9. Получить набор товаров, которые заказывали все покупатели.
fun Shop.getProductsOrderedByAll(): Set<Product> {
    val allBuyers = this.customers.fold(allOrderedProducts()) { value, cust1 ->
        value.intersect(cust1.orders
            .flatMap { it.products }
            .toSet()
        )
    }
    return allBuyers
}

