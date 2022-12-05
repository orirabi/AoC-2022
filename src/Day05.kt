data class Order(val from: Int, val to: Int, val amount: Int)

class Crates() {
    /*
    *
        [J]         [B]     [T]
        [M] [L]     [Q] [L] [R]
        [G] [Q]     [W] [S] [B] [L]
[D]     [D] [T]     [M] [G] [V] [P]
[T]     [N] [N] [N] [D] [J] [G] [N]
[W] [H] [H] [S] [C] [N] [R] [W] [D]
[N] [P] [P] [W] [H] [H] [B] [N] [G]
[L] [C] [W] [C] [P] [T] [M] [Z] [W]
 1   2   3   4   5   6   7   8   9
    *
    * */
    val crates: Map<Int, ArrayDeque<String>> = mapOf(
        1 to ArrayDeque(listOf("D", "T", "W", "N", "L")),
        2 to ArrayDeque(listOf("H", "P", "C")),
        3 to ArrayDeque(listOf("J", "M", "G", "D", "N", "H", "P", "W")),
        4 to ArrayDeque(listOf("L", "Q", "T", "N", "S", "W", "C")),
        5 to ArrayDeque(listOf("N", "C", "H", "P")),
        6 to ArrayDeque(listOf("B", "Q", "W", "M", "D", "N", "H", "T")),
        7 to ArrayDeque(listOf("L", "S", "G", "J", "R", "B", "M")),
        8 to ArrayDeque(listOf("T", "R", "B", "V", "G", "W", "N", "Z")),
        9 to ArrayDeque(listOf("L", "P", "N", "D", "G", "W")),
    )

    fun getTops(): String {
        return crates.entries.sortedBy { it.key }.map { it.value.first() }.joinToString("")
    }

    fun runOrder(order: Order) {
        val from = crates[order.from]!!
        val to = crates[order.to]!!
        for (i in 1..order.amount) {
            to.addFirst(from.removeFirst())
        }
    }

    fun runBulkOrder(order: Order) {
        val from = crates[order.from]!!
        val to = crates[order.to]!!
        val temp = ArrayDeque<String>()
        for (i in 1..order.amount) {
            temp.addLast(from.removeFirst())
        }
        to.addAll(0, temp)
    }
}



fun main() {
    fun parseOrder(str: String): Order {
        //move 6 from 6 to 5
        val split = str.split(" ")
        return Order(
            amount = split[1].toInt(),
            from = split[3].toInt(),
            to = split[5].toInt(),
        )
    }

    fun getTopCrates(orders: List<String>): String {
        val crates = Crates()
        orders.asSequence()
            .map(::parseOrder)
            .forEach(crates::runOrder)
        return crates.getTops()
    }

    fun getTopCratesByBulkOrder(orders: List<String>): String {
        val crates = Crates()
        orders.asSequence()
            .map(::parseOrder)
            .forEach(crates::runBulkOrder)
        return crates.getTops()
    }

    val orders = readInput("Day05")
    println(getTopCrates(orders))
    println(getTopCratesByBulkOrder(orders))
}
