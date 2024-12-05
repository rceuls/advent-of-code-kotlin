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
        "Tests: $timeTakenTests".prettyPrint("#FBD8C6")

        val actualData = readInput(dayNumber)
        val timeTaken = measureTime {
            p1 = part1(actualData)
            p2 = part2(actualData)
        }
        "Actual: $timeTaken".prettyPrint("#FBD8C6")
    }
    "Total: $timeTakenTotal".prettyPrint("#FBC6CF")
    p1.prettyPrint("#FBC6EA")
    p2.prettyPrint("#FBC6EB")
}