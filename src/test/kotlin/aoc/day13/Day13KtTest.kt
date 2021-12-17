package aoc.day13

import kotlin.test.Test
import kotlin.test.assertEquals

internal class Day13KtTest {
    @Test
    fun part1() {
        assertEquals(17, part1("day13.txt"))
    }

    @Test
    fun part2() {
        assertEquals("""
            #####
            #...#
            #...#
            #...#
            #####""".trimIndent() + "\n", part2("day13.txt"))
    }
}