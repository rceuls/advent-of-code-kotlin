fun main() {
    fun part1(input: List<String>): Int {
        val target = listOf('X', 'M', 'A', 'S')
        fun checkAdj(row: Int, column: Int): Int {
            val adj = listOf(0, 1, 2, 3)
            val coords = listOf(
                adj.map { row to column + it },
                adj.map { row to column - it },
                adj.map { row + it to column },
                adj.map { row - it to column },
                adj.map { row + it to column + it },
                adj.map { row - it to column - it },
                adj.map { row + it to column - it },
                adj.map { row - it to column + it },
            )
            var totalMatches = 0
            for (dir in coords) {
                var matches = 0
                for (i in dir.indices) {
                    val (r, c) = dir[i]
                    val character = input.getOrNull(r)?.getOrNull(c)
                    if (character == target[i]) {
                        matches++
                    } else {
                        break
                    }
                }
                if (matches == 4) totalMatches++
            }
            return totalMatches
        }

        val width = input[0].length
        val height = input.size
        var cnt = 0
        for (row in 0..height) {
            for (column in 0..<width) {
                val c = input.getOrNull(row)?.getOrNull(column)
                if (c == 'X') {
                    cnt += checkAdj(row, column)
                }
            }
        }
        return cnt
    }

    fun part2(input: List<String>): Int {
        val target = listOf('M', 'A', 'S')
        fun checkAdj(row: Int, column: Int): Boolean {
            val adj = listOf(-1, 0, 1)
            val coords1 = listOf(
                adj.map { row + it to column + it },
                adj.map { row - it to column - it },
            )
            val coords2 = listOf(
                adj.map { row - it to column + it },
                adj.map { row + it to column - it },
            )
            for (coords in listOf(coords1, coords2)) {
                var totalMatches = 0
                for (dir in coords) {
                    var matches = 0
                    for (i in dir.indices) {
                        val (r, c) = dir[i]
                        val character = input.getOrNull(r)?.getOrNull(c)
                        if (character == target[i]) {
                            matches++
                        } else {
                            break
                        }
                    }
                    if (matches == 3) totalMatches++
                }
                if (totalMatches == 0) {
                    return false
                }
            }
            return true
        }

        val width = input[0].length
        val height = input.size
        var cnt = 0
        for (row in 0..height) {
            for (column in 0..<width) {
                val c = input.getOrNull(row)?.getOrNull(column)
                if (c == 'A' && checkAdj(row, column)) {
                    cnt++
                }
            }
        }
        return cnt
    }

    val testData = readInput("Day04_test")
    check(part1(testData) == 18)
    check(part2(testData) == 9)
    val data = readInput("Day04")
    part1(data).println()
    part2(data).println()
}