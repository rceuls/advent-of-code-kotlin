import kotlin.time.measureTime

fun main() {
    val dayNumber = "Day13"
    val numberRegex = "\\d+".toRegex()

    data class CalculationPart(val x: Long, val y: Long)

    data class Calculation(val button1: CalculationPart, val button2: CalculationPart, val prize: CalculationPart) {}

    fun convertLine(input: String) = input.let {
        numberRegex.findAll(it).toList().map { c -> c.value.toLong() }.toList()
            .let { (x, y) -> CalculationPart(x, y) }
    }

    fun parseInput(input: String) =
        input
            .split("\n\n")
            .map { line ->
                val (button1, button2, prize) = line.split("\n")
                Calculation(
                    button1 = convertLine(button1),
                    button2 = convertLine(button2),
                    prize = convertLine(prize)
                )
            }

    fun solve(calc: Calculation, adj: Long = 0): Long {
        val prizeX = calc.prize.x + adj
        val prizeY = calc.prize.y + adj

        val det = calc.button1.x * calc.button2.y - calc.button2.x * calc.button1.y
        if (det == 0L) return 0L
        val detA = prizeX * calc.button2.y - prizeY * calc.button2.x
        val detB = prizeY * calc.button1.x - prizeX * calc.button1.y

        if (detA % det != 0L || detB % det != 0L) return 0L

        val finalButtonA = detA / det
        val finalButtonB = detB / det

        if (finalButtonA < 0L || finalButtonB < 0L) {
            return 0
        }
        return (3 * finalButtonA) + finalButtonB
    }

    fun part1(input: String): Long = parseInput(input).sumOf { solve(it) }
    fun part2(input: String): Long = parseInput(input).sumOf { solve(it, 10_000_000_000_000L) }

    var p1: Long
    var p2: Long
    val timeTakenTotal = measureTime {
        val testData = readInputOneLine("${dayNumber}_test")
        val timeTakenTests = measureTime {
            check(part1(testData) == 480L)
            check(part2(testData) == 875318608908L)
        }
        "Tests: $timeTakenTests".prettyPrint("#FBD8C6")

        val actualData = readInputOneLine(dayNumber)
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