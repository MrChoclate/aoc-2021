package aoc.day10

fun main() {
    val resourcePath = "day10.txt"
    val count = part2(resourcePath)

    println(count)
}

val points = mapOf(')' to 3, ']' to 57, '}' to 1197, '>' to 25137)
val points2 = mapOf('(' to 1, '[' to 2, '{' to 3, '<' to 4)

fun part1(resourcePath: String): Int {
    val content = ClassLoader.getSystemResource(resourcePath).readText()
    val lines = content.split("\n")
    return lines.map { line ->
        computePoint(line)
    }.filterNotNull().sum()
}

private fun computePoint(line: String): Int? {
    val stack = mutableListOf<Char>()
    for (char in line) {
        when (char) {
            '[', '(', '<', '{' -> stack.add(char)

            ']', ')', '>', '}' -> {
                val c = stack.removeLast()
                when (c to char) {
                    '[' to ']' -> continue
                    '(' to ')' -> continue
                    '<' to '>' -> continue
                    '{' to '}' -> continue
                    else -> return points[char]
                }
            }

            else -> continue
        }
    }
    return null
}

private fun computePoint2(line: String): Long? {
    val stack = mutableListOf<Char>()
    for (char in line) {
        when (char) {
            '[', '(', '<', '{' -> stack.add(char)

            ']', ')', '>', '}' -> {
                val c = stack.removeLast()
                when (c to char) {
                    '[' to ']' -> continue
                    '(' to ')' -> continue
                    '<' to '>' -> continue
                    '{' to '}' -> continue
                    else -> return null
                }
            }
            else -> continue
        }
    }
    var score = 0L
    stack.reversed().forEach { char ->
        score *= 5
        score += points2[char]!!
    }

    return score
}


fun part2(resourcePath: String): Long {
    val content = ClassLoader.getSystemResource(resourcePath).readText()
    val lines = content.split("\n")
    val scores = lines.mapNotNull { line ->
        computePoint2(line)
    }.sorted()
    return scores[scores.size / 2]
}
