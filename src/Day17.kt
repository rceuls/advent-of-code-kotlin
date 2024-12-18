import kotlin.math.pow
import kotlin.math.truncate

fun main() {
    val dayNumber = "Day17"

    data class Machine(
        var registerA: Long = 0,
        var registerB: Long = 0,
        var registerC: Long = 0,
        val operations: List<Long> = emptyList(),
        val output: MutableList<Long> = mutableListOf(),
    ) {
        fun comboAsValue(literal: Long): Long = when (literal) {
            0L, 1L, 2L, 3L -> literal
            4L -> registerA
            5L -> registerB
            6L -> registerC
            else -> throw Exception("Invalid register")
        }

        fun doAction(operation: Long, literal: Long, instructionPointer: Int): Int =
            comboAsValue(literal).let { combo ->
                when (operation) {
                    0L -> {
                        registerA = truncate(registerA / 2.toDouble().pow(combo.toDouble())).toLong()
                        instructionPointer + 2
                    }

                    1L -> {
                        registerB = registerB xor literal
                        instructionPointer + 2
                    }

                    2L -> {
                        registerB = combo % 8
                        instructionPointer + 2
                    }

                    3L -> if (registerA == 0L) instructionPointer + 2 else literal
                    4L -> {
                        registerB = registerB xor registerC
                        instructionPointer + 2
                    }

                    5L -> {
                        output.add(combo % 8)
                        instructionPointer + 2
                    }

                    6L -> {
                        registerB = truncate(registerA / 2.toDouble().pow(combo.toDouble())).toLong()
                        instructionPointer + 2
                    }

                    7L -> {
                        registerC = truncate(registerA / 2.toDouble().pow(combo.toDouble())).toLong()
                        instructionPointer + 2
                    }

                    else -> throw Exception("Invalid operation")
                }
            }.toInt()

        fun runProgram() {
            var instructionPointer = 0
            while (instructionPointer <= operations.size - 1) {
                instructionPointer =
                    doAction(operations[instructionPointer], operations[instructionPointer + 1], instructionPointer)
            }
        }

    }

    fun part1(machine: Machine): String = machine.runProgram().let {
        machine.output.joinToString(",")
    }

    fun part2(ops: List<Int>): Long {
        fun List<Int>.combo(operand: Int) = if (operand in 0..3) operand else get(operand - 4)

        fun rehash(p: Int, r: Long, d: Int): Long {
            if (p < 0) return r
            if (d > 7) return -1

            val registry = mutableListOf((r shl 3).toInt() or d, 0, 0)
            var opPointer = 0
            var w = 0

            while (opPointer < ops.size) {
                val operation = ops[opPointer + 1]
                val combo = registry.combo(operation)

                when (ops[opPointer]) {
                    0 -> registry[0] = registry[0] shr combo
                    1 -> registry[1] = registry[1] xor ops[opPointer + 1]
                    2 -> registry[1] = (combo and 7)
                    3 -> opPointer = if (registry[0] != 0) ops[opPointer + 1] - 2 else opPointer
                    4 -> registry[1] = registry[1] xor registry[2]
                    5 -> {
                        w = combo and 7
                        break
                    }
                    6 -> registry[1] = registry[0] shr combo
                    7 -> registry[2] = registry[0] shr combo
                }
                opPointer += 2
            }

            if (w == ops[p]) {
                val result = rehash(p - 1, (r shl 3) or d.toLong(), 0)
                if (result != -1L) return result
            }

            return rehash(p, r, d + 1)
        }

        return rehash(ops.size - 1, 0, 0)
    }

    val input = readInput(dayNumber)
    val machine = Machine(
        registerA = input[0].substringAfter("Register A: ").toLong(),
        registerB = input[1].substringAfter("Register B: ").toLong(),
        registerC = input[2].substringAfter("Register C: ").toLong(),
        operations = input[4].substringAfter("Program: ").split(",").map { it.toLong() },
    )

    check(
        part1(
            Machine(
                registerA = 729, registerB = 0, registerC = 0, operations = listOf(0, 1, 5, 4, 3, 0)
            )
        ) == "4,6,3,5,6,3,5,2,1,0"
    )
    check(
        part2(
            listOf(0, 3, 5, 4, 3, 0)
        ) == 117440L
    )
    part2(
        machine.operations.map { it.toInt()}
    ).println()
}
