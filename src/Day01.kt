fun main() {
    fun getMaxCalories(input: List<String>): Int {
        var highest = 1 to 0
        var current = 1
        var currentSum = 0
        input.forEach {
            if (it.isBlank()) {
                if (currentSum > highest.second) {
                    highest = current to currentSum
                }
                current++
                currentSum = 0
            } else {
                currentSum += it.toInt()
            }
        }
        return highest.second
    }

    fun getMaxCaloriesByThree(input: List<String>): Int {
        val calorieCounts = mutableListOf<Int>()
        var currentSum = 0
        input.forEach {
            if (it.isBlank()) {
                calorieCounts.add(currentSum)
                currentSum = 0
            } else {
                currentSum += it.toInt()
            }
        }
        return calorieCounts.asSequence().sortedDescending().take(3).sum()
    }

    val calorieList = readInput("Day01")
    println(getMaxCalories(calorieList))
    println(getMaxCaloriesByThree(calorieList))
}
