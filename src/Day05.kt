fun main() {
    fun readFile(name: String) = readInputOneLine(name)
        .split("\n\n")
        .map { it.split("\n") }
        .let { input ->
            val (first, second) = input
            first.map {
                val parts = it.split("|").map(String::toInt)
                parts[0] to parts[1]
            } to second.map { it.split(",").map(String::toInt) }
        }

    fun List<Int>.takeMiddle() = this[this.size / 2]

    fun isCorrectLine(
        line: List<Int>, correctOrder: List<Pair<Int, Int>>
    ) = line.indices.all { ci ->
        (ci + 1..<line.size).all { (line[ci] to line[it]) in correctOrder }
    }

    fun part1(input: Pair<List<Pair<Int, Int>>, List<List<Int>>>): Int =
        input.second.filter { line -> isCorrectLine(line, input.first) }.sumOf { it.takeMiddle() }

    fun part2(input: Pair<List<Pair<Int, Int>>, List<List<Int>>>): Int =
        input.let { (correctOrder, lines) ->
            lines.filterNot { isCorrectLine(it, correctOrder) }.map { line ->
                line.toMutableList().apply {
                    while (!isCorrectLine(this, correctOrder)) {
                        this.indices.forEach { ci ->
                            (ci + 1..<this.size).forEach {
                                val l = this[ci]
                                val r = this[it]
                                if (l to r !in correctOrder) {
                                    this[ci] = r
                                    this[it] = l
                                }
                            }
                        }
                    }
                }
            }.sumOf { it.takeMiddle() }
        }

    val testData = readFile("Day05_test")
    check(part1(testData) == 143)
    check(part2(testData) == 123)

    val actualData = readFile("Day05")
    part1(actualData).println() // 4578
    part2(actualData).println() // 6179
}