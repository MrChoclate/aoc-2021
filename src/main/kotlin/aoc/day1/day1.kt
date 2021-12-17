package aoc.day1

fun main() {
    val resourcePath = "day1.txt"
    val count = part2(resourcePath)

    println(count)
}

fun part1(resourcePath: String): Int {
    val content = ClassLoader.getSystemResource(resourcePath).readText()
    val numbers = content.split("\n").map { it.toInt() }
    return countIncreased(numbers)
}

fun part2(resourcePath: String): Int {
    val content = ClassLoader.getSystemResource(resourcePath).readText()
    val numbers = content.split("\n").map { it.toInt() }
    val windows = numbers.windowed(size = 3) { it.sum() }
    return countIncreased(windows)
}

private fun countIncreased(numbers: List<Int>) =
    numbers.windowed(2).count { it[0] < it[1] }
