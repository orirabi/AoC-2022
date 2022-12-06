fun main() {

    fun CharArray.isDistinct(i: Int, requiredCount: Int): Boolean {
        if (i < requiredCount - 1) {
            return false
        }

        return generateSequence(i to this[i]) { it.first - 1 to this[it.first - 1] }
            .take(requiredCount)
            .map { it.second }
            .distinct()
            .count() == requiredCount
    }

    fun CharArray.isMarker(i: Int): Boolean = isDistinct(i, 4)

    fun CharArray.isMessage(i: Int): Boolean = isDistinct(i, 14)

    fun getFirstMarkerEnd(input: String): Int {
        val chars = input.toCharArray()
        for (i in chars.indices) {
            if (chars.isMarker(i)) return i + 1
        }
        throw IllegalStateException()
    }

    fun getFirstMessageEnd(input: String): Int {
        val chars = input.toCharArray()
        for (i in chars.indices) {
            if (chars.isMessage(i)) return i + 1
        }
        throw IllegalStateException()
    }

    val input = readInput("Day06").single()
    println(getFirstMarkerEnd(input))
    println(getFirstMessageEnd(input))
}
