package ru.sber.streams


// 1. Используя withIndex() посчитать сумму элементов листа, индекс которых кратен 3. (нулевой индекс тоже входит)
fun getSumWithIndexDivisibleByThree(list: List<Long>): Long {
    return list.withIndex().filterIndexed { index, _ -> index % 3 == 0 }.sumOf { it.value }
}

// 2. Используя функцию generateSequence() создать последовательность, возвращающую числа Фибоначчи.
fun generateFibonacciSequence(): Sequence<Int> {
    return generateSequence(0 to 1) { it.second to it.first + it.second }.map { it.first }
}

// 3. Получить города, в которых есть покупатели.
fun Shop.getCustomersCities(): Set<City> = this
    .customers
    .filter { it.orders.isNotEmpty() }
    .map { it.city }
    .toSet()

// 4. Получить все когда-либо заказанные продукты.
fun Shop.allOrderedProducts(): Set<Product> = this
    .customers
    .flatMap { customer ->
        customer.orders.flatMap { order -> order.products }
    }
    .toSet()

// 5. Получить покупателя, который сделал больше всего заказов.
fun Shop.getCustomerWithMaximumNumberOfOrders(): Customer? = this.customers.maxByOrNull { it.orders.size }

// 6. Получить самый дорогой продукт, когда-либо приобртенный покупателем.
fun Customer.getMostExpensiveProduct(): Product? = this
    .orders
    .flatMap { it.products }
    .maxByOrNull { it.price }

// 7. Получить соответствие в мапе: город - количество заказанных и доставленных продуктов в данный город.
fun Shop.getNumberOfDeliveredProductByCity(): Map<City, Int> = this
    .customers
    .groupingBy { it.city }
    .fold(0) { accumulator, element ->
        val sumOfDeliveredOrders = element.orders.filter { it.isDelivered }.sumOf { it.products.size }
        accumulator + sumOfDeliveredOrders
    }

// 8. Получить соответствие в мапе: город - самый популярный продукт в городе.
fun Shop.getMostPopularProductInCity(): Map<City, Product> = this
    .customers
    .groupBy({ it.city }) { customer ->
        customer.orders.flatMap { order -> order.products }
    }
    .mapValues { (city, products) -> // city можно заменить на _ т.к. он нигде не используется,
        products                    // явно написал для лучшей читаемости
            .flatten()
            .groupingBy { product -> product }
            .eachCount()
            .maxByOrNull { (product, count) -> count }!! // аналогично city
            .key
    }

// 9. Получить набор товаров, которые заказывали все покупатели.
fun Shop.getProductsOrderedByAll(): Set<Product> = this
    .customers
    .map { customer -> customer.orders.flatMap { it.products }.toSet() }
    .reduce { accum, next -> accum.intersect(next) }
    .toSet()