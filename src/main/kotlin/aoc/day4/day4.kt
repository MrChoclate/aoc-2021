package aoc.day4

fun main() {
    val resourcePath = "day4.txt"
    val count = part2(resourcePath)

    println(count)
}

fun <E> transpose(xs: List<List<E>>): List<List<E>> {
    // Helpers
    fun <E> List<E>.head(): E = this.first()
    fun <E> List<E>.tail(): List<E> = this.takeLast(this.size - 1)
    fun <E> E.append(xs: List<E>): List<E> = listOf(this).plus(xs)

    xs.filter { it.isNotEmpty() }.let { ys ->
        return when (ys.isNotEmpty()) {
            true -> ys.map { it.head() }.append(transpose(ys.map { it.tail() }))
            else -> emptyList()
        }
    }
}

data class Board(val board: List<List<Int>>) {
    private val marked = mutableSetOf<Int>()

    private fun numbers() = board.flatten()

    fun mark(number: Int) {
        if (numbers().contains(number)) {
            marked.add(number)
        }
    }

    fun isDone(): Boolean {
        val rows = board.any { row -> row.all { it in marked } }
        val columns = transpose(board).any { row -> row.all { it in marked } }
        val diagonals = (0 until 5).all { board[it][it] in marked }

        return rows || columns || diagonals
    }

    fun score(number: Int): Int {
        return (board.flatten() - marked).sum() * number
    }

    fun isNotDone() = !isDone()
}

fun part1(resourcePath: String): Int {
    val (boards, numbers) = readInput(resourcePath)

    for (number in numbers) {
        boards.forEach {
            it.mark(number)
            if (it.isDone()) {
                return it.score(number)
            }
        }
    }
    return 0
}

private fun readInput(resourcePath: String): Pair<List<Board>, List<Int>> {
    val content = ClassLoader.getSystemResource(resourcePath).readText()
    val lines = content.split("\n").listIterator()
    val numbers = lines.next().split(",").map { it.toInt() }

    val boards = mutableListOf<Board>()
    var board = mutableListOf<List<Int>>()
    for (line in lines) {
        if (line.isEmpty()) {
            continue
        }

        board.add(line.split(" ").filter { it.isNotBlank() }.map { it.toInt() })

        if (board.size == 5) {
            boards.add(Board(board))
            board = mutableListOf()
        }
    }

    return boards to numbers
}

fun part2(resourcePath: String): Int {
    val (boards, numbers) = readInput(resourcePath)

    var lastPop: Board? = null
    for (number in numbers) {
        boards.filter { it.isNotDone() }.forEach {
            it.mark(number)
            if (it.isDone()) {
                lastPop = it
            }
        }
        if (boards.all { it.isDone() }) {
            return lastPop!!.score(number)
        }
    }
    return 0
}
