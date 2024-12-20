import java.util.*

fun dijkstra(graph: Map<Coordinate, List<Pair<Coordinate, Int>>>, start: Coordinate): Map<Coordinate, Int> {
    val distances = mutableMapOf<Coordinate, Int>().withDefault { Int.MAX_VALUE }
    val priorityQueue = PriorityQueue<Pair<Coordinate, Int>>(compareBy { it.second }).apply { add(start to 0) }

    distances[start] = 0

    while (priorityQueue.isNotEmpty()) {
        val (node, currentDist) = priorityQueue.poll()
        graph[node]?.forEach { (adjacent, weight) ->
            val totalDist = currentDist + weight
            if (totalDist < distances.getValue(adjacent)) {
                distances[adjacent] = totalDist
                priorityQueue.add(adjacent to totalDist)
            }
        }
    }
    return distances
}

