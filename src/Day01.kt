import kotlin.math.abs

fun main() {
    fun createLists(input: List<String>): Pair<List<Int>, List<Int>> =
        input.map { it.split("   ").map(String::toInt) }
            .let { pairs -> pairs.map { it[0] }.sorted() to pairs.map { it[1] }.sorted() }

    fun part1(firstList: List<Int>, secondList: List<Int>): Int =
        firstList.zip(secondList).sumOf { abs(it.first - it.second) }

    fun part2(firstList: List<Int>, secondList: List<Int>): Int =
        firstList.fold(0) { acc, next ->
            acc + (next * secondList.count { sit -> sit == next })
        }

    val (firstListTest, secondListTest) = createLists(readInput("Day01_test"))
    check(part1(firstListTest, secondListTest) == 11)
    check(part2(firstListTest, secondListTest) == 31)

    val (firstList, secondList) = createLists(readInput("Day01"))
    part1(firstList, secondList).println()
    part2(firstList, secondList).println()

}
