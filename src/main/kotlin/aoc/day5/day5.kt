package aoc.day5

import kotlin.math.sign

fun main() {
    val resourcePath = "day5.txt"
    val count = part2(resourcePath)

    println(count)
}

fun <T> List<T>.toPair(): Pair<T, T> {
    require (this.size == 2) { "List is not of length 2!" }
    val (a, b) = this
    return Pair(a, b)
}

data class Wind(val begin: Pair<Int, Int>, val end: Pair<Int, Int>) {
    fun points(): List<Pair<Int, Int>> {
        val xMin = minOf(begin.first, end.first)
        val xMax = maxOf(begin.first, end.first)
        val yMin = minOf(begin.second, end.second)
        val yMax = maxOf(begin.second, end.second)

        val list = mutableListOf<Pair<Int, Int>>()
        for (i in 0 until maxOf(xMax - xMin, yMax - yMin) + 1) {
            val xSign = sign((end.first - begin.first).toFloat()).toInt()
            val ySign = sign((end.second - begin.second).toFloat()).toInt()
            list.add(begin.first + i * xSign to begin.second + i * ySign)
        }
        return list
    }

    fun isHorizontal() = begin.first == end.first
    fun isVertical() = begin.second == end.second
}

fun part1(resourcePath: String): Int {
    val winds = readInput(resourcePath)

    val counter = mutableMapOf<Pair<Int, Int>, Int>()
    for (wind in winds) {
        if (wind.isHorizontal() || wind.isVertical()) {
           for (point in wind.points()) {
               counter[point] = (counter[point] ?: 0) + 1
           }
        }
    }
    return counter.count { it.value >= 2 }
}

fun part2(resourcePath: String): Int {
    val winds = readInput(resourcePath)

    val counter = mutableMapOf<Pair<Int, Int>, Int>()
    for (wind in winds) {
        for (point in wind.points()) {
            counter[point] = (counter[point] ?: 0) + 1
        }
    }
    return counter.count { it.value >= 2 }
}

private fun readInput(resourcePath: String): List<Wind> {
    val content = ClassLoader.getSystemResource(resourcePath).readText()
    val lines = content.split("\n")
    return lines.map { line ->
        val (begin, end) = line.split(" -> ").map { tuple -> tuple.split(",").map { it.toInt() }.toPair() }
        Wind(begin = begin, end = end)
    }
}
