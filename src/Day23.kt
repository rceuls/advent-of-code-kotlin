fun main() {
    val dayNumber = "Day23"

    fun cliques(
        graph: Map<String, Set<String>>,
        limit: Int = Int.MAX_VALUE
    ): Set<List<String>> =
        buildSet {
            fun recurse(node: String, clique: Set<String>) {
                clique.sorted()
                    .takeUnless { it in this }
                    ?.also(::add)
                    ?.takeIf { limit > it.size }
                    ?: return

                for (nb in graph.getValue(node)) {
                    if (nb in clique) continue
                    if (!graph.getValue(nb).containsAll(clique)) continue
                    recurse(nb, clique + nb)
                }
            }
            for (c in graph.keys) recurse(c, setOf(c))
        }


    fun build(input: List<String>): Map<String, MutableSet<String>> =
        buildMap {
            input.map {
                it.split("-")
            }.forEach { (first, second) ->
                compute(first) { _, acc -> ((acc ?: mutableSetOf<String>() as MutableSet<String>)).apply { add(second) } }
                compute(second) { _, acc -> ((acc ?: mutableSetOf<String>() as MutableSet<String>)).apply { add(first) } }
            }
        }


    fun part2(input: List<String>): String =
        cliques(build(input)).maxBy { it.size }.joinToString(",")

    fun part1(input: List<String>): Int =
        cliques(build(input), 3).filter { it.size == 3 }.count { it.any { n -> n.startsWith("t") } }

    val testData = readInput("${dayNumber}_test")


    check(part1(testData) == 7)
    check(part2(testData) == "co,de,ka,ta")
    val actualData = readInput(dayNumber)
    part1(actualData).println()
    part2(actualData).println()
}
