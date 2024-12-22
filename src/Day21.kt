import java.util.PriorityQueue

data class KeypadCoordinate(val x: Int, val y: Int) {
    companion object {
        val UP = KeypadCoordinate(0, -1)
        val DOWN = KeypadCoordinate(0, 1)
        val LEFT = KeypadCoordinate(-1, 0)
        val RIGHT = KeypadCoordinate(1, 0)
    }

    fun neighbours(): Set<KeypadCoordinate> = setOf(
        this + UP, this + RIGHT, this + DOWN, this + LEFT
    )

    operator fun minus(other: KeypadCoordinate): KeypadCoordinate = KeypadCoordinate(x - other.x, y - other.y)

    operator fun plus(other: KeypadCoordinate): KeypadCoordinate = KeypadCoordinate(x + other.x, y + other.y)
}

val numericPad: Map<KeypadCoordinate, Char> = mapOf(
    KeypadCoordinate(0, 0) to '7',
    KeypadCoordinate(1, 0) to '8',
    KeypadCoordinate(2, 0) to '9',
    KeypadCoordinate(0, 1) to '4',
    KeypadCoordinate(1, 1) to '5',
    KeypadCoordinate(2, 1) to '6',
    KeypadCoordinate(0, 2) to '1',
    KeypadCoordinate(1, 2) to '2',
    KeypadCoordinate(2, 2) to '3',
    KeypadCoordinate(1, 3) to '0',
    KeypadCoordinate(2, 3) to 'A'
)

val directionalPad: Map<KeypadCoordinate, Char> = mapOf(
    KeypadCoordinate(1, 0) to '^', KeypadCoordinate(2, 0) to 'A', KeypadCoordinate(0, 1) to '<', KeypadCoordinate(1, 1) to 'v', KeypadCoordinate(2, 1) to '>'
)


fun main() {
    fun <T> Collection<T>.allPairs(): List<Pair<T, T>> = flatMap { left ->
        map { right -> left to right }
    }

    fun KeypadCoordinate.diffToChar(other: KeypadCoordinate): Char = when (val result = other - this) {
        KeypadCoordinate.UP -> '^'
        KeypadCoordinate.RIGHT -> '>'
        KeypadCoordinate.DOWN -> 'v'
        KeypadCoordinate.LEFT -> '<'
        else -> throw IllegalArgumentException("Invalid direction $result")
    }

    fun Map<KeypadCoordinate, Char>.findLowestCostPaths(start: KeypadCoordinate, end: KeypadCoordinate): List<String> {
        val queue = PriorityQueue<Pair<List<KeypadCoordinate>, Int>>(compareBy { it.second }).apply { add(listOf(start) to 0) }
        val seen = mutableMapOf<KeypadCoordinate, Int>()
        var finalCost: Int? = null
        val allPaths: MutableList<String> = mutableListOf()

        while (queue.isNotEmpty()) {
            val (path, cost) = queue.poll()
            val location = path.last()

            if (finalCost != null && cost > finalCost) {
                return allPaths
            } else if (path.last() == end) {
                finalCost = cost
                allPaths.add(path.zipWithNext().map { (from, to) -> from.diffToChar(to) }.joinToString("") + "A")
            } else if (seen.getOrDefault(location, Int.MAX_VALUE) >= cost) {
                seen[location] = cost
                location.neighbours().filter { it in keys }.forEach { queue.add(path + it to cost + 1) }
            }
        }
        return allPaths
    }

    fun Map<KeypadCoordinate, Char>.allPaths(): Map<Pair<Char, Char>, List<String>> = keys.allPairs().associate {
        (getValue(it.first) to getValue(it.second)) to findLowestCostPaths(it.first, it.second)
    }

    val numericPaths: Map<Pair<Char, Char>, List<String>> = numericPad.allPaths()

    val directionalPaths: Map<Pair<Char, Char>, List<String>> = directionalPad.allPaths()

    fun cost(
        code: String,
        depth: Int,
        transitions: Map<Pair<Char, Char>, List<String>> = numericPaths,
        cache: MutableMap<Pair<String, Int>, Long> = mutableMapOf()
    ): Long = cache.getOrPut(code to depth) {
        "A$code".zipWithNext().sumOf { transition ->
            val paths: List<String> = transitions.getValue(transition)
            when (depth) {
                0 -> {
                    paths.minOf { it.length }.toLong()
                }
                else -> {
                    paths.minOf { path -> cost(path, depth - 1, directionalPaths, cache) }
                }
            }
        }
    }


    fun solve(depth: Int, codes: List<String>): Long = codes.sumOf { cost(it, depth) * it.dropLast(1).toLong() }

    val codes = readInput("Day21")
    solve(2, codes).println()
    solve(25, codes).println()
}
