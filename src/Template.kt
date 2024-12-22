fun main() {
    val dayNumber = "Day0X"

    fun part1(input: List<String>): Int = input.size
    fun part2(input: List<String>): Int = input.size

    val testData = readInput("${dayNumber}_test")
    check(part1(testData) == 0)
    check(part2(testData) == 0)
    val actualData = readInput(dayNumber)
    part1(actualData).println()
    part2(actualData).println()
}
