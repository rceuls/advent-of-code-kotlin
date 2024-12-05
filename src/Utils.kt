import com.github.ajalt.mordant.rendering.TextColors
import com.github.ajalt.mordant.terminal.Terminal
import kotlin.io.path.Path
import kotlin.io.path.readText

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = Path("src/$name.txt").readText().trim().lines()
fun readInputOneLine(name: String) = Path("src/$name.txt").readText().trim()

/**
 * The cleaner shorthand for printing output.
 */
fun Any?.println() = println(this)

fun Any?.prettyPrint(rgbHex: String) =
    Terminal().println(TextColors.rgb(rgbHex)(this.toString()))

fun String.toNumberArray() = this.split(" ").map { it.toInt() }