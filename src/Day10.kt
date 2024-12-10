import kotlin.time.measureTime

fun main() {
    val dayNumber = "Day10"

    fun Coordinate.calculateScore(
        grid: Map<Coordinate, Int>,
        distinct: Boolean,
        visited: MutableSet<Coordinate> = mutableSetOf(),
    ): Int {
        if (distinct) {
            if (this in visited) return 0
            visited.add(this)
        }
        if (grid[this] == 9) return 1

        return neighbours().filter {
            grid.getValue(it) - grid.getValue(this) == 1
        }.sumOf { it.calculateScore(grid, distinct, visited) }
    }

    fun part1(input: IntGrid): Int {
        val grid = input.toMappedGrid()
        return grid.filter { it.value == 0 }.toList().sumOf { it.first.calculateScore(grid, true) }
    }

    fun part2(input: IntGrid): Int {
        val grid = input.toMappedGrid()
        return grid.filter { it.value == 0 }.toList().sumOf { it.first.calculateScore(grid, false) }
    }

    var p1: Int
    var p2: Int
    val timeTakenTotal = measureTime {
        val testData = readInputIntGrid("${dayNumber}_test")
        val timeTakenTests = measureTime {
            check(part1(testData) == 36)
            check(part2(testData) == 81)
        }
        "Tests: $timeTakenTests".prettyPrint("#FBD8C6")

        val actualData = readInputIntGrid(dayNumber)
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