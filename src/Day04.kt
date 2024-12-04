fun main() {

    fun validate(dir: List<Pair<Int, Int>>, target: String, input: List<String>): Boolean {
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
        return matches == target.length
    }

    fun part1(input: List<String>): Int {
        fun checkAdj(row: Int, column: Int): Int {
            val adj = listOf(0, 1, 2, 3)
            return listOf(
                adj.map { row to column + it },
                adj.map { row to column - it },
                adj.map { row + it to column },
                adj.map { row - it to column },
                adj.map { row + it to column + it },
                adj.map { row - it to column - it },
                adj.map { row + it to column - it },
                adj.map { row - it to column + it },
            ).count { validate(it, "XMAS", input) }
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
                if (coords.count { validate(it, "MAS", input) } == 0) {
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
    part1(data).println() // 2514
    part2(data).println() // 1888
}