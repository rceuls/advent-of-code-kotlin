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

    fun checkCorners(input: CharGrid, a: Coordinate): Int {
        val base = input[a.row][a.col]

        val nb = mapOf(
            Compass.N to input.getOrNull(a.row + Compass.N.row)?.getOrNull(a.col + Compass.N.col),
            Compass.S to input.getOrNull(a.row + Compass.S.row)?.getOrNull(a.col + Compass.S.col),
            Compass.E to input.getOrNull(a.row + Compass.E.row)?.getOrNull(a.col + Compass.E.col),
            Compass.W to input.getOrNull(a.row + Compass.W.row)?.getOrNull(a.col + Compass.W.col),
            Compass.NW to input.getOrNull(a.row + Compass.NW.row)?.getOrNull(a.col + Compass.NW.col),
            Compass.NE to input.getOrNull(a.row + Compass.NE.row)?.getOrNull(a.col + Compass.NE.col),
            Compass.SW to input.getOrNull(a.row + Compass.SW.row)?.getOrNull(a.col + Compass.SW.col)
        ).mapValues { it.value == base }

        var count = 0
        if (nb[Compass.N] == false && !(nb[Compass.W] == true && nb[Compass.NW] == false)) count++
        if (nb[Compass.W] == false && !(nb[Compass.N] == true && nb[Compass.NW] == false)) count++
        if (nb[Compass.E] == false && !(nb[Compass.N] == true && nb[Compass.NE] == false)) count++
        if (nb[Compass.S] == false && !(nb[Compass.W] == true && nb[Compass.SW] == false)) count++
        return count
    }

    fun assembleEdges(area: Set<Coordinate>, input: CharGrid): Int = area.sumOf { checkCorners(input, it) }

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