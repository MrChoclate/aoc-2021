package aoc.day13

import aoc.day13.Action.FoldX
import aoc.day13.Action.FoldY

fun main() {
    val resourcePath = "day13.txt"
    val count = part2(resourcePath)

    println(count)
}

fun part1(resourcePath: String): Int {
    val (pos, actions) = readInput(resourcePath)

    val action = actions.first()

    val dots = applyAction(pos, action)
    println(printDots(dots))
    return dots.size
}

fun applyAction(pos: List<Point>, action: Action): Set<Point> = when (action) {
    is FoldX -> pos.map { (x, y) ->
        if (x >= action.x) Point(
            x - 2 * (x - action.x),
            y
        ) else Point(x, y)
    }.toSet()
    is FoldY -> pos.map { (x, y) ->
        if (y >= action.y) Point(
            x,
            y - 2 * (y - action.y)
        ) else Point(x, y)
    }.toSet()
}

fun part2(resourcePath: String): String {
    val (pos, actions) = readInput(resourcePath)

    var dots = pos.toSet()
    for (action in actions) {
        dots = applyAction(dots.toList(), action)
    }

    return printDots(dots)
}

private fun printDots(dots: Set<Point>): String {
    val maxX = dots.map { it.x }.maxOf { it } + 1
    val maxY = dots.map { it.y }.maxOf { it } + 1
    return buildString {
        for (j in 0 until maxY) {
            for (i in 0 until maxX) {
                if (dots.any { it.x == i && it.y == j }) {
                    append("#")
                } else {
                    append(".")
                }
            }
            append("\n")
        }
    }
}

data class Point(val x: Int, val y: Int)

sealed class Action {
    class FoldX(val x: Int) : Action()
    class FoldY(val y: Int) : Action()
}

private fun readInput(resourcePath: String): Pair<List<Point>, List<Action>> {
    val content = ClassLoader.getSystemResource(resourcePath).readText()
    val lines = content.split("\n")
    val pos = mutableListOf<Point>()
    val folds = mutableListOf<Action>()
    for (line in lines) {
        if (',' in line) {
            val (x, y) = line.split(',').map { it.toInt() }
            pos.add(Point(x, y))
        }
        if ("fold along " in line) {
            if ('x' in line) {
                folds.add(FoldX(line.split('=').last().toInt()))
            }
            if ('y' in line) {
                folds.add(FoldY(line.split('=').last().toInt()))
            }
        }
    }
    return pos.toList() to folds.toList()
}

