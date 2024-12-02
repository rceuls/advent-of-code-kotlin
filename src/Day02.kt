import kotlin.math.abs

fun main() {
    fun isValidForPart1(it: List<Int>): Boolean {
        val (first, second, third) = it
        val firstSub = first - second
        val secondSub = second - third
        val isIncOrDec = ((firstSub > 0 && secondSub > 0) || (firstSub < 0 && secondSub < 0))
        val isGoodInterval = abs(firstSub) in 1..3 && abs(secondSub) in 1..3
        return isIncOrDec && isGoodInterval
    }

    fun part1(input: List<String>): Int = input.count { line ->
        line.toNumberArray().windowed(3).all { isValidForPart1(it) }
    }

    fun part2(input: List<String>): Int = input.count { line ->
        val numberArray = line.toNumberArray()
        numberArray.windowed(3).all { isValidForPart1(it) } ||
                numberArray.indices.any { index ->
                    (numberArray.take(index) + numberArray.drop(index + 1))
                        .windowed(3)
                        .all { isValidForPart1(it) }
                }
    }

    val testData = readInput("Day02_test")
    check(part1(testData) == 2)
    check(part2(testData) == 4)

    val actualData = readInput("Day02")
    part1(actualData).println()
    part2(actualData).println()
}