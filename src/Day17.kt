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

        fun reset(regA: Long = 0) {
            output.clear()
            registerA = regA
            registerB = 0
            registerC = 0
        }
    }

    fun part1(machine: Machine): String = machine.runProgram().let {
        machine.output.joinToString(",")
    }

    fun part2(machine: Machine): Long {
        val from = 8.toDouble().pow(machine.operations.size - 1).toLong()
        val to = 8.toDouble().pow(machine.operations.size).toLong()
        "$from -> $to".println()
        val range = to - from
        var fullProcent = 1L
        (from..to).reversed().forEach {
            machine.reset(it)
            machine.runProgram()
            if (machine.output == machine.operations) {
                it.println()
                return it
            }
            if (fullProcent < it / range) {
                fullProcent = it / range
                "Percentage: $fullProcent".println()
            }
        }

        return 0
    }

    check(
        part1(
            Machine(
                registerA = 729, registerB = 0, registerC = 0, operations = listOf(0, 1, 5, 4, 3, 0)
            )
        ) == "4,6,3,5,6,3,5,2,1,0"
    )
    check(
        part2(
            Machine(
                registerA = 729, registerB = 0, registerC = 0, operations = listOf(0,3,5,4,3,0)
            )
        ) == 117440L
    )
}
