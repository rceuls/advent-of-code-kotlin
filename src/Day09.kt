import kotlin.time.measureTime

fun main() {
    val dayNumber = "Day09"

    fun convertInput(input: String): MutableList<Long> {
        var currDigit = 0L
        val line = mutableListOf<Long>()
        input.map { it.digitToInt() }.forEachIndexed { i, c ->
            if (i % 2 == 0) {
                line += (0..<c).map { currDigit }
                currDigit++
            } else {
                line += (0..<c).map { -1 }
            }
        }
        return line
    }

    fun part1(input: String): Long {
        val line = convertInput(input)
        line.indices.forEach { i ->
            if (i < line.size) {
                if (line[i] == -1L) {
                    var last = line.removeLast()
                    while (last == -1L)
                        last = line.removeLast()
                    line[i] = last
                }
            }
        }
        return line.mapIndexed { index, c -> index * c }.sumOf { it }
    }

    fun part2(input: String): Long {
//        val line = convertInput(input)
//        while (true) {
//            var grandOffset = 0
//            val previous = line.toList()
//            while (true) {
//                var reversed = line.reversed().drop(grandOffset)
//                if (reversed.none { it == -1L }) {
//                    break
//                }
//                var firstIndexFill = reversed.indexOfFirst { it != -1L }
//                if (firstIndexFill > 0) {
//                    reversed = reversed.drop(firstIndexFill)
//                    grandOffset += firstIndexFill
//                }
//                firstIndexFill = reversed.indexOfFirst { it != -1L }
//                val lastIndexFill = reversed.indexOfFirst { it != reversed[firstIndexFill] }
//
//                var offset = 0
//
//                while (true) {
//                    val subLine = line.drop(offset)
//                    var firstIndexEmpty = subLine.indexOf(-1L)
//                    var lastIndexEmpty = subLine.drop(firstIndexEmpty).indexOfFirst { it != -1L } + firstIndexEmpty
//                    firstIndexEmpty += offset
//                    lastIndexEmpty += offset
//                    if (abs(firstIndexEmpty - lastIndexEmpty) < abs(lastIndexFill - firstIndexFill)) {
//                        offset = lastIndexEmpty
//                        if (offset > subLine.size) {
//                            break
//                        }
//                    } else {
//                        for (i in 0 until (lastIndexFill - firstIndexFill)) {
//                            line[i + firstIndexEmpty] = reversed[firstIndexFill]
//                            line[line.size - firstIndexFill - i - grandOffset - 1] = -1
//                        }
//                        break
//                    }
//                }
//                grandOffset += abs(firstIndexFill - lastIndexFill)
//                if (grandOffset == line.size) {
//                    break
//                }
//            }
//            if (line == previous) {
//                break
//            }
//        }
        return 0L
    }

    var p1: Long
    var p2: Long
    val timeTakenTotal = measureTime {
        val testData = readInputOneLine("${dayNumber}_test")
        val timeTakenTests = measureTime {
            check(part1(testData) == 1928L)
            check(part2(testData) == 2858L)
        }
        "Tests: $timeTakenTests".prettyPrint("#FBD8C6")

        val actualData = readInputOneLine(dayNumber)
        val timeTaken = measureTime {
            p1 = part1(actualData)
            p2 = part2(actualData)
        }
        "Actual: $timeTaken".prettyPrint("#FBD8C6")
    }
    "Total: $timeTakenTotal".prettyPrint("#FBC6CF")
// 1239952463 too low
    p1.prettyPrint("#FBC6EA")
    p2.prettyPrint("#FBC6EB")
}