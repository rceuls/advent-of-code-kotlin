import kotlin.time.measureTime


fun Long.isChonkable() = this.toString().length % 2 == 0

fun Long.chonked() = this.toString().chunked(this.toString().length / 2)

fun main() {
    val dayNumber = "Day11"
    val cache: MutableMap<Pair<Long, Int>, Long> = mutableMapOf()

    fun blink(stone: Long, iteration: Int): Long =
        (stone to iteration).let { kvp ->
            when {
                iteration == 0 -> 1
                kvp in cache -> cache.getValue(kvp)
                else -> {
                    val result = when {
                        stone == 0L -> blink(1, iteration - 1)
                        stone.isChonkable() -> stone.chonked().sumOf { blink(it.toLong(), iteration - 1) }
                        else -> blink(stone * 2024, iteration - 1)
                    }
                    cache[kvp] = result
                    result
                }
            }
        }

    fun part(input: List<Long>, iter: Int): Long = input.sumOf { blink(it, iter) }

    var p1: Long
    var p2: Long
    val timeTakenTotal = measureTime {
        val testData = readInputOneLine("${dayNumber}_test")
            .split(" ")
            .map { it.toLong() }
        val timeTakenTests = measureTime {
            check(part(testData, 25) == 55312L)
        }
        cache.clear()
        "Tests: $timeTakenTests".prettyPrint("#FBD8C6")

        val actualData = readInputOneLine(dayNumber)
            .split(" ")
            .map { it.toLong() }
        val timeTaken = measureTime {
            p1 = part(actualData, 25)
            p2 = part(actualData, 75)
        }
        "Actual: $timeTaken".prettyPrint("#FBD8C6")
    }
    "Total: $timeTakenTotal".prettyPrint("#FBC6CF")
    p1.prettyPrint("#FBC6EA")
    p2.prettyPrint("#FBC6EB")

}