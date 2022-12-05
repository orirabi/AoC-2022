
val calc = mapOf(
    Choice.ROCK to mapOf(
        Choice.ROCK to Result.DRAW,
        Choice.PAPER to Result.LOSS,
        Choice.SCISSORS to Result.WIN,
    ),
    Choice.PAPER to mapOf(
        Choice.ROCK to Result.WIN,
        Choice.PAPER to Result.DRAW,
        Choice.SCISSORS to Result.LOSS,
    ),
    Choice.SCISSORS to mapOf(
        Choice.ROCK to Result.LOSS,
        Choice.PAPER to Result.WIN,
        Choice.SCISSORS to Result.DRAW,
    ),
)


enum class Result {
    WIN {
        override fun getPoints(): Int = 6
    },
    LOSS{
        override fun getPoints(): Int = 0
    },
    DRAW{
        override fun getPoints(): Int = 3
    };

    abstract fun getPoints(): Int

    companion object {
        fun parse(s: String): Result {
            return when (s) {
                "X" -> LOSS
                "Y" -> DRAW
                "Z" -> WIN
                else -> throw IllegalStateException("Cannot parse $s")
            }
        }
    }
}

enum class Choice {
    ROCK {
        override fun getPoints(): Int = 1
    },
    PAPER {
        override fun getPoints(): Int = 2
    },
    SCISSORS {
        override fun getPoints(): Int = 3
    };

    companion object {
        fun parse(s: String): Choice {
            return when (s) {
                "A" -> ROCK
                "B" -> PAPER
                "C" -> SCISSORS
                "X" -> ROCK
                "Y" -> PAPER
                "Z" -> SCISSORS
                else -> throw IllegalStateException("Cannot parse $s")
            }
        }
    }

    fun against(that: Choice): Result {
        return calc[this]!![that]!!
    }
    fun forResult(result: Result): Choice {
        if (result == Result.DRAW) {
            return this
        }
        return calc[this]!!.filter { it.value != result && it.value != Result.DRAW }.keys.single()
    }
    abstract fun getPoints(): Int
}

fun main() {
    fun getStrategyResult1(input: List<String>): Int {
        return input.asSequence()
            .map {
                val split = it.split(" ")
                val opponent = Choice.parse(split.first())
                val mine = Choice.parse(split.last())
                val result = mine.against(opponent)
                mine.getPoints() + result.getPoints()
            }
            .sum()
    }

    fun getStrategyResult2(input: List<String>): Int {
        return input.asSequence()
            .map {
                val split = it.split(" ")
                val opponent = Choice.parse(split.first())
                val result = Result.parse(split.last())
                val myPlay = opponent.forResult(result)
                val points = myPlay.getPoints() + result.getPoints()
                points
            }
            .sum()
    }

    val strategy = readInput("Day02")

    println(getStrategyResult1(strategy))
    println(getStrategyResult2(strategy))
}
