import kotlin.time.measureTime

const val EMPTY = '.'

fun main() {
    val dayNumber = "Day08"
    fun part1(input: CharGrid): Int {
        val ghosts: MutableList<Coordinate> = mutableListOf()
        input.createCoordinateGrid()
            .forEach { (_, v) ->
                v.forEach { base ->
                    v.filterNot { base == it }.forEach { other ->
                        val (rowAdj, colAdj) = base.row - other.row to base.col - other.col
                        ghosts.add(Coordinate(base.row + rowAdj, base.col + colAdj))
                    }
                }
            }
        return ghosts.filter { input.getOrNull(it.row)?.getOrNull(it.col) != null }.toSet().size
    }

    fun part2(input: CharGrid): Int {
        val ghosts: MutableList<Coordinate> = mutableListOf()
        input.createCoordinateGrid().filter { (_, lst) -> lst.size > 1 }
            .forEach { (_, v) ->
                v.forEach { base ->
                    v.filterNot { base == it }.forEach { other ->
                        val (rowAdj, colAdj) = base.row - other.row to base.col - other.col
                        for(i in input.indices) {
                            val newCoodinate = Coordinate(base.row + (rowAdj * i), base.col + (colAdj * i))
                            if (newCoodinate.row in input.indices && newCoodinate.col in input[0].indices) {
                                ghosts.add(newCoodinate)
                            }
                        }
                    }
                }
            }
        return ghosts.toSet().size
    }

    var p1: Int
    var p2: Int
    val timeTakenTotal = measureTime {
        val testData = readInputCharGrid("${dayNumber}_test")
        val timeTakenTests = measureTime {
            check(part1(testData) == 14)
            check(part2(testData) == 34)
        }
        "Tests: $timeTakenTests".prettyPrint("#FBD8C6")

        val actualData = readInputCharGrid(dayNumber)
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