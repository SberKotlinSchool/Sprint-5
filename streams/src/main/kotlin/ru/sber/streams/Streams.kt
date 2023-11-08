package ru.sber.streams


// 1. Используя withIndex() посчитать сумму элементов листа, индекс которых кратен 3. (нулевой индекс тоже входит)
fun getSumWithIndexDivisibleByThree(list: List<Long>): Long {
    var sum = 0L
    list.withIndex().forEach { (index, value) ->
        if (index % 3 == 0) {
            sum += value
        }
    }
    return sum
}

// 2. Используя функцию generateSequence() создать последовательность, возвращающую числа Фибоначчи.
fun generateFibonacciSequence(): Sequence<Int> {
    return sequence {
        var terms = Pair(0, 1)
        while (true) {
            yield(terms.first)
            terms = Pair(terms.second, terms.first + terms.second)
        }
    }
}

// 3. Получить города, в которых есть покупатели.
fun Shop.getCustomersCities(): Set<City> = this.customers.map { it.city }.distinct().toSet()

// 4. Получить все когда-либо заказанные продукты.
fun Shop.allOrderedProducts(): Set<Product> = this.customers.flatMap { it.orders }.flatMap { it.products }.toSet()

// 5. Получить покупателя, который сделал больше всего заказов.
fun Shop.getCustomerWithMaximumNumberOfOrders(): Customer? = this.customers.maxByOrNull { it.orders.size }!!

// 6. Получить самый дорогой продукт, когда-либо приобретенный покупателем.
fun Customer.getMostExpensiveProduct(): Product? = this.orders.flatMap { it.products }.maxByOrNull { it.price }

// 7. Получить соответствие в мапе: город - количество заказанных и доставленных продуктов в данный город.
fun Shop.getNumberOfDeliveredProductByCity(): Map<City, Int> {
    val map = HashMap<City, Int>()
    this.customers.forEach { customer ->
        val count = (map[customer.city] ?: 0) + customer.orders
            .asSequence()
            .filter { it.isDelivered }
            .sumOf { it.products.size }
        map[customer.city] = count
    }
    return map
}

// 8. Получить соответствие в мапе: город - самый популярный продукт в городе.
fun Shop.getMostPopularProductInCity(): Map<City, Product> {
    return this.customers.groupBy { it.city }.mapValues { cityListCustomerMap ->
        cityListCustomerMap.value.flatMap { it.orders }
            .flatMap { it.products }
            .groupBy { it }
            .maxByOrNull { it.value.size }!!.key
    }
}

// 9. Получить набор товаров, которые заказывали все покупатели.
fun Shop.getProductsOrderedByAll(): Set<Product> {
    if (customers.isEmpty()) {
        return emptySet()
    }
    val resultSet: MutableSet<Product> = customers[0].getCustomerProducts()
    customers
        .map { it.getCustomerProducts() }
        .forEach { customerProductSet -> resultSet.removeIf { !customerProductSet.contains(it) } }
    return resultSet
}

fun Customer.getCustomerProducts(): MutableSet<Product> {
    return this.orders.flatMap { it.products }.distinct().toMutableSet()
}

