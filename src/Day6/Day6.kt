package Day6

import readInput

fun main() {

    val lines = readInput("Day6/part1")

    val timeString = lines[0].split(" ")
    val distanceString = lines[1].split(" ")

    val timesToken = timeString.filter{it != ""}
    val times  = timesToken.subList(1, timesToken.size).reduce{ acc, str -> "$acc$str" }.toLong()


    println(times)

    val distanceToken = distanceString.filter{it != ""}
    val distance = distanceToken.subList(1, distanceToken.size).reduce{ acc, str -> "$acc$str" }.toLong()


    val list: MutableList<MutableList<Int>> = mutableListOf()
    val numOfVictories: MutableList<Int> = mutableListOf()
    for (i in 0 until 1) {

        val times = (calculatePossibleDistances(times))

        numOfVictories.add(compareWins(times, distance))

    }

    println(numOfVictories)
    println(numOfVictories.reduce(Int::times))

}

fun compareWins(times: MutableList<Long>, distance: Long): Int {

    // compare how many numbers are greater than distance
    return times.filter{it > distance}.size

}

fun calculatePossibleDistances(time: Long): MutableList<Long>{

    val distanceList = mutableListOf<Long>()
    for (i in 0 until time) {

        //the length of time held * the length of time remaining
        distanceList.add(i*1*(time-i))

    }

    return distanceList

}
