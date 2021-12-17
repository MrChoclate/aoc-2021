package aoc.day15

import java.util.PriorityQueue

fun main() {
    val resourcePath = "day15.txt"
    val count = part2(resourcePath)
    println(count)
}

data class Point(val x: Int, val y: Int) {
    fun nextPoints(maxX: Int, maxY: Int) = listOf(
        Point(x + 1, y),
        Point(x - 1, y),
        Point(x, y - 1),
        Point(x, y + 1),
    ).filter { it.x in (0..maxX) && it.y in (0..maxY) }
}

fun part1(resourcePath: String): Int {
    val matrix = readInput(resourcePath)
    return dijkstra(matrix)
}

private fun dijkstra(matrix: List<List<Int>>): Int {
    val start = Point(0, 0)
    val distances = mutableMapOf(start to 0)
    val maxX = matrix.size - 1
    val maxY = matrix[0].size - 1
    val end = Point(maxX, maxY)
    val visited = mutableSetOf<Point>()
    val queue = PriorityQueue<Pair<Int, Point>>(
        maxX * maxY
    ) { o1, o2 -> compareValues(o1.first, o2.first) }
    queue.add(0 to start)

    while (distances[end] == null) {
        val current = pickNext(queue, visited)

        for (point in current.nextPoints(maxX, maxY)) {
            val weight = matrix[point.x][point.y]
            val dCurrent = distances[current]!!
            if (distances[point] == null || distances[point]!! > dCurrent + weight) {
                distances[point] = dCurrent + weight
            }
            queue.add(distances[point]!! to point)
        }
    }
    return distances[end]!!
}

private fun pickNext(
    queue: PriorityQueue<Pair<Int, Point>>,
    visited: MutableSet<Point>
): Point {
    var current = queue.poll().second
    while (current in visited) {
        current = queue.poll().second
    }
    visited.add(current)
    return current
}

private fun readInput(resourcePath: String): List<List<Int>> {
    val content = ClassLoader.getSystemResource(resourcePath).readText()
    return content.split("\n").map { line -> line.map { c -> c.toString().toInt() } }
}

fun part2(resourcePath: String): Int {
    val matrix = readInput(resourcePath)
    val newMatrix = mutableListOf<List<Int>>()
    for (i in 0 until 5) {
        for (x in matrix.indices) {
            val newRow = mutableListOf<Int>()
            newMatrix.add(newRow)
            for (j in 0 until 5) {
                for (y in matrix[x].indices) {
                    val newValue = (matrix[x][y] + j + i - 1) % 9 + 1
                    newRow.add(newValue)
                }
            }
        }
    }
    return dijkstra(newMatrix)
}

