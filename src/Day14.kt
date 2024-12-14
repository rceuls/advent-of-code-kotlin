fun main() {
    val dayNumber = "Day14"
    val numberRegex = "-?\\d+".toRegex()

    data class Point(var x: Int, var y: Int)

    data class Robot(
        val name: String,
        val position: Point,
        val velocity: Point,
    )

    fun calculateThreat(width: Int, height: Int, robots: List<Robot>): Int {
        val halfWidth = width / 2
        val halfHeight = height / 2

        val q1 = (0 until halfWidth).flatMap { x -> (0 until halfHeight).map { y -> Point(x, y) } }
        val q2 = (0 until halfWidth).flatMap { x -> (halfHeight + 1..height).map { y -> Point(x, y) } }
        val q3 = (halfWidth + 1..width).flatMap { x -> (0 until halfHeight).map { y -> Point(x, y) } }
        val q4 = (halfWidth + 1..width).flatMap { x -> (halfHeight + 1..height).map { y -> Point(x, y) } }


        val robotsQ1 = robots.count { it.position in q1 }
        val robotsQ2 = robots.count { it.position in q2 }
        val robotsQ3 = robots.count { it.position in q3 }
        val robotsQ4 = robots.count { it.position in q4 }
        val calc = robotsQ1 * robotsQ2 * robotsQ3 * robotsQ4
        return calc
    }

    fun moveRobots(robots: List<Robot>, width: Int, height: Int) {
        robots.forEach { robot ->
            robot.position.x += robot.velocity.x
            robot.position.y += robot.velocity.y
            if (robot.position.x < 0) {
                robot.position.x += (width + 1)
            }
            if (robot.position.y < 0) {
                robot.position.y += (height + 1)
            }
            if (robot.position.x > width) {
                robot.position.x -= (width + 1)
            }
            if (robot.position.y > height) {
                robot.position.y -= (height + 1)
            }
        }
    }

    fun mapRobots(input: List<String>): List<Robot> = input.mapIndexed { i, line ->
        numberRegex.findAll(line).toList().map { c -> c.value.toInt() }.toList()
            .let { (pX, pY, vX, vY) -> Robot(name = "robot $i", Point(pX, pY), Point(vX, vY)) }
    }

    fun part1(input: List<String>, width: Int = 100, height: Int = 102, iterations: Int = 99): Int =
        mapRobots(input).let { robots ->
            repeat((0..iterations).count()) {
                moveRobots(robots, width, height)
            }
            calculateThreat(width, height, robots)
        }

    fun part2(input: List<String>, width: Int = 100, height: Int = 102): Int {
        val robots = mapRobots(input)
        (1..1_000_000).forEach { i ->
            moveRobots(robots, width, height)
            if (robots.map { it.position }.distinct().count() == robots.size) {
                return i
            }
        }
        return -1
    }

    val testData = readInput("${dayNumber}_test")
    check(part1(testData, 10, 6) == 12)
    execute({
        val actualData = readInput(dayNumber)
        part1(actualData).println()
    }, {
        val actualData = readInput(dayNumber)
        part2(actualData).println()
    })
}
