package Day5

import readInput
val resourceTypeList = arrayOf("seed","soil", "fertilizer", "water", "light", "temperature", "humidity", "location","null")


data class RecordList (val resources: MutableList<MapRecord>) {

    fun get(resourceType: String, index: Long): MapRecord? {
        return this.resources.firstOrNull { it.resourceType == resourceType && it.resourceNumber == index}
    }
    fun getOrCreate(resourceType: String, index: Long): MapRecord {

        val resource = this.resources.firstOrNull { it.resourceType == resourceType && it.resourceNumber == index}
        if (resource == null) {
            val newResource = MapRecord(resourceType, index)
            this.resources.add(newResource)
            return newResource
        }
        return resource

    }
}


fun iterateThroughRecord( record: MapRecord, recordList: RecordList): MutableList<Long> {

    val map :MutableList<Long> = mutableListOf()


    var nextRecord = record

    for ( i in (resourceTypeList.indexOfFirst{ it == record.resourceType}) until resourceTypeList.size-1) {


        map.add(nextRecord.resourceNumber)
         nextRecord = nextRecord.linkedResource ?: recordList.getOrCreate(resourceTypeList[resourceTypeList.indexOfFirst{ it == nextRecord.resourceType}+1], nextRecord.resourceNumber )



    }
    return map


}

//fun traverseResourceMap(record: MapRecord, recordList: RecordList): MutableList<Int>{
//
//
//    if (record.resourceType == "location") {
//        return mutableListOf(record.resourceNumber)
//    }
//
//        return traverseResourceMap(record.linkedResource ?: recordList.getOrCreate(resourceTypeList[resourceTypeList.indexOfFirst{ it == record.resourceType}+1], record.resourceNumber ), records )
//
//}


data class MapRecord (val resourceType: String, val resourceNumber: Long, public var linkedResource: MapRecord? = null) {
    fun setDestinationMap(toRecord: MapRecord) {
        this.linkedResource = toRecord
    }

    fun getLinkedResourceIndex(): Long
    {
        return linkedResource?.resourceNumber ?: resourceNumber
    }
}



fun main() {

    val input  = readInput("Day5/part1")

    println(input.size)

    val seedList: MutableList<Long> = mutableListOf()
    var lineNumber = 0
    val iter = input.iterator()


    var fromResource = ""
    var toResouce = ""
    var empty = ""
    var prevLineNewLine = false
    val records = RecordList(mutableListOf())
    while (iter.hasNext()) {
        val line = iter.next()
        lineNumber++

        if (lineNumber == 1)
        {
            seedList.addAll(line.split(":")[1].trim().split(' ').map{ it.toLong()})
        }
        else if (line.length == 0){
            // new line, reset the mapping of resources
            fromResource = ""
            var toResouce = ""
            prevLineNewLine = true
        }
        else {
            // if the last line was new line, I'm expecting new resource information
            if (prevLineNewLine) {
                prevLineNewLine = false
                val resourceList = line.split(' ')[0].split('-')
                fromResource = resourceList[0]
                toResouce = resourceList[2]
            } else {
                //else, I'm expecting a list of numbers
                val resourceMap = line.split(' ').map { it.toLong() }

                val start = resourceMap[2]
                val resouceNumber = resourceMap[1]
                val destinationNumber = resourceMap[0]

                //parse the map and create the right resources and mappings.
                for(i in (0 until start)) {
                    //create and add destination Resource
                    val fromRecord = records.getOrCreate(fromResource, resouceNumber+i)
                    val toRecord = records.getOrCreate(toResouce,destinationNumber+i)
                    fromRecord.linkedResource = toRecord
                }
            }

        }

    }

    //in theory, I have added all resources, and linked them According to the  map
    println(records.resources.size)



    val seedsLookup = seedList.map{records.getOrCreate("seed", it)}
    seedsLookup.forEach{ println(iterateThroughRecord(it, records))}
    println(records.resources.size)
//    println(seedsLookup)


    //val completeResouceMap



}