package ru.sber.streams

import io.mockk.InternalPlatformDsl.toArray


// 1. Используя withIndex() посчитать сумму элементов листа, индекс которых кратен 3. (нулевой индекс тоже входит)
fun getSumWithIndexDivisibleByThree(list: List<Long>): Long {
    return list.withIndex().filter { it.index % 3 == 0 }
        .reduce { acc, indexedValue -> IndexedValue(0 , acc.value + indexedValue.value) }
        .value
}

// 2. Используя функцию generateSequence() создать последовательность, возвращающую числа Фибоначчи.
fun generateFibonacciSequence(): Sequence<Int> {
    return generateSequence(Pair(0, 1)) { Pair(it.second, it.first + it.second) }.map { it.first }

}

// 3. Получить города, в которых есть покупатели.
fun Shop.getCustomersCities(): Set<City> = this.customers.map{it.city}.toSet()

// 4. Получить все когда-либо заказанные продукты.
fun Shop.allOrderedProducts(): Set<Product> {
    val set = mutableSetOf<Product>()
    this.customers
        .map {customer -> customer.orders
            .map { order -> order.products
                .forEach {product -> set.add(product)
                }
            }
        }
    return set
}

// 5. Получить покупателя, который сделал больше всего заказов.
fun Shop.getCustomerWithMaximumNumberOfOrders(): Customer? {
    val emptyCustomer = Customer("", City(""), emptyList())
    var tempPair: Pair<Customer, Int> = Pair(emptyCustomer, 0)

    fun compareWithCurrentMaxCustomer(currentMax: Pair<Customer, Int>, customer: Customer): Unit{
        if(customer.orders.size > currentMax.second)
            tempPair = Pair(customer, customer.orders.size)
    }

    this.customers.forEach{compareWithCurrentMaxCustomer(tempPair, it)}
    return tempPair.first
}

// 6. Получить самый дорогой продукт, когда-либо приобртенный покупателем.
fun Customer.getMostExpensiveProduct(): Product? {
    return Shop("", listOf(this)).allOrderedProducts()
        .reduce { acc, product ->
            when{
                acc.price < product.price -> product
                else -> acc
            }
        }
}

// 7. Получить соответствие в мапе: город - количество заказанных и доставленных продуктов в данный город.
fun Shop.getNumberOfDeliveredProductByCity(): Map<City, Int> {


    fun getNumberOfProductsByCustomer(customer: Customer): Int{
        return customer.orders.filter { it.isDelivered }.map { order -> order.products.size}
                    .reduceOrNull{ sum, i -> sum + i } ?: 0
    }

    val tempMap : MutableMap<City, Int> = mutableMapOf()
    this.customers.forEach { customer ->
        when (customer.city) {
            in tempMap.keys -> tempMap[customer.city] = tempMap.getValue(customer.city) + getNumberOfProductsByCustomer(customer)
            else -> tempMap[customer.city] = getNumberOfProductsByCustomer(customer)
        }
    }
    return tempMap
}

// 8. Получить соответствие в мапе: город - самый популярный продукт в городе.
fun Shop.getMostPopularProductInCity(): Map<City, Product> {

    fun parseOrder(order: Order, map: MutableMap<Product, Int>?){
        order.products.forEach{
            if (map != null) {
                when(it){
                    in map.keys -> map[it] = map[it]!!.plus(1)
                    else -> map[it] = 1
                }
            }
        }
    }

    fun parseCustomer(customer: Customer, map: MutableMap<City, MutableMap<Product, Int>>){
        when(customer.city){
            in map.keys -> customer.orders.forEach { parseOrder(it, map[customer.city]) }
            else -> {map[customer.city] = mutableMapOf()
                    customer.orders.forEach { parseOrder(it, map[customer.city]) }
                    }
        }
    }

    val tempMap : MutableMap<City, MutableMap<Product, Int>> = mutableMapOf()

    this.customers.forEach { customer -> parseCustomer(customer, tempMap) }

    fun getPopularProduct(map: MutableMap<Product, Int>): Product {
        return map.toList().reduce { acc, pair ->
            when{
                acc.second < pair.second -> pair
                else -> acc
            }
        }.first
    }

    return tempMap.mapValues {getPopularProduct(it.value) }

}

// 9. Получить набор товаров, которые заказывали все покупатели.
fun Shop.getProductsOrderedByAll(): Set<Product> {

    fun getProductsOrderedBy(customer: Customer): MutableSet<Product>{
        val tempSet = mutableSetOf<Product>()
        customer.orders.forEach { order: Order -> tempSet.addAll(order.products) }
        return tempSet
    }

    val tempSet = getProductsOrderedBy(this.customers[0])
    this.customers.forEach{tempSet.retainAll(getProductsOrderedBy(it))}

    return tempSet.toSet()
}

