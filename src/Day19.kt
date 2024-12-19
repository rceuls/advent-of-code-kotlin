fun main() {
    val dayNumber = "Day19"

    fun String.doStuff(available: List<String>, cache: MutableMap<String, Long> = mutableMapOf()): Long =
        1L.takeIf { this.isEmpty() } ?: cache.getOrPut(this) {
            available
                .filter { this.startsWith(it) }
                .sumOf { this.removePrefix(it).doStuff(available, cache) }
        }

    fun part1(toCheck: List<String>, available: List<String>): Int = toCheck.count { it.doStuff(available) > 0 }

    fun part2(toCheck: List<String>, available: List<String>): Long = toCheck.sumOf { it.doStuff(available) }

    val input = readInputOneLine(dayNumber)
    val (availableRaw, toCheckRaw) = input.split("\n\n").map(String::trim)
    val available = availableRaw.split(", ").map(String::trim)
    val toCheck = toCheckRaw.lines()
    part1(toCheck, available).println()
    part2(toCheck, available).println()
}
