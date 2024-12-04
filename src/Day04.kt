fun main() {
    fun validate(coordinates: List<Pair<Int, Int>>, target: String, input: List<String>): Boolean =
        coordinates.indices.all { i ->
            input.getOrNull(coordinates[i].first)?.getOrNull(coordinates[i].second) == target[i]
        }

    fun part1(input: List<String>): Int {
        val word = "XMAS"
        fun checkAdj(row: Int, column: Int): Int {
            val adj = word.mapIndexed { index, _ -> index }
            return listOf(
                adj.map { row to column + it },
                adj.map { row to column - it },
                adj.map { row + it to column },
                adj.map { row - it to column },
                adj.map { row + it to column + it },
                adj.map { row - it to column - it },
                adj.map { row + it to column - it },
                adj.map { row - it to column + it },
            ).count { validate(it, word, input) }
        }
        return (0..input.size).sumOf { row ->
            (0..input[0].length).sumOf { column ->
                checkAdj(row, column)
            }
        }
    }

    fun part2(input: List<String>): Int {
        val word = "MAS"
        fun checkAdj(row: Int, column: Int): Int {
            val adj = listOf(-1, 0, 1)
            val coords1 = listOf(
                adj.map { row + it to column + it },
                adj.map { row - it to column - it }
            )
            val coords2 = listOf(
                adj.map { row - it to column + it },
                adj.map { row + it to column - it },
            )
            for (coords in listOf(coords1, coords2)) {
                if (coords.count { validate(it, word, input) } == 0) {
                    return 0
                }
            }
            return 1
        }
        return (0..input.size).sumOf { row ->
            (0..input[0].length).sumOf { column ->
                checkAdj(row, column)
            }
        }
    }

    val testData = readInput("Day04_test")
    check(part1(testData) == 18)
    check(part2(testData) == 9)
    val data = readInput("Day04")
    part1(data).println() // 2514
    part2(data).println() // 1888
}