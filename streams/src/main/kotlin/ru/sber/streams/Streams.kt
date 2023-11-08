package ru.sber.streams

fun main(){

    val shop = Shop("Test shop", listOf(
        Customer("Alex", Moscow, listOf(Order(listOf(water, mango)), Order(listOf(bag)))),
        Customer("Mary", Tula, listOf(Order(listOf(chocolate, bag)))),
        Customer("Ivan", Perm, listOf(Order(listOf(water, bag)))),
    ))

    println(shop.getProductsOrderedByAll())

}
// 1. Используя withIndex() посчитать сумму элементов листа, индекс которых кратен 3. (нулевой индекс тоже входит)
fun getSumWithIndexDivisibleByThree(list: List<Long>) : Long =
    list
        .withIndex( )
        .filter { indexedValue -> indexedValue.index % 3 == 0 }
        .sumOf { indexedValue -> indexedValue.value }


// 2. Используя функцию generateSequence() создать последовательность, возвращающую числа Фибоначчи.
fun generateFibonacciSequence(): Sequence<Int> =
    generateSequence( 0 to 1 ) { it.second to ( it.first + it.second ) }.map { it.first }

// 3. Получить города, в которых есть покупатели.
fun Shop.getCustomersCities(): Set<City> = customers.map{ it.city }.toSet()
// 4. Получить все когда-либо заказанные продукты.
fun Shop.allOrderedProducts(): Set<Product> = customers.flatMap{ it.orders }.flatMap { it.products }.toSet()
// 5. Получить покупателя, который сделал больше всего заказов.
fun Shop.getCustomerWithMaximumNumberOfOrders(): Customer? = customers.maxByOrNull { it.orders.size }

// 6. Получить самый дорогой продукт, когда-либо приобретенный покупателем.
fun Customer.getMostExpensiveProduct(): Product? = orders.flatMap { it.products }.maxByOrNull { it.price }

// 7. Получить соответствие в мапе: город - количество заказанных и доставленных продуктов в данный город.
fun Shop.getNumberOfDeliveredProductByCity(): Map<City, Int> =
    customers
        .groupBy { it.city }
        .mapValues { ( _, value ) -> value
            .flatMap { it.orders }
            .filter{ it.isDelivered }
            .sumOf { it.products.size }  }

// 8. Получить соответствие в мапе: город - самый популярный продукт в городе.
fun Shop.getMostPopularProductInCity(): Map<City, Product> =
    customers
        .groupBy { it.city }
        .mapValues { ( _, value ) -> value
            .flatMap { it.orders }
            .flatMap { it.products }
            .groupingBy { it }
            .eachCount()
            .maxByOrNull { it.value }!!.key }

// 9. Получить набор товаров, которые заказывали все покупатели.
fun Shop.getProductsOrderedByAll(): Set<Product> =
    customers
        .groupBy( { it }, { it.orders.flatMap { it.products } } )
        .mapValues { it.value.flatten().toSet() }
        .values
        .reduce { a, b -> a.intersect(b) }


