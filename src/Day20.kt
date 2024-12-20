fun main() {
    val dayNumber = "Day20"

    fun part1(input: Map<Coordinate, Char>): Int {
        val grouped = input.toList().groupBy { it.second }.map { it.key to it.value.map { c -> c.first } }.toMap()
        val graph = mutableMapOf<Coordinate, MutableList<Pair<Coordinate, Int>>>()
        val validShortcuts: MutableSet<Coordinate> = mutableSetOf()

        val open = grouped['.']!!
        val walls = grouped['#']!!
        val start = grouped['S']!!.first()
        val end = grouped['E']!!.first()

        val allowed = open + start + end
        for (coordinate in allowed) {
            val vn = coordinate.validNeighbours()
            graph[coordinate] = vn.filter { it in allowed }.mapTo(mutableListOf()) { it to 1 }
            validShortcuts += vn
                .filter { it in walls }
                .filter { wall -> wall.neighbours().any { nb -> nb in allowed && nb != coordinate } }
        }
        val baseline = dijkstra(graph, start).getValue(end)

        val savedDistances: MutableMap<Int, Int> = mutableMapOf<Int, Int>().withDefault { 0 }
        for (validShortcut in validShortcuts) {
            val workingGraph = graph.toMutableMap()
            for (nb in validShortcut.neighbours().filter { it in allowed }) {
                workingGraph[nb]?.add(validShortcut to 1)
            }
            workingGraph[validShortcut] =
                validShortcut.neighbours().filter { it in allowed }.map { it to 1 }.toMutableList()
            val d = baseline - dijkstra(workingGraph, start).getValue(end)
            if (!savedDistances.containsKey(d)) {
                savedDistances[d] = 1
            } else {
                savedDistances[d] = savedDistances[d]!! + 1
            }
        }
        savedDistances.toSortedMap().println()
        return savedDistances.filter { it.key >= 100 }.map { it.value }.sum()
    }

    fun part2(input: List<String>): Int = input.size
    val testData = readInputCoordinateGrid("${dayNumber}_test")
    part1(testData)
    val actualData = readInputCoordinateGrid(dayNumber)
    part1(actualData).println()
}
