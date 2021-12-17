package aoc.day7

import kotlin.math.abs
import kotlin.math.ceil
import kotlin.math.floor

fun main() {
    val resourcePath = "day7.txt"
    val count = part2(resourcePath)

    println(count)
}

fun part1(resourcePath: String): Int {
    val positions = readInput(resourcePath)
    val median = positions.median()
    return positions.sumOf { abs(it - median) }
}

private fun <E: Comparable<E>> List<E>.median(): E {
    val array = sortedBy { it }
    return array[array.size / 2]
}

fun part2(resourcePath: String): Int {
    val positions = readInput(resourcePath)
    val mean = positions.mean()
    return minOf(computeCostPart2(positions, floor(mean).toInt()), computeCostPart2(positions, ceil(mean).toInt()))
}

private fun computeCostPart2(
    positions: List<Int>,
    position: Int
) = positions.sumOf {
    val n = abs(it - position)
    n * (n + 1) / 2
}

private fun List<Int>.mean(): Float {
    return sum() / size.toFloat()
}

private fun readInput(resourcePath: String): List<Int> {
    val content = ClassLoader.getSystemResource(resourcePath).readText()
    return content.split(",").map { it.toInt() }
}
