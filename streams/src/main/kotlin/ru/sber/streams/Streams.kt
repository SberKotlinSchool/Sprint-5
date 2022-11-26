package ru.sber.streams


// 1. Используя withIndex() посчитать сумму элементов листа, индекс которых кратен 3. (нулевой индекс тоже входит)
fun getSumWithIndexDivisibleByThree(list: List<Long>): Long =
    list.withIndex().sumOf { (index, value) -> if (index % 3 == 0) value else 0 }

// 2. Используя функцию generateSequence() создать последовательность, возвращающую числа Фибоначчи.
fun generateFibonacciSequence(): Sequence<Int> =
    generateSequence(0 to 1) { it.second to (it.first + it.second) }
        .map { it.first }

// 3. Получить города, в которых есть покупатели.
fun Shop.getCustomersCities(): Set<City> =
    customers.asSequence()
        .map { it.city }
        .toSet()

// 4. Получить все когда-либо заказанные продукты.
fun Shop.allOrderedProducts(): Set<Product> =
    customers.asSequence()
        .flatMap { it.orders }
        .flatMap { it.products }
        .toSet()

// 5. Получить покупателя, который сделал больше всего заказов.
fun Shop.getCustomerWithMaximumNumberOfOrders(): Customer? =
    customers.maxByOrNull { it.orders.size }

// 6. Получить самый дорогой продукт, когда-либо приобртенный покупателем.
fun Customer.getMostExpensiveProduct(): Product? =
    orders.asSequence()
        .flatMap { it.products }
        .maxByOrNull { it.price }

// 7. Получить соответствие в мапе: город - количество заказанных и доставленных продуктов в данный город.
fun Shop.getNumberOfDeliveredProductByCity(): Map<City, Int> =
    customers.groupingBy { it.city }
        .fold(0) { sum, customer ->
            val deliveredProducts = customer.orders.asSequence()
                .filter { it.isDelivered }
                .flatMap { it.products }
                .count()

            sum + deliveredProducts
        }

// 8. Получить соответствие в мапе: город - самый популярный продукт в городе.
fun Shop.getMostPopularProductInCity(): Map<City, Product> =
    customers.groupingBy { it.city }
        .fold(emptyList<Product>()) { products, customer ->
            products + customer.orders.flatMap { it.products }
        }
        .asSequence()
        .mapNotNull { (city, products) ->
            products.groupBy { it }
                .maxByOrNull { (_, products) -> products.count() }
                ?.let { city to it.key }
        }
        .toMap()

// 9. Получить набор товаров, которые заказывали все покупатели.
fun Shop.getProductsOrderedByAll(): Set<Product> =
    customers.asSequence()
        .flatMap { it.orders }
        .flatMap { it.products }
        .groupBy { it }
        .map { (product, entries) -> product to entries.count() }
        .filter { (_, count) -> count == customers.size }
        .map { (product, _) -> product }
        .toSet()

