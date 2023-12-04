package Day4




import day2.GameSet
import readInput

data class Card(var cardNumber: Int, val winningNumbers: List<Int>, val numbers: List<Int>) {

    fun score(matches: Int): Int {
            return Math.pow(2.0,(matches-1).toDouble()).toInt()
    }

    fun calculateNumberOfMatchingNumbers(): Int {
        var matches = numbers.filter {num -> winningNumbers.any { winNum -> winNum == num }}.size
        return (matches)
    }

    fun calculateScore(): Int {
        var matches = calculateNumberOfMatchingNumbers()

        return score(matches)
    }


    companion object {
        operator fun invoke(line: String): Card {

            val (cardNumberString, numbersString) = line.split(":")
            val (winningNumberString, ownNumberString) = numbersString.split("|")

            val cardNumber = cardNumberString.trimStart().trimEnd().split(" ").last().toInt()


//            println(cardNumber)
            val winningNumbersStringList = winningNumberString.split(" ")
            val ownNumbersStringList = ownNumberString.trimStart().trimEnd().split(" ")
            val winningNumbersList = winningNumbersStringList.filter { it != "" }.map { it.toInt() }
            val ownNumbersList = ownNumbersStringList.filter { it != "" }.map { it.toInt() }

            return Card(cardNumber, winningNumbersList, ownNumbersList)

        }

    }





}




fun main() {

    val input = readInput("day4/part1")

    var cards: MutableList<Card> = mutableListOf()

    input.forEach{ it -> cards.add(Card(it))}

//    println(cards)

    println(cards.sumOf{it.calculateScore()})
}