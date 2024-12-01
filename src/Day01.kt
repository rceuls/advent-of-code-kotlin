import kotlin.math.abs

fun main() {
    fun createLists(input: List<String>): Pair<List<Int>, List<Int>> {
        val pairs = input.map { it.split("\\s+".toRegex()).map(String::toInt) }
        val firstList = pairs.map { it[0] }.sorted()
        val secondList = pairs.map { it[1] }.sorted()
        return Pair(firstList, secondList)
    }

    fun part1(firstList: List<Int>, secondList: List<Int>): Int =
        firstList.zip(secondList).sumOf { abs(it.first - it.second) }

    fun part2(firstList: List<Int>, secondList: List<Int>): Int {
        val counts = mutableMapOf<Int, Int>()
        return firstList.fold(0) { acc, next ->
            if (next !in counts) {
                counts[next] = secondList.count { sit -> sit == next }
            }
            acc + (next * counts[next]!!)
        }
    }

    val (firstListTest, secondListTest) = createLists(readInput("Day01_test"))
    check(part1(firstListTest, secondListTest) == 11)
    check(part2(firstListTest, secondListTest) == 31)

    val (firstList, secondList) = createLists(readInput("Day01"))
    part1(firstList, secondList).println()
    part2(firstList, secondList).println()

}
