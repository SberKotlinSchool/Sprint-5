package ru.sber.streams


// 1. Используя withIndex() посчитать сумму элементов листа, индекс которых кратен 3. (нулевой индекс тоже входит)
fun getSumWithIndexDivisibleByThree(list: List<Long>): Long {
    return list.filterIndexed { idx, _ -> idx % 3 == 0 }.sum()
}

// 2. Используя функцию generateSequence() создать последовательность, возвращающую числа Фибоначчи.
fun generateFibonacciSequence(): Sequence<Int> {
    return generateSequence(Pair(0, 1)) { Pair(it.second, it.first + it.second) }.map { it.first }
}

// 3. Получить города, в которых есть покупатели.
fun Shop.getCustomersCities(): Set<City> {
    return this.customers.map { it.city }.toSet()
}

// 4. Получить все когда-либо заказанные продукты.
fun Shop.allOrderedProducts(): Set<Product> {
    return this.customers
            .flatMap { it.orders }
            .flatMap { it.products }
            .toSet()
}

// 5. Получить покупателя, который сделал больше всего заказов.
fun Shop.getCustomerWithMaximumNumberOfOrders(): Customer? {
    return this.customers.reduce { acc, next -> if (acc.orders.size > next.orders.size) acc else next }
}

// 6. Получить самый дорогой продукт, когда-либо приобретенный покупателем.
fun Customer.getMostExpensiveProduct(): Product? {
    return this.orders
            .flatMap { it.products }
            .reduce { acc, next -> if (acc.price > next.price) acc else next }
}

// 7. Получить соответствие в мапе: город - количество заказанных и доставленных продуктов в данный город.
fun Shop.getNumberOfDeliveredProductByCity(): Map<City, Int> {
    return this.customers.groupingBy { customer -> customer.city } // Grouping<Customer, City>
            .fold(0) { accumulator: Int, customer ->
                accumulator + customer.orders
                        .filter { it.isDelivered }//Условие - доставленных
                        .flatMap { it.products }
                        .size
            }
}

// 8. Получить соответствие в мапе: город - самый популярный продукт в городе.
fun Shop.getMostPopularProductInCity(): Map<City, Product> {
    return this.customers.groupBy({ it.city }, { it.orders.flatMap { order -> order.products } })//Map<City, List<List<Product>>>
            .mapValues { map -> map.value.flatten() }//Map<City, List<Product>>
            .filterValues { value -> value.isNotEmpty() }
            .mapValues { map ->
                map.value.groupingBy { product -> product } //Grouping<Product, Product>
                        .eachCount() //Map<Product, Int>
                        .maxByOrNull { it.value }!!.key
            }
}

// 9. Получить набор товаров, которые заказывали все покупатели.
fun Shop.getProductsOrderedByAll(): Set<Product> {
    return this.customers.associateBy({ it }, { it.orders.flatMap { order -> order.products }.toSet() })//Map<Customer, Set<Products>>
            .values.reduce { accumulator: Set<Product>, products -> accumulator.intersect(products) }
}


