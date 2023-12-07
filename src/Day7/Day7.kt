package Day7

import readInput


//enum class HandRank {
//    FIVE(7),
//    FOUR(6),
//    FULLHOUSE(5),
//    THREE(4),
//    TWO(3),
//    ONE(2),
//    HIGH(1)
//}


class CompareHands {
    companion object: Comparator<Hand> {
        private fun assignValueToCard(c: Char): Int = when(c) {
            'A' -> 14
            'K' -> 13
            'Q' -> 12
            'J' -> 1
            'T' -> 10
            else -> c.digitToInt()

        }

        override fun compare(h1: Hand, h2: Hand): Int {
            // hand class is different, so just sort them
            if (h1.handClass != h2.handClass)
                return (h1.handClass - h2.handClass)

            // handClass is the same, so iterate through the hand and pick the first non same card for comparison

            if (h1.hand[0] != h2.hand[0]) {
                return assignValueToCard(h1.hand[0]) -  assignValueToCard(h2.hand[0])
            }
            else if (h1.hand[1] != h2.hand[1]) {
                return assignValueToCard(h1.hand[1]) -  assignValueToCard(h2.hand[1])
            } else if (h1.hand[2] != h2.hand[2]) {
                return assignValueToCard(h1.hand[2]) -  assignValueToCard(h2.hand[2])
            }else if (h1.hand[3] != h2.hand[3]) {
                return assignValueToCard(h1.hand[3]) -  assignValueToCard(h2.hand[3])
            }else {
                return assignValueToCard(h1.hand[4]) -  assignValueToCard(h2.hand[4])
            }



        }

    }

}


data class Hand(val hand: String, val bid: Int, var rank: Int= -1) {

    var handClass: Int = 0
    val rankMap: MutableMap<Char, Int?> = mutableMapOf()
    var numJokers: Int = 0




    fun numberOf(num: Int):Int {

        if (numJokers == 0) {
            return rankMap.values.filter { it == num}.size
        }

        // we have jokers, so remove them from the rankMap.
        rankMap.remove('J')
        return rankMap.values.filter { it == num}.size


    }


    fun rankHand() {
        hand.forEach { rankMap[it] = rankMap[it]?.plus(1) ?: 1  }

    }

    fun classifyHand(): Int{

        numJokers = hand.count { it == 'J' }

        if (numJokers > 0 ) {
            println("joker")
        }


        if (numberOf(5) == 1) {
            handClass = 7
            rank = 7
            return 7
        }
        else if (numberOf(4) == 1) {
            handClass = 6 + numJokers
            rank = 6 + numJokers
            return 6+ numJokers
        }
        else if (numberOf(3) == 1 && numberOf(2) == 1) {
            handClass = 5
            rank = 5

            if (numJokers == 2 || numJokers == 3) {
                handClass = 7
                rank = 7

            }
            return handClass
        }
        else if (numberOf(3) == 1) {
            handClass = 4
            rank = 4

            when (numJokers) {
                1 -> handClass = 6
                2 -> handClass = 7

            }



            rank = handClass
            return handClass
        }
        else if (numberOf(2) == 2) {
            handClass = 3

            when (numJokers) {
                1 -> handClass = 5
                2 -> handClass = 6
                3-> handClass = 7
            }

            rank = handClass
            return handClass
        }
        else if (numberOf(2) == 1) {
            handClass = 2

            when (numJokers) {
                1 -> handClass = 4
                2 -> handClass = 6
                3-> handClass = 7
            }

            rank = handClass
            return handClass
        }
        else if (numberOf(1) == 0) {
            //edge case where there are no cards, meaning 5 jokers
            handClass = 7
            rank = handClass
            return handClass

        }
        else {
            handClass = 1

            when (numJokers) {
                1 -> handClass = 2
                2 -> handClass = 4
                3-> handClass = 6
                4-> handClass = 7
            }

            rank = handClass
            return handClass
        }
    }

}
fun main() {

    val input = readInput("Day7/part1")

    val hands: MutableList<Hand> = mutableListOf()
    input.forEach {


        val (handString, bid)  = it.split(' ')

        val hand =Hand(handString, bid.toInt())
        hands.add(hand)
        hand.rankHand()
        hand.classifyHand()



    }


    val sortedHands  = hands.sortedWith(CompareHands)

    sortedHands.forEachIndexed { index, hand -> println("${hand.hand} has hand rank ${hand.rank} and number ${index+1} in rankings with a bid of ${hand.bid}") }
    println(sortedHands.map {it.bid})
    println(sortedHands.map {it.rank})
    println(sortedHands.mapIndexed { index, ithand ->
         ithand.bid * (index+1) }.sum())


}