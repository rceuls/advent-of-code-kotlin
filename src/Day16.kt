import java.util.*

fun main() {
    val dayNumber = "Day16"

    data class Reindeer(val cost: Int, val direction: Direction, val path: List<Coordinate>)

    fun Coordinate.move(dir: Direction) = Coordinate(this.row + dir.row, this.col + dir.col)

    fun Reindeer.getNextNodes(possible: Set<Coordinate>) =
        Direction.entries.toTypedArray()
            .filterNot { it == this.direction.opposite() }
            .filter { this.path.last().move(it) in possible }
            .map {
                Reindeer(
                    path = path + this.path.last().move(it),
                    direction = it,
                    cost = cost + if (it == direction) 1 else 1001
                )
            }


    fun dijkstra(other: Set<Coordinate>, start: Coordinate, end: Coordinate): Pair<Int, Int> {
        var minCost = Int.MAX_VALUE
        val seats = mutableSetOf<Coordinate>()
        val cachedCosts = mutableMapOf<Pair<Coordinate, Direction>, Int>().withDefault { Int.MAX_VALUE }
        val queue = PriorityQueue<Reindeer>(compareBy { it.cost }).apply {
            add(Reindeer(0, Direction.RIGHT, listOf(start)))
        }
        while (queue.isNotEmpty()) {
            queue.poll()
                .takeIf { rd ->
                    val node = rd.path.last() to rd.direction
                    val cached = cachedCosts.getOrDefault(node, Int.MAX_VALUE)
                    if (rd.cost < cached) cachedCosts[node] = rd.cost
                    if (rd.path.last() == end) {
                        if (rd.cost > minCost) return minCost to seats.size
                        minCost = rd.cost
                        seats += rd.path
                    }
                    rd.cost <= cached
                }?.getNextNodes(other)?.forEach { queue.add(it) }
        }
        return -1 to -1
    }


    fun part(input: CharGrid): Pair<Int, Int> {
        val grid = input.createCoordinateGrid(true)

        val start = grid['S']?.first()!!
        val end = grid['E']?.first()!!
        val validPositions = (grid['.'] ?: emptyList()).toSet()

        val doDijkstra = dijkstra(validPositions + end, start, end)

        return doDijkstra

    }

    val testData = readInputCharGrid("${dayNumber}_test")
    part(testData).println()
    check(part(testData) == 7036 to 45)
    execute(
        {
            val actualData = readInputCharGrid(dayNumber)
            part(actualData).println()
        }, {})
}
