import kotlin.time.measureTime

fun main() {
    val dayNumber = "Day09"

    data class Block(val position: Int, val length: Int, val id: Int)

    fun parse(input: String): Pair<MutableList<Block>, MutableList<Block>> {
        val spaces = mutableListOf<Block>()
        val files = mutableListOf<Block>()

        var position = 0
        input.map { it.digitToInt() }.forEachIndexed { i, length ->
            (if (i % 2 == 0) files else spaces).add(Block(position, length, i / 2))
            position += length
        }

        return spaces to files
    }

    fun calc(spaces: MutableList<Block>, files: MutableList<Block>): Long {
        for (fi in files.indices.reversed()) {
            val (fp, fl, fid) = files[fi]

            for (si in spaces.indices) {
                val (sp, sl, sid) = spaces[si]

                if (sp >= fp) break

                if (sl >= fl) {
                    files[fi] = Block(sp, fl, fid)
                    spaces[si] = Block(sp + fl, sl - fl, sid)
                    break
                }
            }
        }

        return files.sumOf { f -> (0..<f.length).sumOf { i -> f.id.toLong() * (f.position + i) } }
    }


    fun part1(input: String): Long {
        val (spaces, files) = parse(input)
        val spacesFlattened = spaces.flatMap { s -> (0..<s.length).map { i -> Block(s.position + i, 1, s.id) } }
        val filesFlattened = files.flatMap { f -> (0..<f.length).map { i -> Block(f.position + i, 1, f.id) } }
        return calc(spacesFlattened.toMutableList(), filesFlattened.toMutableList())
    }

    fun part2(input: String): Long {
        val (spaces, files) = parse(input)
        return calc(spaces.toMutableList(), files.toMutableList())    }

    var p1: Long
    var p2: Long
    val timeTakenTotal = measureTime {
        val testData = readInputOneLine("${dayNumber}_test")
        val timeTakenTests = measureTime {
            check(part1(testData) == 1928L)
            check(part2(testData) == 2858L)
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
// 1239952463 too low
    p1.prettyPrint("#FBC6EA")
    p2.prettyPrint("#FBC6EB")
}