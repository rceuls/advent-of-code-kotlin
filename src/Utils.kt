import com.github.ajalt.mordant.rendering.TextColors
import com.github.ajalt.mordant.terminal.Terminal
import kotlin.io.path.Path
import kotlin.io.path.readText

typealias CharGrid = List<List<Char>>

data class Coordinate(val row: Int, val col: Int)

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = Path("src/$name.txt").readText().trim().lines()
fun readInputOneLine(name: String) = Path("src/$name.txt").readText().trim()
fun readInputCharGrid(name: String): CharGrid = readInput(name).map { it -> it.toList() }

/**
 * The cleaner shorthand for printing output.
 */
fun Any?.println() = println(this)

fun Any?.prettyPrint(rgbHex: String) =
    Terminal().println(TextColors.rgb(rgbHex)(this.toString()))


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

fun CharGrid.createCoordinateGrid(): MutableMap<Char, MutableList<Coordinate>> {
    val coordinateMap: MutableMap<Char, MutableList<Coordinate>> = mutableMapOf()
    this.forEachIndexed { row, _ ->
        this[row]
            .forEachIndexed { col, c ->
                if (c != EMPTY) {
                    coordinateMap.putIfAbsent(c, mutableListOf())
                    coordinateMap[c]!!.add(Coordinate(row, col))
                }
            }
    }
    return coordinateMap
}