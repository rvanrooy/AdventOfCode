package day2

import readInput

data class GameSet(var red: Int, var green: Int, var blue: Int) {


    companion object {
        operator fun invoke(log: String): GameSet {
            val split = log.split(',')
            var set = GameSet(0,0,0)

            split.map{
                val item = it.split(' ')
                var number  = item[1]
                var color = item[2]

                when (color) {
                    "blue" -> set.blue = number.toInt()
                    "red" -> set.red = number.toInt()
                    "green" -> set.green = number.toInt()

                }
            }
            return set
        }
    }
}

data class Game (val roundNumber: Int, val rounds: MutableList<GameSet>?) {
    fun addSet(gameSet: GameSet) {
        this.rounds?.add(gameSet)
    }

    constructor(roundNumber: Int) : this(roundNumber, mutableListOf()) {

    }


}

fun main() {
//    println("hello")

    val gamesLog = readInput("day2/part1")
    println(gamesLog.size)

    var games = (gamesLog.map { generateGameFromLog(it) })

   println( games.sumOf {
        validateGame(it, 14, 12,13)
    })


    println( games.sumOf {
        findMinGame(it, 14, 12,13)
    })
//println(games)


}

fun validateGame(game: Game, blueTarget: Int, redTarget: Int, greenTarget: Int): Int {

    val blue = game.rounds?.maxOf { it -> it.blue }
    val red = game.rounds?.maxOf { it -> it.red }
    val green = game.rounds?.maxOf { it -> it.green }

//    println("blue is $blue : green is $green : red is $red")

    if ((blue!! <= blueTarget) && (green!! <= greenTarget) && (red!! <= redTarget))
        return game.roundNumber

    return 0
}

fun findMinGame(game: Game, blueTarget: Int, redTarget: Int, greenTarget: Int): Int {

    var blue = game.rounds?.maxOf { it -> it.blue }
    var red = game.rounds?.maxOf { it -> it.red }
    var green = game.rounds?.maxOf { it -> it.green }

//    println("blue is $blue : green is $green : red is $red")


    blue = if (blue!! > 0) blue else 1
    red = if (red!! > 0) red else 1
    green = if (green!! > 0) green else 1

    return blue * green * red


}

fun generateGameFromLog(it: String): Game {

    val log = it.split(':')
    val gameNumber = log[0].split(' ')[1].toInt()

    var rounds = log[1].split(';')

    var game = Game(gameNumber)
    rounds.map { game.addSet(GameSet(it))}

    return game

//    var game = Game()


}
