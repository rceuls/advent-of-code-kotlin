import kotlin.time.measureTime

fun main() {
    val dayNumber = "Day0X"

    fun part1(input: List<String>): Int = input.size
    fun part2(input: List<String>): Int = input.size

    var p1: Int
    var p2: Int
    val timeTakenTotal = measureTime {
        val testData = readInput("${dayNumber}_test")
        val timeTakenTests = measureTime {
            check(part1(testData) == 0)
            check(part2(testData) == 0)
        }
        "Tests: $timeTakenTests".println()

        val actualData = readInput(dayNumber)
        val timeTaken = measureTime {
            p1 = part1(actualData)
            p2 = part2(actualData)
        }
        "Actual: $timeTaken".println()
    }
    "Total: $timeTakenTotal".println()
    p1.println()
    p2.println()
}