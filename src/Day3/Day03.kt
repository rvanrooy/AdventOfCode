package Day3

import readInput

fun findSymbol(schematic: MutableList<List<Char>>, rowIndex: Int,  charIndexStart: Int,  charIndexEnd: Int):Boolean {


    val start = if (charIndexStart < 0)   0 else charIndexStart
    val end = if (charIndexEnd > schematic[0].size) schematic[0].size-1 else charIndexEnd
    val testString = schematic[rowIndex].subList(start, end)
    return schematic[rowIndex].subList(start, end).any { it: Char -> (!it.isDigit()) && it != '.' }
}

fun main() {

    var schematic: MutableList<List<Char>> = mutableListOf()
    var input = readInput("day3/part1").toList()

    input.forEach { it ->
        schematic.add(it.toList())
    }

//    println(schematic)


    var partTotal: Int = 0
    schematic.mapIndexedNotNull {rowIndex, row ->
       println("=============================================")
        println(schematic.elementAtOrNull(rowIndex-1))
        println(row)
        println(schematic.elementAtOrNull(rowIndex+1))
        println("=============================================")

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
println(number)


                    var aboveValid: Boolean = false
                    var inlineValid: Boolean = false
                    var belowValid: Boolean = false

                    //now find whether there are any symbols obove, inline or below the index

                    //find above
                    if (rowIndex == 0)
                        aboveValid = false
                    else {
                        aboveValid =  findSymbol(schematic, rowIndex-1, charIndex-1, endIndex+1)
                    }

                    //find Inline
                    inlineValid = findSymbol(schematic, rowIndex, charIndex-1, endIndex+1)

                    if (rowIndex == schematic.size-1) {
                        belowValid = false
                    }
                    else {
                        belowValid = findSymbol(schematic, rowIndex+1, charIndex-1, endIndex+1)
                    }

                    if (aboveValid || inlineValid || belowValid) {
//                        println("$number is valid $aboveValid $inlineValid $belowValid")
                        partTotal += number
                    }
                    else {
                        println("$number not valid $aboveValid $inlineValid $belowValid on Row $rowIndex")

                    }
                }


            }

        }
    }
    println(partTotal)

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
