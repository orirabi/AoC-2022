fun main() {
    fun parseSingleAssignment(str: String): Pair<Int, Int> {
        val assignment = str.split("-")
        check(assignment.size == 2)

        return assignment.first().toInt() to assignment.last().toInt()
    }

    fun parseToPairs(str: String): Pair<Pair<Int, Int>, Pair<Int, Int>> {
        val assignments = str.split(",")
        check(assignments.size == 2)

        val assignment1 = assignments.first()
        val assignment2 = assignments.last()

        return parseSingleAssignment(assignment1) to parseSingleAssignment(assignment2)
    }

    fun Pair<Int, Int>.fullyContains(that: Pair<Int, Int>): Boolean {
        return this.first <= that.first && this.second >= that.second
    }

    fun getContainingCount(pairs: List<String>): Int {
        return pairs.asSequence()
            .map { parseToPairs(it) }
            .filter { it.first.fullyContains(it.second) || it.second.fullyContains(it.first) }
            .count()
    }

    fun Pair<Int, Int>.has(i: Int): Boolean {
        return this.first <= i && this.second >= i
    }

    fun Pair<Int, Int>.anyOverlap(that: Pair<Int, Int>): Boolean {
        return this.has(that.first) || this.has(that.second)
    }

    fun getOverlappingCount(pairs: List<String>): Int {
        return pairs.asSequence()
            .map { parseToPairs(it) }
            .filter { it.first.anyOverlap(it.second) || it.second.anyOverlap(it.first) }
            .count()
    }

    val allAssignments = readInput("Day04")

    println(getContainingCount(allAssignments))
    println(getOverlappingCount(allAssignments))
}
