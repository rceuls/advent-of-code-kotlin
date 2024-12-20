fun List<Coordinate>.wallhack(min: Int, hack: Int): Int = this.indices.sumOf { start ->
    (start + min..this.lastIndex).count { end ->
        val distance = this[start].distanceTo(this[end])
        distance <= hack && distance <= end - start - min
    }
}

fun main() {
    val dayNumber = "Day20"

    fun getPath(input: Map<Coordinate, Char>): MutableList<Coordinate> {
        var current = input.filter { it.value == 'S' }.keys.first()
        val last = input.filter { it.value == 'E' }.keys.first()
        val valid = input.filter { it.value == '.' }.keys.toMutableSet() + last
        val path = mutableListOf(current)
        while (current != last) {
            current = current.neighbours().first { it in valid && it !in path }
            path += current
        }
        return path
    }

    fun part1(input: Map<Coordinate, Char>): Int = getPath(input).wallhack(100, 2)

    fun part2(input: Map<Coordinate, Char>): Int = getPath(input).wallhack(100, 20)

    val actualData = readInputCoordinateGrid(dayNumber)
    part1(actualData).println()
    part2(actualData).println()

}
