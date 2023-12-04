package Day3part2

import readInput

data class Gear(val row: Int, val col: Int, val numbers: MutableList<Int> = mutableListOf()) {
    fun addSchematic(number: Int) {
        numbers.add(number)
    }
}
class Gears {

     val gearsList: MutableList<Gear> = mutableListOf()

       fun get(row: Int, col: Int): Gear {

           //find a gear at these coords
          var gear =  gearsList.find { it -> it.col == col && it.row == row}

           if (gear == null) {
               gear =  Gear(row,col, mutableListOf())
               gearsList.add(gear)
               return gear
           }

           return gear
        }



}

val gears = Gears()


data class SchematicNumber(val number: Int) {

}


fun adjectentToGear(schematic: MutableList<List<Char>>, rowIndex: Int,  charIndexStart: Int,  charIndexEnd: Int, number: Int) {


    val start = if (charIndexStart < 0)   0 else charIndexStart
    val end = if (charIndexEnd > schematic[0].size) schematic[0].size-1 else charIndexEnd

    for (col in (start .. end-1)) {
        if (schematic[rowIndex][col] == '*') {

            gears.get(rowIndex,col).addSchematic(number)
        }

    }


}

fun main() {


    var schematicNumberList: MutableList<SchematicNumber> = mutableListOf()
    var schematic: MutableList<List<Char>> = mutableListOf()
    var input = readInput("day3/part1").toList()

    input.forEach { it ->
        schematic.add(it.toList())
    }

//    println(schematic)


    var partTotal: Int = 0
    schematic.mapIndexedNotNull {rowIndex, row ->
//       println("=============================================")
//        println(schematic.elementAtOrNull(rowIndex-1))
//        println(row)
//        println(schematic.elementAtOrNull(rowIndex+1))
//        println("=============================================")

        row.mapIndexedNotNull { charIndex, char ->
            if (char.isDigit()) {


                if (charIndex > 0 && row[charIndex-1].isDigit()) {
                    //I have already run things, so I don't need to compute this
                }
                else {
                    var endIndex = findIndexOfNextNonDigit(row, charIndex)


                    var trimmedEndIndex = endIndex

                    if (!(row[endIndex].isDigit())) {
                        //there was a symbol after the number
                        trimmedEndIndex--
                    }
                    var number = row.subList(charIndex, trimmedEndIndex+1).joinToString("").toInt()
//println(number)


                    var schematicNumber = SchematicNumber(number)

                    var aboveValid: Boolean = false
                    var inlineValid: Boolean = false
                    var belowValid: Boolean = false

                    //now find whether there are any symbols obove, inline or below the index

                    //find above
                    if (rowIndex == 0)
                        aboveValid = false
                    else {
//                        aboveValid =  findSymbol(schematic, rowIndex-1, charIndex-1, endIndex+1)
                        (adjectentToGear(schematic, rowIndex-1, charIndex-1, endIndex+1, number))
                    }

                    //find Inline
                    (adjectentToGear(schematic, rowIndex, charIndex-1, endIndex+1,number))

                    if (rowIndex == schematic.size-1) {
                        belowValid = false
                    }
                    else {
                        (adjectentToGear(schematic, rowIndex+1, charIndex-1, endIndex+1,number))
                    }


                }


            }

        }
    }



    var adjacentGears  = gears.gearsList.filter { it: Gear -> it.numbers.size == 2}

    println(adjacentGears.sumOf { it: Gear -> it.numbers[0] * it.numbers[1]})


}

fun findIndexOfNextNonDigit(row: List<Char>, charIndex: Int): Int {


    for (i in charIndex .. row.size-1) {

        if (row[i].isDigit()) continue

        return i


    }
    return row.size-1

//    (charIndex .. row.size).map { i ->
//        if (row[i].isDigit())
//
//
//    }

}
