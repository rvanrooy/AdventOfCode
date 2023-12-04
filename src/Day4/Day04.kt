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

class Stack<T>{
    val elements: MutableList<T> = mutableListOf()
    fun isEmpty() = elements.isEmpty()
    fun count() = elements.size
    fun push(item: T) = elements.add(item)
    fun pop() : T? {
        val item = elements.lastOrNull()
        if (!isEmpty()){
            elements.removeAt(elements.size -1)
        }
        return item
    }
    fun peek() : T? = elements.lastOrNull()

    override fun toString(): String = elements.toString()
}

fun <T> Stack<T>.push(items: Collection<T>) = items.forEach { this.push(it) }


fun main() {

    val input = readInput("day4/part1")

    var cards: MutableList<Card> = mutableListOf()
    var cardStack: Stack<Card> = Stack()
    input.forEach{ it -> cards.add(Card(it))
    cardStack.push(Card(it))
    }


    println(cards.sumOf{it.calculateScore()})
var cardCount =0

    while (cardStack.count() > 0) {

        val card = cardStack.pop()

        cardCount++
        val numberOfCardsToAdd = card?.calculateNumberOfMatchingNumbers()
        if (numberOfCardsToAdd != null) {
            if (numberOfCardsToAdd > 0) {
                val cardNumber  = card?.cardNumber

                for (i in cardNumber!!+1 .. cardNumber+numberOfCardsToAdd) {

                    val cardToAdd = cards.first {it.cardNumber == i}

                    cardStack.push(cardToAdd)


                }
            }
        }

    }

    println(cardCount)

}