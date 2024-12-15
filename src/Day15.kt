enum class WarehouseItem(val representation: Char) {
    ROBOT(representation = '@'), WALL(representation = '#'), NOTHING(representation = '.'), BOX(representation = 'O');

    override fun toString(): String {
        return representation.toString() // working!
    }
}

enum class WarehouseMove(val representation: Char, val row: Int, val col: Int) {
    UP('^', row = -1, col = 0), DOWN('v', row = 1, col = 0), LEFT('<', row = 0, col = -1), RIGHT('>', row = 0, col = 1),
}

fun Coordinate.goodsPositioning() = (this.row * 100) + this.col

typealias Warehouse = MutableMap<Coordinate, WarehouseItem>

fun main() {
    val dayNumber = "Day15"

    fun parseInput(input: String): Pair<Warehouse, List<WarehouseMove>> {
        val (warehouseRaw, movesRaw) = input.split("\n\n").map { it.trim() }
        val warehouseMapper = WarehouseItem.entries.toTypedArray().associateBy({ it.representation }, { it })
        val movesMapper = WarehouseMove.entries.toTypedArray().associateBy({ it.representation }, { it })
        val warehouse: Warehouse = hashMapOf()
        warehouseRaw.split("\n").forEachIndexed { ri, line ->
            line.forEachIndexed { ci, c -> warehouse[Coordinate(ri, ci)] = warehouseMapper.getValue(c) }
        }

        val moves = movesRaw.filter { it in movesMapper }.map { movesMapper.getValue(it) }
        return warehouse to moves
    }

    fun checkMove(coordinate: Coordinate, move: WarehouseMove) = Coordinate(coordinate.row + move.row, coordinate.col + move.col)

    tailrec fun getNextEmptyOrWall(coordinate: Coordinate, move: WarehouseMove, warehouse: Warehouse): Coordinate {
        val next = checkMove(coordinate, move)
        if (warehouse[next] == WarehouseItem.BOX) {
            return getNextEmptyOrWall(next, move, warehouse)
        }
        return next
    }

    fun moveBoxesAndSelf(robotPosition: Coordinate, move: WarehouseMove, warehouse: Warehouse): Coordinate {
        val boxPosition = checkMove(robotPosition, move)
        val nextEmptyOrWall = getNextEmptyOrWall(boxPosition, move, warehouse)
        return if (warehouse[nextEmptyOrWall] == WarehouseItem.WALL) {
            robotPosition // no move
        } else {
            warehouse[nextEmptyOrWall] = WarehouseItem.BOX
            warehouse[boxPosition] = WarehouseItem.NOTHING
            boxPosition
        }
    }

    fun part1(input: String): Int {
        val (warehouse, moves) = parseInput(input)
        var robotPosition = warehouse.filterValues { it == WarehouseItem.ROBOT }.keys.first()
        moves.forEach { mv ->
            val nextSpot = Coordinate(robotPosition.row + mv.row, robotPosition.col + mv.col)
            robotPosition = when (warehouse[nextSpot]) {
                WarehouseItem.NOTHING -> nextSpot
                WarehouseItem.BOX -> moveBoxesAndSelf(robotPosition, mv, warehouse)
                WarehouseItem.WALL -> robotPosition
                else -> throw IllegalArgumentException("Unknown move/position")
            }
            //printCoordinates(warehouse)
        }
        return warehouse.filterValues { it == WarehouseItem.BOX  }.keys.sumOf { it.goodsPositioning() }
    }

    fun part2(input: String): Int = input.length

    val testData = readInputOneLine("${dayNumber}_test")
    part1(testData).println()
    check(part1(testData) == 10092)
//    check(part2(testData) == 0)
    execute({
        val actualData = readInputOneLine(dayNumber)
        part1(actualData).println()
    }, {
        val actualData = readInputOneLine(dayNumber)
        part2(actualData).println()
    })
}
