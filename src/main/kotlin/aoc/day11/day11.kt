package aoc.day11

fun main() {
    val resourcePath = "day11.txt"
    val count = part2(resourcePath)

    println(count)
}

data class Point(val x: Int, val y: Int)
data class Octopus(val energy: Int, val hasFlashed: Boolean = false)

fun part1(resourcePath: String): Int {
    var map = readInput(resourcePath)
    var flashesCount = 0
    repeat(100) {
        val (newMap, newFlashes) = nextStep(map)
        map = newMap
        flashesCount += newFlashes
    }
    return flashesCount
}

fun nextStep(map: Map<Point, Octopus>): Pair<MutableMap<Point, Octopus>, Int> {
    var flashesCount = 0
    val mutableMap = map.toMutableMap()
    mutableMap.forEach { (point, octopus) ->
        flashesCount += increaseEnergy(mutableMap, point, octopus)
    }
    mutableMap.forEach { (point, octopus) ->
        if (octopus.hasFlashed) {
            mutableMap[point] = Octopus(energy = 0, hasFlashed = false)
        }
    }
    return mutableMap to flashesCount
}

private fun increaseEnergy(
    mutableMap: MutableMap<Point, Octopus>,
    point: Point,
    octopus: Octopus,
): Int {
    var flashesCount = 0
    mutableMap[point] = octopus.copy(energy = octopus.energy + 1)
    if (!octopus.hasFlashed && octopus.energy >= 9) {
        mutableMap[point] = octopus.copy(hasFlashed = true)
        flashesCount += flash(mutableMap, point)
    }
    return flashesCount
}

fun flash(mutableMap: MutableMap<Point, Octopus>, point: Point): Int {
    var flashCount = 1
    for (p in neighbours(point)) {
        flashCount += increaseEnergy(mutableMap, p, mutableMap[p]!!)
    }
    return flashCount
}

fun neighbours(point: Point): List<Point> {
    return listOf(-1, 0, 1).map { x ->
        listOf(-1, 0, 1).map { y ->
            val newX = point.x + x
            val newY = point.y + y
            if (newX in (0..9) && newY in (0..9)) {
                Point(newX, newY)
            } else {
                null
            }
        }
    }.flatten().filterNotNull()
}

private fun readInput(resourcePath: String): Map<Point, Octopus> {
    val content = ClassLoader.getSystemResource(resourcePath).readText()
    val lines = content.split("\n")
    val map = mutableMapOf<Point, Octopus>()
    lines.forEachIndexed { y, line ->
        line.forEachIndexed { x, energy ->
            val octopus = Octopus(energy.toString().toInt())
            map[Point(x, y)] = octopus
        }
    }
    return map
}

fun part2(resourcePath: String): Int {
    var map = readInput(resourcePath)
    var stepCount = 0
    while (!map.all { it.value.energy == 0 }) {
        map = nextStep(map).component1()
        stepCount += 1
    }
    return stepCount
}
