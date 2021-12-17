package aoc.day2

fun main() {
    val resourcePath = "day2.txt"
    val count = part2(resourcePath)

    println(count)
}

data class Position(val depth: Int = 0, val horizontal: Int = 0, val aim: Int = 0)

fun part1(resourcePath: String): Int {
    val content = ClassLoader.getSystemResource(resourcePath).readText()
    val lines = content.split("\n")
    var position = Position()
    for (line in lines) {
        val (command, value) = line.split(' ')
        when (command) {
            "forward" -> position =
                position.copy(horizontal = position.horizontal + value.toInt())
            "down" -> position =
                position.copy(depth = position.depth + value.toInt())
            "up" -> position =
                position.copy(depth = position.depth - value.toInt())
        }
    }
    return position.depth * position.horizontal
}

fun part2(resourcePath: String): Int {
    val content = ClassLoader.getSystemResource(resourcePath).readText()
    val lines = content.split("\n")
    var position = Position()
    for (line in lines) {
        val (command, value) = line.split(' ')
        val x = value.toInt()

        when (command) {
            "forward" -> position =
                position.copy(horizontal = position.horizontal + x, depth = position.depth + position.aim * x)
            "down" -> position =
                position.copy(aim = position.aim + x)
            "up" -> position =
                position.copy(aim = position.aim - x)
        }
    }
    return position.depth * position.horizontal
}