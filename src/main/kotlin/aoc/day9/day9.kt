package aoc.day9

fun main() {
    val resourcePath = "day9.txt"
    val count = part2(resourcePath)

    println(count)
}

fun isLowPoint(matrix: List<List<Int>>, i: Int, j: Int): Boolean {
    val isUp = i - 1 !in matrix.indices || matrix[i][j] < matrix[i - 1][j]
    val isDown = i + 1 !in matrix.indices || matrix[i][j] < matrix[i + 1][j]
    val isLeft = j - 1 !in matrix[i].indices || matrix[i][j] < matrix[i][j - 1]
    val isRight = j + 1 !in matrix[i].indices || matrix[i][j] < matrix[i][j + 1]
    return isUp && isDown && isLeft && isRight
}

fun part1(resourcePath: String): Int {
    val content = ClassLoader.getSystemResource(resourcePath).readText()
    val matrix = readInput(content)
    val lowPoints = matrix.mapIndexed { i, _ ->
        matrix[i].mapIndexed { j, _ ->
            if (isLowPoint(matrix, i, j)) {
                matrix[i][j]
            } else {
                null
            }
        }
    }.flatten().filterNotNull()
    return lowPoints.sumOf { it + 1 }
}

private fun readInput(content: String) =
    content.split("\n").map { line -> line.map { it.toString().toInt() } }

fun part2(resourcePath: String): Int {
    val content = ClassLoader.getSystemResource(resourcePath).readText()
    val matrix = readInput(content)
    val lowPoints = matrix.mapIndexed { i, _ ->
        matrix[i].mapIndexed { j, _ ->
            if (isLowPoint(matrix, i, j)) {
                i to j
            } else {
                null
            }
        }
    }.flatten().filterNotNull()

    val bassinCounts = mutableMapOf<Pair<Int, Int>, Int>()

    lowPoints.forEach { (i, j) ->
        visit(matrix, i to j, i, j, bassinCounts)
    }

    return bassinCounts.values.sortedDescending().take(3).reduce { x, y -> x * y}
}

fun visit(matrix: List<List<Int>>, pair: Pair<Int, Int>, i: Int, j: Int, flowCounts: MutableMap<Pair<Int, Int>, Int>, visited: MutableSet<Pair<Int, Int>> = mutableSetOf()) {
    if (visited.contains(i to j)) {
        return
    }
    visited.add(i to j)
    if (matrix[i][j] == 9) {
        return
    }

    flowCounts[pair] = flowCounts.getOrDefault(pair, 0) + 1
    if(i - 1 in matrix.indices && matrix[i][j] < matrix[i - 1][j]) {
        visit(matrix, pair, i - 1, j, flowCounts, visited)
    }
    if(i + 1 in matrix.indices && matrix[i][j] < matrix[i + 1][j]) {
        visit(matrix, pair, i + 1, j, flowCounts, visited)
    }
    if(j - 1 in matrix[i].indices && matrix[i][j] < matrix[i][j - 1]) {
        visit(matrix, pair, i, j -1, flowCounts, visited)
    }
    if(j + 1 in matrix[i].indices && matrix[i][j] < matrix[i][j + 1]) {
        visit(matrix, pair, i, j + 1, flowCounts, visited)
    }
}

