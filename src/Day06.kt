import kotlinx.coroutines.*
import kotlin.time.measureTime

const val BLOCKER = '#'

@Suppress("unused")
enum class Direction(val row: Int, val col: Int) {
    UP(-1, 0),
    RIGHT(0, 1),
    DOWN(1, 0),
    LEFT(0, -1),
}

inline fun <reified T : Enum<T>> T.next(): T {
    val values = enumValues<T>()
    val nextOrdinal = (ordinal + 1) % values.size
    return values.getOrNull(nextOrdinal) ?: values[0]
}

typealias Grid = List<MutableList<Char>>

data class Coordinate(val row: Int, val col: Int, val visitedDirection: Direction = Direction.UP)

fun main() {
    val dayNumber = "Day06"

    fun getStartingCoordinates(input: Grid): Coordinate? {
        var startingCoords: Coordinate? = null
        for (i in input.indices) {
            for (j in input[i].indices) {
                if (input[i][j] == '^') {
                    startingCoords = Coordinate(i, j, Direction.UP)
                    break
                }
            }
        }
        return startingCoords
    }

    fun isBlocked(direction: Direction, currentCoordinate: Coordinate, grid: Grid): Boolean? =
        grid.getOrNull(currentCoordinate.row + direction.row)
            ?.getOrNull(currentCoordinate.col + direction.col)
            ?.let { it == BLOCKER }


    tailrec fun step(
        accumulator: List<Coordinate> = emptyList(),
        current: Coordinate,
        direction: Direction,
        grid: Grid
    ): List<Coordinate> =
        isBlocked(direction, current, grid).let { blocked ->
            return when (blocked) {
                true ->
                    step(accumulator, current, direction.next(), grid)

                false -> {
                    val newCoordinate = Coordinate(current.row + direction.row, current.col + direction.col, direction)
                    step(
                        accumulator + newCoordinate,
                        newCoordinate,
                        direction,
                        grid
                    )
                }

                else -> accumulator
            }
        }

    tailrec fun stepWithLoopDetection(
        accumulator: List<Coordinate> = emptyList(),
        current: Coordinate,
        direction: Direction,
        grid: Grid
    ): Pair<Boolean, List<Coordinate>> =
        isBlocked(direction, current, grid).let { blocked ->
            val newCoordinate = Coordinate(
                current.row + direction.row,
                current.col + direction.col, direction
            )
            return when (blocked) {
                true ->
                    stepWithLoopDetection(accumulator, current, direction.next(), grid)
                false ->
                    if (newCoordinate !in accumulator) {
                        stepWithLoopDetection(
                            accumulator + newCoordinate,
                            newCoordinate,
                            direction,
                            grid
                        )
                    } else {
                        true to accumulator
                    }

                else -> false to accumulator
            }
        }


    fun part1(input: Grid): Int {
        val startingCoords: Coordinate = getStartingCoordinates(input) ?: throw Exception("No starting coordinates.")
        return step(
            listOf(startingCoords),
            startingCoords,
            Direction.UP,
            input
        ).map { it.row to it.col }
            .toSet().size
    }

    fun part2(input: Grid): Int = runBlocking {
        val startingCoords: Coordinate = getStartingCoordinates(input) ?: throw Exception("No starting coordinates.")
        val obstaclesWithLoop: MutableSet<Coordinate> = mutableSetOf()
        val happyPath = step(listOf(startingCoords), startingCoords, Direction.UP, input).map { it.row to it.col }

        val jobs = mutableListOf<Job>()
        for (i in input.indices) {
            for (j in input[i].indices) {
                jobs += launch(Dispatchers.Default) {
                    if (input[i][j] != '#' && (i to j) in happyPath) {
                        val changedGrid = input.toList().map { it.toMutableList() }
                        changedGrid[i][j] = '#'
                        val steps = stepWithLoopDetection(listOf(startingCoords), startingCoords, Direction.UP, changedGrid)
                        if (steps.first) {
                            synchronized(obstaclesWithLoop) { obstaclesWithLoop.add(Coordinate(i, j)) }
                        }
                    }
                }
            }
        }

        jobs.forEach { it.join() }

        return@runBlocking obstaclesWithLoop.size    }

    var p1: Int
    var p2: Int
    val timeTakenTotal = measureTime {
        val testData = readInput("${dayNumber}_test").map { it.map { c -> c }.toMutableList() }
        val timeTakenTests = measureTime {
            check(part1(testData) == 41)
            check(part2(testData) == 6)
        }
        "Tests: $timeTakenTests".prettyPrint("#FBD8C6")

        val actualData = readInput(dayNumber).map { it.map { c -> c }.toMutableList() }
        val timeTaken = measureTime {
            p1 = part1(actualData)
            p2 = part2(actualData)
        }
        "Actual: $timeTaken".prettyPrint("#FBD8C6")
    }
    "Total: $timeTakenTotal".prettyPrint("#FBC6CF")
    p1.prettyPrint("#FBC6EA") // 5131
    p2.prettyPrint("#FBC6EB") // 217 == too low
}