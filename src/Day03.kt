fun main() {
    val mulRegex = "(mul\\((\\d{1,3}),(\\d{1,3})\\))".toRegex()
    val p2Regex = "(mul\\((\\d{1,3}),(\\d{1,3})\\))|do\\(\\)|don't\\(\\)".toRegex()

    fun calculateMultipleFromString(line: String) = mulRegex.findAll(line)
        .sumOf {
            it.groups[2]!!.value.toInt() * it.groups[3]!!.value.toInt()
        }

    fun part1(input: String): Int = calculateMultipleFromString(input)

    fun part2(input: String): Int {
        var enabled = true
        return p2Regex.findAll(input).sumOf { m ->
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

    check(part1(readInputOneLine("Day03_test")) == 161)
    check(part2(readInputOneLine("Day03p02_test")) == 48)
    val data = readInputOneLine("Day03")
//188116424
//104245808
    part1(data).println()
    part2(data).println()
}