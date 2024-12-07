import java.math.BigInteger
import kotlin.time.measureTime

typealias Line = Pair<BigInteger, List<BigInteger>>

fun main() {
    val dayNumber = "Day07"

    fun List<String>.formatInput() = this
        .map { it.substringBefore(": ") to it.substringAfter(": ") }
        .map { (s, n) -> s.toBigInteger() to n.toBigIntArray() }

    fun calc(total: BigInteger, op: BigInteger, ops: List<BigInteger>): Boolean {
        if (ops.isEmpty()) return total == op
        return calc(total, op * ops[0], ops.drop(1)) || calc(total, op + ops[0], ops.drop(1))
    }

    fun part1(input: List<Line>): BigInteger {
        var bigTotal: BigInteger = BigInteger.ZERO
        for (line in input) {
            val (total, components) = line
            if (calc(total, components[0], components.drop(1))) {
                bigTotal += total
            }
        }
        return bigTotal
    }

    fun calc2(total: BigInteger, op: BigInteger, ops: List<BigInteger>): Boolean {
        if (ops.isEmpty()) return total == op
        return calc2(total, op * ops[0], ops.drop(1))
                || calc2(total, op + ops[0], ops.drop(1))
                || calc2(total, "$op${ops[0]}".toBigInteger(), ops.drop(1))
    }

    fun part2(input: List<Line>): BigInteger {
        var bigTotal: BigInteger = BigInteger.ZERO
        for (line in input) {
            val (total, components) = line
            if (calc2(total, components[0], components.drop(1))) {
                bigTotal += total
            }
        }
        return bigTotal
    }

    var p1: BigInteger
    var p2: BigInteger
    val timeTakenTotal = measureTime {
        val testData = readInput("${dayNumber}_test").formatInput()
        val timeTakenTests = measureTime {
            check(part1(testData) == 3749.toBigInteger())
            check(part2(testData) == 11387.toBigInteger())
        }
        "Tests: $timeTakenTests".prettyPrint("#FBD8C6")

        val actualData = readInput(dayNumber).formatInput()
        val timeTaken = measureTime {
            p1 = part1(actualData)
            p2 = part2(actualData)
        }
        "Actual: $timeTaken".prettyPrint("#FBD8C6")
    }
    "Total: $timeTakenTotal".prettyPrint("#FBC6CF")
    p1.prettyPrint("#FBC6EA")
    p2.prettyPrint("#FBC6EB")
}