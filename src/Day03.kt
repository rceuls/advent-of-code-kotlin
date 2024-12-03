fun main() {
    val mulRegex = "(mul\\(\\d{1,3},\\d{1,3}\\))".toRegex()
    val numRegex = "(\\d{1,3}),(\\d{1,3})".toRegex()
    val p2Regex = "(mul\\((\\d{1,3}),(\\d{1,3})\\))|do\\(\\)|don't\\(\\)".toRegex()

    fun calculateMultipleFromString(line: String) = mulRegex.findAll(line).map { mulMatch ->
        numRegex.find(mulMatch.groupValues[0])
            ?.let { numMatch -> numMatch.groups[1]!!.value.toInt() * numMatch.groups[2]!!.value.toInt() }
            ?: 0
    }.sum()

    fun part1(input: List<String>): Int = input.sumOf { calculateMultipleFromString(it) }

    fun part2(input: List<String>): Int {
        var enabled = true
        return input.sumOf {
            p2Regex.findAll(it).sumOf { m ->
                if (enabled && m.value.contains("mul")) {
                    m.groups[3]!!.value.toInt() * m.groups[2]!!.value.toInt()
                } else if (m.value == "do()") {
                    enabled = true
                    0
                } else {
                    enabled = false
                    0
                }
            }
        }
    }

    check(part1(readInput("Day03_test")) == 161)
    check(part2(readInput("Day03p02_test")) == 48)
    val data = readInput("Day03")
    part1(data).println()
    part2(data).println() // 104245808 (H) 99724783 (L)
}