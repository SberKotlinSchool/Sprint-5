package ru.sber.streams


// 1. Используя withIndex() посчитать сумму элементов листа, индекс которых кратен 3. (нулевой индекс тоже входит)
fun getSumWithIndexDivisibleByThree(list: List<Long>): Long {
    return list.filterIndexed { idx, value -> idx % 3 == 0 }.sum()
}

// 2. Используя функцию generateSequence() создать последовательность, возвращающую числа Фибоначчи.
fun generateFibonacciSequence(): Sequence<Int> {
    return generateSequence(Pair(0, 1)) { Pair(it.second, it.first + it.second) }.map { it.first }
}

// 3. Получить города, в которых есть покупатели.
fun Shop.getCustomersCities(): Set<City> = this.customers.map { it.city }.toSet()

// 4. Получить все когда-либо заказанные продукты.
fun Shop.allOrderedProducts(): Set<Product> = this.customers
    .flatMap { it.orders.flatMap { it.products } }.toSet()

// 5. Получить покупателя, который сделал больше всего заказов.
fun Shop.getCustomerWithMaximumNumberOfOrders(): Customer? = this.customers
    .maxByOrNull { it.orders.size }

// 6. Получить самый дорогой продукт, когда-либо приобртенный покупателем.
fun Customer.getMostExpensiveProduct(): Product? = this.orders
    .flatMap { it.products }
    .maxByOrNull { it.price }

// 7. Получить соответствие в мапе: город - количество заказанных и доставленных продуктов в данный город.
fun Shop.getNumberOfDeliveredProductByCity(): Map<City, Int> = this.customers
    .map {
        Pair(
            it.city,
            it.orders.filter { it.isDelivered }
                .flatMap { it.products }
                .size)
    }
    .groupBy { it.first }.map { Pair(it.key, it.value.map { it.second }.sum()) }
    .toMap()

// 8. Получить соответствие в мапе: город - самый популярный продукт в городе.
fun Shop.getMostPopularProductInCity(): Map<City, Product> = this.customers
    .map { Pair(it.city, it.orders.flatMap { it.products }.groupingBy { it }.eachCount()) }
    .groupBy { it.first }
    .map {
        Pair(it.key, it.value.map { it.second }.flatMap { it.entries }
            .fold(mutableMapOf<Product, Int>()) { acc, v ->
                if (acc.contains(v.key)) {
                    acc[v.key] = acc[v.key]!! + v.value
                } else {
                    acc[v.key] = v.value
                }
                acc
            }
        )
    }
    .fold(mutableMapOf()) { acc, v ->
        val maxEntry = v.second.maxByOrNull { it.value }
        acc[v.first] = maxEntry!!.key
        acc
    }


// 9. Получить набор товаров, которые заказывали все покупатели.
fun Shop.getProductsOrderedByAll(): Set<Product> = this.customers
    .fold(mutableMapOf<Customer, MutableSet<Product>>()) { acc, v ->
        val productsOfOneOrder = v.orders.flatMap { it.products }.toMutableSet()
        if (acc.contains(v)) {
            acc[v]!!.addAll(productsOfOneOrder)
        } else {
            acc[v] = productsOfOneOrder
        }
        acc
    }
    .values
    .fold(mutableSetOf<Product>()) { acc, v ->
        if (acc.isEmpty()) acc.addAll(v.toMutableSet())
        else {
            val conjunction = acc intersect v.toMutableSet()
            acc.clear()
            acc.addAll(conjunction)
        }
        acc
    }


fun main() {
    val list = listOf(mapOf("a" to 3), mapOf("a" to 2, "c" to 1))
    val res = list.fold(mutableMapOf<String, Int>(), { acc, v ->
        v.forEach { (key, value) ->
            if (acc.contains(key)) {
                acc[key] = acc[key]!! + value
            } else {
                acc[key] = value
            }
        }
        acc
    })
    print(res)
}
