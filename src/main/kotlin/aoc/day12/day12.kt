package aoc.day12

fun main() {
    val resourcePath = "day12.txt"
    val count = part2(resourcePath)

    println(count)
}

fun part1(resourcePath: String): Int {
    val map = readInput(resourcePath)
    fun rec(currentNode: String, path: List<String>): Set<List<String>> {
        val children = map[currentNode] ?: listOf()
        val nodesToVisit = children.filter { node ->
            node.isBigCave() || node !in path 
        }
        if (currentNode == "end") {
            return setOf(path)
        }
        val res = mutableSetOf<List<String>>()
        for (node in nodesToVisit) {
            res += rec(node, path + node)
        }
        return res.filter { it.last() == "end" }.toSet()
    }

    return rec("start", listOf("start")).size
}

fun part2(resourcePath: String): Int {
    val map = readInput(resourcePath)
    fun rec(currentNode: String, path: List<String>, smallChosen: String): Set<List<String>> {
        if (currentNode == "end") {
            return setOf(path)
        }
        val children = map[currentNode] ?: listOf()
        val nodesToVisit = children.filter { node ->
            node.isBigCave() || node !in path || (smallChosen == node && path.count { it == smallChosen } <= 1)
        }
        val res = mutableSetOf<List<String>>()
        for (node in nodesToVisit) {
            res += rec(node, path + node, smallChosen)
        }
        return res.toSet()
    }

    val nodes = (map.keys + map.values.flatten()).toSet() - setOf("start", "end")
    return nodes.map { rec("start", listOf("start"), it) }.reduce { x, y -> x + y }.size
}

private fun String.isBigCave(): Boolean = first().isUpperCase()

private fun readInput(resourcePath: String): Map<String, List<String>> {
    val content = ClassLoader.getSystemResource(resourcePath).readText()
    val lines = content.split("\n")
    val inEdges = lines.groupBy { line -> line.split('-').first() }
        .mapValues { it.value.map { it.split('-').last() } }
    val outEdges = lines.groupBy { line -> line.split('-').last() }
        .mapValues { it.value.map { it.split('-').first() } }
    return inEdges.merge(outEdges)
}

private fun <X> Map<String, List<X>>.merge(mapValues: Map<String, List<X>>): Map<String, List<X>> {
    val res = mapValues.toMutableMap()
    forEach {
        res[it.key] = mapValues.getOrDefault(it.key, listOf()) + it.value
    }
    return res
}
