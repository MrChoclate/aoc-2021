package aoc.day8

import kotlin.math.pow

fun main() {
    val resourcePath = "day8.txt"
    val count = part2(resourcePath)

    println(count)
}

fun part1(resourcePath: String): Int {
    val content = ClassLoader.getSystemResource(resourcePath).readText()
    val outputValues = content.split("\n").map { line ->
        val (first, last) = line.split("|")
        last.split(" ")
    }.flatten()

    return outputValues.count { it.length in listOf(2, 3, 4, 7) }
}

class Line(val examples: List<String>, val outputValues: List<String>) {
    fun output(): Int {
        val sets = examples.map { it.toSet() }
        val one = sets.first { it.size == 2 }
        val four = sets.first { it.size == 4 }
        val seven = sets.first { it.size == 3 }
        val eight = sets.first { it.size == 7 }
        val bd = (four - seven)
        val zero = sets.first { it.size == 6 && !it.containsAll(bd) }
        val eg = zero - seven - four
        val six = sets.first { it.size == 6 && it.containsAll(eg) && it != zero }
        val nine = sets.first { it.size == 6 && !it.containsAll(eg) && it != zero }
        val c = (nine - six).first()
        val e = (six - nine).first()
        val two = sets.first { it.size == 5 && it.contains(e) && it.contains(c) }
        val three = sets.first { it.size == 5 && !it.contains(e) && it.contains(c) }
        val five = sets.first { it.size == 5 && !it.contains(e) && !it.contains(c) }

        return outputValues.reversed().mapIndexed { i, v ->
            when (v.toSet()) {
                zero -> 0
                one -> 1
                two -> 2
                three -> 3
                four -> 4
                five -> 5
                six -> 6
                seven -> 7
                eight -> 8
                nine -> 9
                else -> throw Error("should not happen")
            } * 10.0.pow(i)
        }.sumOf { it.toInt() }
    }
}

fun part2(resourcePath: String): Int {
    val content = ClassLoader.getSystemResource(resourcePath).readText()
    val lines = content.split("\n").map { line ->
        val (first, last) = line.split("|")
        Line(
            examples = first.split(" ").filter { it.isNotBlank() },
            outputValues = last.split(" ").filter { it.isNotBlank() }
        )
    }

    return lines.sumOf { it.output() }
}

