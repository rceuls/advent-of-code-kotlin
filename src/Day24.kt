import kotlin.math.pow

enum class GateOp {
    XOR, OR, AND
}

data class Gate(
    val input: Pair<String, String>, val output: String?, val operation: GateOp
)

fun calculateValue(input: String, value: Boolean): Long {
    if (value) {
        return 2.0.pow(input.takeLast(2).toDouble()).toLong()
    }
    return 0L
}

fun printGroups(gates: Map<String, Boolean>) {
    val grouped = gates.entries.groupBy { (k, _) -> k[0] }
    for (gr in grouped.entries.sortedBy { it.key }) {
        "${gr.key}:${gr.value.map { if (it.value) '1' else '0' }.joinToString("")}".println()
    }
}

fun main() {
    val dayNumber = "Day24"

    fun getInitialState(input: String): Triple<MutableSet<String>, MutableMap<String, Boolean>, List<Gate>> {
        val (initValuesRaw, gatesRaw) = input.split("\n\n")
        val allGates = mutableSetOf<String>()
        val values = initValuesRaw.lines().associate { l ->
            val (gate, isSet) = l.substringBefore(": ") to l.substringAfter(": ")
            Pair(gate, isSet == "1")
        }.toMutableMap()

        val gates = gatesRaw.lines().map { l ->
            val (op, tgt) = l.substringBefore(" -> ") to l.substringAfter(" -> ")
            val ops = op.split(" ")
            val gateOp = when {
                ops[1] == "XOR" -> GateOp.XOR
                ops[1] == "OR" -> GateOp.OR
                ops[1] == "AND" -> GateOp.AND
                else -> GateOp.AND
            }
            allGates.add(tgt)
            allGates.add(ops[0])
            allGates.add(ops[2])
            Gate(
                input = ops[0] to ops[2],
                output = tgt,
                operation = gateOp,
            )
        }
        return Triple(allGates, values, gates)
    }

    fun part1(input: String): Long {
        val (allGates, values, gates) = getInitialState(input)
        while (allGates.size != values.size) {
            gates.forEach { gate ->
                val firstValue = values[gate.input.first]
                val secondValue = values[gate.input.second]
                if (firstValue != null && secondValue != null && gate.output != null) {
                    values[gate.output] = when (gate.operation) {
                        GateOp.XOR -> firstValue xor secondValue
                        GateOp.OR -> firstValue || secondValue
                        GateOp.AND -> firstValue && secondValue
                    }
                }
            }
        }
        printGroups(values)
        return allGates.filter { it.startsWith("z") }.sumOf { calculateValue(it, values.getValue(it)) }
    }

    fun part2(input: String): Int {
        return 0
    }

    val testData = readInputOneLine("${dayNumber}_test")
    check(part1(testData) == 2024L)
    // check(part2(testData) == 0)
    val actualData = readInputOneLine(dayNumber)
    part1(actualData).println()
    part2(actualData).println()
}
