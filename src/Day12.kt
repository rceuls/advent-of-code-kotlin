import kotlin.time.measureTime

fun main() {
    val dayNumber = "Day12"

    fun gridAsCoords(input: CharGrid) = input.flatMapIndexed { rowIndex, row ->
        row.mapIndexed { colIndex, value ->
            Coordinate(rowIndex, colIndex) to value
        }
    }

    fun assembleItems(
        pair: Pair<Coordinate, Char>, input: CharGrid, accumulator: MutableList<Pair<Coordinate, Char>>
    ): List<Pair<Coordinate, Char>> {
        val (c, value) = pair
        val coords = c.neighbours().filter { input.getOrNull(it.row)?.getOrNull(it.col) != null }
            .map { it to input[it.row][it.col] }.filter { it.second == value && it !in accumulator }
        if (coords.isEmpty()) {
            return accumulator
        } else {
            accumulator += coords
            return coords.flatMap { assembleItems(it, input, accumulator) }
        }
    }


    fun getEdges(vis: Set<Coordinate>): Int =
        vis.sumOf { v -> 4 - v.neighbours().count { neighbour -> neighbour in vis } }

    fun part1(input: CharGrid): Int {
        val coords = gridAsCoords(input)
        val visited = mutableSetOf<Coordinate>()
        val groups: MutableList<Triple<Char, Int, Int>> = mutableListOf()
        for (c in coords) {
            if (c.first !in visited) {
                val vis = assembleItems(c, input, mutableListOf(c)).map { it.first }.toSet()
                visited += vis
                groups.add(Triple(c.second, vis.size, getEdges(vis)))
            }
        }
        return groups.sumOf { it.second * it.third }
    }

    fun assembleEdges(area: Set<Coordinate>, input: CharGrid): Int {
        var count = 0
        for (a in area) {
            val base = input[a.row][a.col]
            val samesies =
                listOf(
                    Compass.N,
                    Compass.S,
                    Compass.E,
                    Compass.W,
                    Compass.NW,
                    Compass.NE,
                    Compass.SW
                )
                    .associateBy({ it }, { (input.getOrNull(a.row + it.row)?.getOrNull(a.col + it.col) == base) })


            if (!samesies.getValue(Compass.N) && !(samesies.getValue(Compass.W) && !samesies.getValue(Compass.NW)))
                count += 1
            if (!samesies.getValue(Compass.W) && !(samesies.getValue(Compass.N) && !samesies.getValue(Compass.NW)))
                count += 1
            if (!samesies.getValue(Compass.E) && !(samesies.getValue(Compass.N) && !samesies.getValue(Compass.NE)))
                count += 1
            if (!samesies.getValue(Compass.S) && !(samesies.getValue(Compass.W) && !samesies.getValue(Compass.SW)))
                count += 1
        }
        return count
    }

    fun part2(input: CharGrid): Int {
        val coords = gridAsCoords(input)
        val visited = mutableSetOf<Coordinate>()
        val groups: MutableList<Triple<Char, Int, Int>> = mutableListOf()
        for (c in coords) {
            if (c.first !in visited) {
                val vis = assembleItems(c, input, mutableListOf(c)).map { it.first }.toSet()
                visited += vis
                groups.add(Triple(c.second, vis.size, assembleEdges(vis, input)))
            }
        }
        return groups.sumOf { it.second * it.third }
    }

    var p1: Int
    var p2: Int
    val timeTakenTotal = measureTime {
        val testData = readInputCharGrid("${dayNumber}_test")
        val timeTakenTests = measureTime {
            check(part1(testData) == 1930)
            part2(testData).println()
            check(part2(testData) == 1206)
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