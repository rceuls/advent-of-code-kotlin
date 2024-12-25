enum class SchematicType {
    Lock, Key
}

data class Schematic(val raw: List<String>) {
    val schematicType = if (raw[0].all { it == '.'}) SchematicType.Key else SchematicType.Lock
    private val numbered: List<Int>
        get() {
            val asNumber = mutableListOf(0, 0, 0, 0, 0)
            val toParse = raw.drop(1).take(5)
            for (col in 0..4) {
                for (row in 0..4) {
                    if (toParse[row][col] == '#') {
                        asNumber[col] += 1
                    }
                }
            }
            return asNumber
        }


    fun fits(other: Schematic): Boolean {
        if (other.schematicType == schematicType) {
            return false
        } else {
            for (fn in 0..4) {
                if (numbered[fn] + other.numbered[fn] > 5) {
                    return false
                }
            }
            "hit".println()
            return true
        }
    }
}

fun main() {
    val dayNumber = "Day25"

    fun part1(input: String): Int {
        val parsed = input.split("\n\n").map { s ->
            Schematic(s.lines())
        }
        val grouped = parsed.groupBy { it.schematicType }
        var matching = 0
        val allLocks = grouped[SchematicType.Lock].orEmpty()
        for(key in grouped[SchematicType.Key].orEmpty()) {
            matching += allLocks.count { lock -> key.fits(lock) }
        }
        return matching
    }

    val testData = readInputOneLine("${dayNumber}_test")
    check(part1(testData) == 3)
    val actualData = readInputOneLine(dayNumber)
    part1(actualData).println()
}
