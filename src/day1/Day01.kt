package day1

import println
import readInput

fun main() {

    val words: Map<String, Int> = mapOf(
        "one" to 1,
        "two" to 2,
        "three" to 3,
        "four" to 4,
        "five" to 5,
        "six" to 6,
        "seven" to 7,
        "eight" to 8,
        "nine" to 9
    )

    fun String.possibleWordsAt(startingAt: Int): List<String> =
        (3 .. 5).map{ len ->
            substring(startingAt, (startingAt+len).coerceAtMost(length))
        }



    fun calibrationValue(row: String): Int =
        "${row.first {it.isDigit()}}${row.last {it.isDigit()}}".toInt()


    fun replaceWordsWithDigit(row: String): String {

        // I need to iterate through the string, from the start and identify a words, and replace it, then continue


        return row
    }



    fun calibrationValue1(row: String): Int {





        val firstDigit = row.first { it.isDigit()}
        val lastDigit =  row.last { it.isDigit()}
        return "$firstDigit$lastDigit".toInt()
    }



    fun part1(input: List<String>): Int {

       val output =  input.sumOf { calibrationValue1(it)}
        return output
    }


    fun part2(input: List<String>): Int {

        println(input)

//        return input.sumOf { calibrationValue1(it)}

       return input.sumOf { calibrationValue1(it.mapIndexedNotNull { index, c ->
            if (c.isDigit()) c
            else it.possibleWordsAt(index).firstNotNullOfOrNull { candidate -> words[candidate] }
        }.joinToString())}

    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("day1/Day01_test")
    val input = readInput("day1/Day01")

    val part2TestInput = readInput("day1/Day01_part2_test")
//    part1(testInput)

//    val input = readInput("Day01")
//    part1(testInput).println()
//    part1(input).println()

    part2(part2TestInput).println()
    part2(input).println()
}
