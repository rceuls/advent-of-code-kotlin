enum class WarehouseItem(val representation: Char) {
    ROBOT(representation = '@'),
    WALL(representation = '#'),
    NOTHING(representation = '.'),
    BOX(representation = 'O');

    override fun toString(): String {
        return representation.toString() // working!
    }
}

enum class WarehouseItemLarge(val representation: Char) {
    ROBOT(representation = '@'),
    WALL(representation = '#'),
    NOTHING(representation = '.'),
    BOX_LEFT(representation = '['),
    BOX_RIGHT(representation = ']');

    override fun toString(): String {
        return representation.toString() // working!
    }
}

enum class WarehouseMove(val representation: Char, val row: Int, val col: Int) {
    UP('^', row = -1, col = 0), DOWN('v', row = 1, col = 0), LEFT('<', row = 0, col = -1), RIGHT('>', row = 0, col = 1),
}

fun Coordinate.goodsPositioning() = (this.row * 100) + this.col

typealias Warehouse = MutableMap<Coordinate, WarehouseItem>
typealias BigWarehouse = MutableMap<Coordinate, WarehouseItemLarge>

fun main() {
    val dayNumber = "Day15"

    fun mapMoves(movesRaw: String) =
        WarehouseMove.entries.toTypedArray().associateBy({ it.representation }, { it }).let { movesMapper ->
            movesRaw.filter { it in movesMapper }.map { movesMapper.getValue(it) }
        }

    fun parseInput(input: String): Pair<Warehouse, List<WarehouseMove>> {
        val (warehouseRaw, movesRaw) = input.split("\n\n").map { it.trim() }
        val warehouseMapper = WarehouseItem.entries.toTypedArray().associateBy({ it.representation }, { it })
        val warehouse: Warehouse = hashMapOf()
        warehouseRaw.split("\n").forEachIndexed { ri, line ->
            line.forEachIndexed { ci, c -> warehouse[Coordinate(ri, ci)] = warehouseMapper.getValue(c) }
        }
        return warehouse to mapMoves(movesRaw)
    }

    fun parseInputPart2(input: String): Pair<BigWarehouse, List<WarehouseMove>> {
        val newInput = input.replace("O", "[]")
            .replace("#", "##")
            .replace(".", "..")
            .replace("@", "@.")
        val (warehouseRaw, movesRaw) = newInput.split("\n\n").map { it.trim() }
        val warehouseMapper = WarehouseItemLarge.entries.toTypedArray().associateBy({ it.representation }, { it })
        val warehouse: BigWarehouse = hashMapOf()
        warehouseRaw.split("\n").forEachIndexed { ri, line ->
            line.forEachIndexed { ci, c -> warehouse[Coordinate(ri, ci)] = warehouseMapper.getValue(c) }
        }
        return warehouse to mapMoves(movesRaw)
    }

    fun checkMove(coordinate: Coordinate, move: WarehouseMove) =
        Coordinate(coordinate.row + move.row, coordinate.col + move.col)

    tailrec fun getNextEmptyOrWall(coordinate: Coordinate, move: WarehouseMove, warehouse: Warehouse): Coordinate {
        val next = checkMove(coordinate, move)
        if (warehouse[next] == WarehouseItem.BOX) {
            return getNextEmptyOrWall(next, move, warehouse)
        }
        return next
    }

    fun canMove(coordinate: Coordinate, move: WarehouseMove, warehouse: BigWarehouse): Boolean {
        val nextPosition = checkMove(coordinate, move)
        fun isHorizontal(move: WarehouseMove): Boolean = move == WarehouseMove.RIGHT || move == WarehouseMove.LEFT
        return when (warehouse[nextPosition]) {
            WarehouseItemLarge.NOTHING -> true
            WarehouseItemLarge.WALL -> false
            WarehouseItemLarge.ROBOT -> canMove(nextPosition, move, warehouse)
            WarehouseItemLarge.BOX_LEFT -> canMove(nextPosition, move, warehouse) && (isHorizontal(move) || canMove(
                checkMove(nextPosition, WarehouseMove.RIGHT),
                move,
                warehouse
            ))

            WarehouseItemLarge.BOX_RIGHT -> canMove(nextPosition, move, warehouse) && (isHorizontal(move) || canMove(
                checkMove(nextPosition, WarehouseMove.LEFT),
                move,
                warehouse
            ))
            else -> throw IllegalArgumentException("Unknown move/position")
        }
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

    fun moveBoxesAndSelf(robotPosition: Coordinate, move: WarehouseMove, warehouse: BigWarehouse) : Coordinate {
        if (!canMove(robotPosition, move, warehouse)) return robotPosition
        var robot: Coordinate = robotPosition

        fun doTheMove(position: Coordinate) {
            val current = warehouse[position]!!
            val next = checkMove(position, move)

            if (current == WarehouseItemLarge.NOTHING) return

            doTheMove(next)

            warehouse[next] = current
            warehouse[position] = WarehouseItemLarge.NOTHING
            if (current == WarehouseItemLarge.ROBOT) robot = next
            if (move == WarehouseMove.RIGHT || move == WarehouseMove.LEFT) return

            if (current == WarehouseItemLarge.BOX_LEFT) {
                doTheMove(checkMove(next, WarehouseMove.RIGHT))
                warehouse[checkMove(next, WarehouseMove.RIGHT)] = WarehouseItemLarge.BOX_RIGHT
                warehouse[checkMove(position, WarehouseMove.RIGHT)] = WarehouseItemLarge.NOTHING
            }

            if (current == WarehouseItemLarge.BOX_RIGHT) {
                doTheMove(checkMove(next, WarehouseMove.LEFT))
                warehouse[checkMove(next, WarehouseMove.LEFT)] = WarehouseItemLarge.BOX_LEFT
                warehouse[checkMove(position, WarehouseMove.LEFT)] = WarehouseItemLarge.NOTHING
            }
        }
        doTheMove(robot)
        return robot
    }

    fun part1(input: String): Int {
        val (warehouse, moves) = parseInput(input)
        var robotPosition = warehouse.filterValues { it == WarehouseItem.ROBOT }.keys.first()
        moves.forEach { mv ->
            val previousPosition = robotPosition
            val nextSpot = Coordinate(robotPosition.row + mv.row, robotPosition.col + mv.col)
            robotPosition = when (warehouse[nextSpot]) {
                WarehouseItem.NOTHING -> nextSpot
                WarehouseItem.BOX -> moveBoxesAndSelf(robotPosition, mv, warehouse)
                WarehouseItem.WALL -> robotPosition
                else -> throw IllegalArgumentException("Unknown move/position")
            }
            warehouse[previousPosition] = WarehouseItem.NOTHING
            warehouse[robotPosition] = WarehouseItem.ROBOT
        }
        return warehouse.filterValues { it == WarehouseItem.BOX }.keys.sumOf { it.goodsPositioning() }
    }

    fun part2(input: String): Int {
        val (warehouse, moves) = parseInputPart2(input)
        var robotPosition = warehouse.filterValues { it == WarehouseItemLarge.ROBOT }.keys.first()
        moves.forEach { mv -> robotPosition = moveBoxesAndSelf(robotPosition, mv, warehouse) }
        return warehouse.filterValues { it == WarehouseItemLarge.BOX_LEFT }.keys.sumOf { it.goodsPositioning() }
    }


    val testData = readInputOneLine("${dayNumber}_test")
    check(part1(testData) == 10092)
    check(part2(testData) == 9021)
    execute({
        val actualData = readInputOneLine(dayNumber)
        part1(actualData).println()
    }, {
        val actualData = readInputOneLine(dayNumber)
        part2(actualData).println()
    })
}
