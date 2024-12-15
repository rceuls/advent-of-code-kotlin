import com.github.ajalt.mordant.rendering.TextColors
import com.github.ajalt.mordant.terminal.Terminal
import kotlin.io.path.Path
import kotlin.io.path.readText
import kotlin.time.measureTime

typealias CharGrid = List<List<Char>>
typealias IntGrid = List<List<Int>>

fun IntGrid.toMappedGrid() = this.flatMapIndexed { row, rowLine ->
    rowLine.mapIndexed { col, c ->
        Coordinate(row, col) to c
    }
}.toMap().withDefault { -1 }

data class Coordinate(val row: Int, val col: Int)

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = Path("src/$name.txt").readText().trim().lines()
fun readInputOneLine(name: String) = Path("src/$name.txt").readText().trim()
fun readInputCharGrid(name: String): CharGrid = readInput(name).map { it -> it.toList() }
fun readInputIntGrid(name: String): IntGrid = readInput(name).map { it -> it.map { c -> c.digitToInt() } }

/**
 * The cleaner shorthand for printing output.
 */
fun Any?.println() = println(this)

fun Any?.prettyPrint(rgbHex: String) = Terminal().println(TextColors.rgb(rgbHex)(this.toString()))


fun String.toNumberArray() = this.trim().split(" ").map { it.toInt() }
fun String.toBigIntArray() = this.trim().split(" ").map { it.toBigInteger() }

@Suppress("unused")
fun printGridWithOverlay(baseGrid: CharGrid, others: Set<Coordinate>) {
    val terminal = Terminal()
    baseGrid.forEachIndexed { r, row ->
        row.forEachIndexed { c, col ->
            if (Coordinate(r, c) in others) {
                terminal.print(TextColors.brightGreen("#"))
            } else {
                terminal.print(TextColors.gray(col.toString()))
            }
        }
        terminal.println()

    }
}

@Suppress("unused")
fun printCoordinates(grid: Map<Coordinate, Any>) {
    val terminal = Terminal()
    val rows = grid.keys.maxOfOrNull { it.row } ?: 0
    val cols = grid.keys.maxOfOrNull { it.col } ?: 0
    (0..rows).forEach { row ->
        (0..cols).forEach { col ->
            terminal.print(TextColors.brightGreen(grid[Coordinate(row, col)]!!.toString()))
        }
        terminal.println()
    }
}

fun CharGrid.createCoordinateGrid(): MutableMap<Char, MutableList<Coordinate>> {
    val coordinateMap: MutableMap<Char, MutableList<Coordinate>> = mutableMapOf()
    this.forEachIndexed { row, _ ->
        this[row].forEachIndexed { col, c ->
            if (c != EMPTY) {
                coordinateMap.putIfAbsent(c, mutableListOf())
                coordinateMap[c]!!.add(Coordinate(row, col))
            }
        }
    }
    return coordinateMap
}


@Suppress("unused")
enum class Direction(val row: Int, val col: Int) {
    UP(-1, 0), RIGHT(0, 1), DOWN(1, 0), LEFT(0, -1),
}

@Suppress("unused")
enum class Compass(val row: Int, val col: Int) {
    N(-1, 0), E(0, 1), S(1, 0), W(0, -1), NE(-1, 1), NW(-1, -1), SE(1, 1), SW(1, -1)
}


fun Coordinate.neighbours() = Direction.entries.map {
    Coordinate(this.row + it.row, this.col + it.col)
}.toSet()


fun <T> execute(first: () -> T, second: () -> T) {
    val measure = measureTime {
        first()
        second()
    }
    "Total: $measure".prettyPrint("#FBC6CF")
}