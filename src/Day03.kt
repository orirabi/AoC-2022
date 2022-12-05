val priorities = generateSequence('a' to 1) {
    when (it.first) {
        'z' -> 'A' to it.second + 1
        'Z' -> null
        else -> it.first + 1 to it.second + 1
    }
}
    .toMap()


fun main() {
    fun getDuplicate(pack: String): Char {
        val compartments = pack.chunked(pack.length / 2)
        check(compartments.size == 2)
        check(compartments[0].length == compartments[1].length)
        val chars = compartments[0].toSet()
        return compartments[1].asSequence()
            .filter { it in chars }
            .first()
    }

    fun getPriority(pack: String): Int {
        return priorities[getDuplicate(pack)]!!
    }

    fun getPriorities(input: List<String>): Int {
        return input.asSequence().map { getPriority(it) }.sum()
    }

    fun findBadge(group: List<String>): Char {
        check(group.size == 3)
        return group.map { it.toSet() }
            .reduce { a: Set<Char>, b: Set<Char> -> a.intersect(b) }
            .single()
    }

    fun getBadgePriority(group: List<String>): Int {
        return priorities[findBadge(group)]!!
    }

    fun getBadges(input: List<String>): Int {
        return input.asSequence().chunked(3)
            .map { getBadgePriority(it) }
            .sum()
    }

    val packs = readInput("Day03")

    println(getPriorities(packs))
    println(getBadges(packs))
}
