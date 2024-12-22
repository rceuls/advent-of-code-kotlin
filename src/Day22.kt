const val PRUNE = 16777216

fun main() {
    fun generate(secret: Long): Long =
        secret.let { (it xor (it * 64L)) % PRUNE }
            .let { (it xor (it / 32L)) % PRUNE }
            .let { (it xor (it * 2048)) % PRUNE }

    fun hash(secret: Long, passes: Long, globalCounter: MutableMap<List<Long>, Long>): Long {
        var working = secret
        var lastPrice = secret % 10
        val changes = mutableListOf<Long>()
        val repeaters = mutableSetOf<List<Long>>()
        repeat((0 until passes).count()) {
            working = generate(working)
            val newPrice = working % 10
            changes += (newPrice - lastPrice)
            lastPrice = newPrice
            if (changes.size >= 4) {
                val groupedChanges = changes.takeLast(4)
                if (groupedChanges !in repeaters) {
                    repeaters += groupedChanges
                    val oldValue = globalCounter.getOrDefault(groupedChanges, 0)
                    globalCounter[groupedChanges] = (oldValue + newPrice)
                }
            }
        }
        return working
    }

    fun getTargetCounter(prodCache: MutableMap<List<Long>, Long>): Long =
        prodCache.values.maxOfOrNull { it } ?: 0L

    fun part1(input: List<String>): Pair<Long, Long> {
        val prodCache = mutableMapOf<List<Long>, Long>()
        val total = input.map { it.toLong() }.sumOf { hash(it, 2000, prodCache) }
        return total to getTargetCounter(prodCache)
    }

    val dayNumber = "Day22"
    val cache: MutableMap<List<Long>, Long> = mutableMapOf()
    check(hash(1, 2000, cache) == 8685429L)
    check(hash(10, 2000, cache) == 4700978L)
    check(hash(100, 2000, cache) == 15273692L)
    check(hash(2024, 2000, cache) == 8667524L)
    cache.clear()

    hash(1, 2000, cache)
    hash(2, 2000, cache)
    hash(3, 2000, cache)
    hash(2024, 2000, cache)
    check(getTargetCounter(cache) == 23L)

    part1(readInput(dayNumber)).println() // 1795 not enough
}
