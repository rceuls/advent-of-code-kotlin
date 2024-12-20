import java.util.*

fun main() {
    val dayNumber = "Day18"

    fun createGrid(size: Int, disabled: Set<Coordinate>): List<Coordinate> {
        val grid = (0 until size).flatMap { row ->
            (0 until size).mapNotNull { col ->
                val c = Coordinate(row, col)
                if (c !in disabled) c else null
            }
        }
        return grid
    }

    fun createDistances(
        size: Int,
        disabled: Set<Coordinate>
    ): Map<Coordinate, Int> {
        val grid = createGrid(size, disabled)
        val graph = mutableMapOf<Coordinate, List<Pair<Coordinate, Int>>>()
        for (coordinate in grid) {
            val nb = coordinate.validNeighbours()
            graph[coordinate] = nb.map { it to 1 }
        }
        val distances = dijkstra(graph, Coordinate(0, 0))
        return distances
    }

    fun part1(input: List<String>, slice: Int, size: Int): Int {
        val disabled =
            input.map { line -> Coordinate(line.substringAfter(",").toInt(), line.substringBefore(",").toInt()) }
                .subList(0, slice)
                .toSet()

        val distances = createDistances(size, disabled)
        return distances.getValue(Coordinate(size - 1, size - 1))
    }

    fun part2(input: List<String>, size: Int): Coordinate? {
        val disabled =
            input.map { line -> Coordinate(line.substringAfter(",").toInt(), line.substringBefore(",").toInt()) }
        val toPass = LinkedList(disabled)
        val allCoords = mutableSetOf<Coordinate>()
        while (toPass.isNotEmpty()) {
            val newCoord = toPass.pop()
            allCoords.add(newCoord)
            val distance = createDistances(size, allCoords)
            if (distance.getOrElse(Coordinate(size - 1, size - 1)) { Int.MAX_VALUE } == Int.MAX_VALUE) {
                return newCoord
            }
        }
        return null
    }

    val testData = readInput("${dayNumber}_test")
    check(part1(testData, 12, 7) == 22)
    check(part2(testData, 7) == Coordinate(1, 6))
    execute(
        {
            val actualData = readInput(dayNumber)
            part1(actualData, 1024, 71).println()
        },
        {
            val actualData = readInput(dayNumber)
            val coord = part2(actualData, 71)
            "${coord?.col},${coord?.row}".println()
        }
    )
}
