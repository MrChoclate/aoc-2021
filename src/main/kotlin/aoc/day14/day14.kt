package aoc.day14

fun main() {
    val resourcePath = "day14.txt"
    val count = part2(resourcePath)

    println(count)
}

fun part1(resourcePath: String): Int {
    var (input, rules) = readInput(resourcePath)
    repeat(10) {
        input = nextStep(input, rules)
    }

    val counts = input.groupBy { it }.mapValues { it.value.count() }
    return counts.maxOf { it.value } - counts.minOf { it.value }
}

fun nextStep(input: String, rules: Map<String, String>): String = buildString {
    append(input.first())
    input.windowed(2).forEach { pair ->
        val c = rules[pair]
        c?.let {
            append(c)
        }
        append(pair[1])
    }
}

private fun readInput(resourcePath: String): Pair<String, Map<String, String>> {
    val content = ClassLoader.getSystemResource(resourcePath).readText()
    val lines = content.split("\n")
    val input = lines[0]
    val pairs = lines.subList(2, lines.size).map { line ->
        val (pair, output) = line.split(" -> ")
        pair to output
    }
    val rules = pairs.toMap()
    return input to rules
}

fun part2(resourcePath: String): Long {
    val (input, rules) = readInput(resourcePath)
    val counts = rec(input, rules, 40)
    return counts.maxOf { it.value } - counts.minOf { it.value }
}

fun rec(input: String, rules: Map<String, String>, step: Int): Map<Char, Long> {
    val cache = mutableMapOf<Pair<Int, String>, Map<Char, Long>>()
    fun rec2(step: Int, input: String): Map<Char, Long> = cache.getOrPut(step to input) {
        if (step <= 0) {
            return@getOrPut input.groupBy { it }.mapValues { it.value.count().toLong() }
        }

        val counts = mutableMapOf<Char, Long>().withDefault { 0 }
        input.windowed(2).forEach { pair ->
            val c = rules[pair]!![0]
            counts[c] = counts.getValue(c) - 1
            counts[pair[0]] = counts.getOrDefault(pair[0], 0L) - 1
            rec2(step - 1, pair[0] + c.toString()).merge(counts)
            rec2(step - 1, c.toString() + pair[1]).merge(counts)

        }
        counts[input.first()] = counts.getOrDefault(input.first(), 0L) + 1
        return@getOrPut counts.toMap()
    }

    return rec2(step, input).toMutableMap().toMap()
}

private fun Map<Char, Long>.merge(counts: MutableMap<Char, Long>) {
    forEach {
        counts[it.key] = counts.getOrDefault(it.key, 0L) + it.value
    }
}