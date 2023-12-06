package Day5v2

import readInput


enum class ResourceType {
    SEED,
    SOIL,
    FERTILIZER,
    WATER,
    LIGHT,
    TEMPERATURE,
    HUMIDITY,
    LOCATION,
    UNKNOWN
}
fun getNextTypeFromType(resourceType: ResourceType): ResourceType {
    return when (resourceType) {
        ResourceType.SEED -> ResourceType.SOIL
        ResourceType.SOIL -> ResourceType.FERTILIZER
        ResourceType.FERTILIZER -> ResourceType.WATER
        ResourceType.WATER -> ResourceType.LIGHT
        ResourceType.LIGHT -> ResourceType.TEMPERATURE
        ResourceType.TEMPERATURE -> ResourceType.HUMIDITY
        ResourceType.HUMIDITY -> ResourceType.LOCATION
        else -> ResourceType.UNKNOWN

    }
}

fun resourceIndexFromType(resourceType: ResourceType): Int {
    return when (resourceType) {
        ResourceType.SEED -> 0
        ResourceType.SOIL -> 1
        ResourceType.FERTILIZER -> 2
        ResourceType.WATER -> 3
        ResourceType.LIGHT -> 4
        ResourceType.TEMPERATURE -> 5
        ResourceType.HUMIDITY -> 6
        ResourceType.LOCATION -> 7
        else -> 8
    }
}

fun getTypeFromString(type: String): ResourceType {
    return when (type) {
        "seed" -> ResourceType.SEED
        "soil" -> ResourceType.SOIL
        "fertilizer" -> ResourceType.FERTILIZER
        "water" -> ResourceType.WATER
        "light" -> ResourceType.LIGHT
        "temperature" -> ResourceType.TEMPERATURE
        "humidty" -> ResourceType.HUMIDITY
        "location" -> ResourceType.LOCATION
        else -> ResourceType.UNKNOWN
    }
}

data class ResourceMap(val entries: MutableList<MapEntry> = mutableListOf()) {

    fun getResource(type: ResourceType, index: Long): Resource =
        entries.firstOrNull { it.fromResource == type && it.exists(index) }?.getResource(index) ?: Resource(
            type,
            index,
            getNextTypeFromType(type),
            index
        )


    fun generateResourceTreeFromSeed(number: Long): MutableList<Long> {

        //step one. Find the resourceMapEntry that contains this seed

        var map: MutableList<Long> = mutableListOf()
        val entry =
            entries.firstOrNull { it.fromResource == ResourceType.SEED && it.exists(number) }?.getResource(number)
                ?: Resource(ResourceType.SEED, number, ResourceType.SOIL, number)

//        println(entry)

        var nextRecord = entry

        val startIndex = (resourceIndexFromType(entry.resourceType))
        for (i in startIndex until 8) {
            map.add(nextRecord.resourceIndex)
            val destinationResource = getResource(nextRecord.toResource, nextRecord.destinationIndex)
            nextRecord = destinationResource
        }
        return map
    }

}

data class MapEntry(
    val fromResource: ResourceType,
    val toResource: ResourceType,
    val destinationIndex: Long,
    val resourceIndex: Long,
    val range: Long
) {
    fun exists(index: Long): Boolean =
        (index in resourceIndex..resourceIndex + range)

    fun getResource(number: Long): Resource {

        //destinationIndex is the start of the range,
        //given an index,


        //make sure the number exists in range
        if (!exists(number))
        {
            println("this value should not exist in this MapEntry")
        }
        val calculatedDestinationIndex = destinationIndex + (number - resourceIndex)
        if (calculatedDestinationIndex < 0) {
            println("we have problem")
        }

        return Resource(fromResource, number, toResource, calculatedDestinationIndex)

    }
}

data class Resource(
    val resourceType: ResourceType,
    val resourceIndex: Long,
    val toResource: ResourceType,
    val destinationIndex: Long
)

fun main() {

    val input = readInput("Day5/part1")

//    println(input.size)

    val seedList: MutableList<Long> = mutableListOf()
    var lineNumber = 0
    val iter = input.iterator()


    var fromResource = ""
    var toResouce = ""
    var empty = ""
    var prevLineNewLine = false
    val resourceMap = ResourceMap()
    while (iter.hasNext()) {
        val line = iter.next()
        lineNumber++

        if (lineNumber == 1) {
            seedList.addAll(line.split(":")[1].trim().split(' ').map { it.toLong() })
        } else if (line.length == 0) {
            // new line, reset the mapping of resources
            fromResource = ""
            var toResouce = ""
            prevLineNewLine = true
        } else {
            // if the last line was new line, I'm expecting new resource information
            if (prevLineNewLine) {
                prevLineNewLine = false
                val resourceList = line.split(' ')[0].split('-')
                fromResource = resourceList[0]
                toResouce = resourceList[2]
            } else {

                val resourceMapLine = line.split(' ').map { it.toLong() }

                val range = resourceMapLine[2]
                val resouceNumber = resourceMapLine[1]
                val destinationNumber = resourceMapLine[0]


                // create the map entry
                val entry = MapEntry(
                    getTypeFromString(fromResource),
                    getTypeFromString(toResouce),
                    destinationNumber,
                    resouceNumber,
                    range
                )

                resourceMap.entries.add(entry)
            }

        }

    }


//    println(resourceMap.entries.size)
//    println(seedList.size)

    var minLocation: Long = 100000000000
//    val allSeeds: MutableList<Long> = mutableListOf()
    for (i in 0 until seedList.size - 1 step 2) {


        println("starting with ${seedList[i]} and adding ${seedList[i + 1]} seeds")

        for (j in (seedList[i] until seedList[i] + seedList[i + 1])) {

            val location = resourceMap.generateResourceTreeFromSeed(j)[7]
            if (location < minLocation) {

                println("$minLocation replaced by $location")
                minLocation = location
            }


        }
        println("minLocation after seed set is $minLocation")

    }

    println(minLocation)
//    val fullMap = seedList.map{ resourceMap.generateResourceTreeFromSeed(it)}.minOf {it[7]}
//    println(fullMap)
//
    //val completeResouceMap


}