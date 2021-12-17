package aoc.day6


fun main() {
    val resourcePath = "day6.txt"
    val count = part2(resourcePath)

    println(count)
}

fun part1(resourcePath: String): Int {
    var fishes = readInput(resourcePath)
    for (i in 0 until 80) {
        fishes = fishes.map { it - 1 }
        val (existingFishes, newFishes) = fishes.partition { it >= 0 }
        fishes = existingFishes + newFishes.indices.map { 6 } + newFishes.indices.map { 8 }
    }
    return fishes.size
}

fun part2(resourcePath: String): Long {
    val fishes = readInput(resourcePath)
    var mapOfFishes = fishes.groupBy { it }.mapValues { it.value.size.toLong() }.toMutableMap()
    for (i in 0 until 256) {
        mapOfFishes = mapOfFishes.mapKeys { it.key - 1 }.toMutableMap()
        val newFishes = mapOfFishes.remove(-1) ?: 0
        mapOfFishes[8] = newFishes
        mapOfFishes[6] = (mapOfFishes[6] ?: 0) + newFishes
    }
    return mapOfFishes.map { it.value }.sum()
}

private fun readInput(resourcePath: String): List<Int> {
    val content = ClassLoader.getSystemResource(resourcePath).readText()
    return content.split(",").map { it.toInt() }
}
