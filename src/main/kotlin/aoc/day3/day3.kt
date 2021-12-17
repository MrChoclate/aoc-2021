package aoc.day3

import java.lang.Error

fun main() {
    val resourcePath = "day3.txt"
    val count = part2(resourcePath)

    println(count)
}


fun part1(resourcePath: String): Int {
    val content = ClassLoader.getSystemResource(resourcePath).readText()
    val bytes = content.split("\n").map { it.toInt(2) }
    val byteSize = content.split("\n").first().length

    var gammaRate = 0
    var espilonRate = 0
    for (i in 0 until byteSize) {
        val mask = 1 shl i
        val (ones, zeros) = bytes.partition { (it and mask) == mask }
        if (ones.size > zeros.size) {
            gammaRate += mask
        } else {
            espilonRate += mask
        }
    }
    return gammaRate * espilonRate
}

fun part2(resourcePath: String): Int {
    val content = ClassLoader.getSystemResource(resourcePath).readText()
    val bytes = content.split("\n").map { it.toInt(2) }
    val byteSize = content.split("\n").first().length

    return computeRating(byteSize, bytes, true) * computeRating(byteSize, bytes, false)
}

private fun computeRating(byteSize: Int, bytes: List<Int>, keepOne: Boolean): Int {
    var search = bytes
    for (i in byteSize - 1 downTo 0) {
        val mask = 1 shl i
        val (ones, zeros) = search.partition { (it and mask) == mask }
        search = if ((ones.size >= zeros.size) xor !keepOne) {
            ones
        } else {
            zeros
        }

        if (search.size == 1) {
            return search.first()
        }
    }
    return search.first()
}