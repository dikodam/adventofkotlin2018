package de.dikodam.adventofkotlin

fun main(args: Array<String>) {
    Day02().runTasks()
}

class Day02 : AbstractDay() {

    private fun groupByChars(string: String): Map<Char, Int> {
        return string.asSequence()
            .groupingBy { it }
            .eachCount()
    }

    override fun task1() {
        val containsLettersNTimes: (Int) -> (String) -> Boolean =
            { nTimes ->
                { string ->
                    groupByChars(string).containsValue(nTimes)
                }
            }

        val countOfIDsWith2Letters = inputLines.filter(containsLettersNTimes(2)).count()
        val countOfIDsWith3Letters = inputLines.filter(containsLettersNTimes(3)).count()

        val checksum = countOfIDsWith2Letters * countOfIDsWith3Letters

        println("TASK 1: checksum is $checksum")
    }

    override fun task2() {
        // IDs have 26 characters
        // the correct IDs differ only by one character, the common characters are the solution

        val processedIDs = mutableListOf<String>()

        for (indexOfFirst in 0..(inputLines.size - 2)) {
            for (indexOfSecond in (indexOfFirst + 1)..(inputLines.size - 1)) {
                processedIDs.add(getCommonCharacterSequence(inputLines[indexOfFirst], inputLines[indexOfSecond]))
            }
        }

        val commonCharSequence = processedIDs.find { string -> string.length == 25 }

        println("TASK 2: The common character sequence is $commonCharSequence")
    }

    private fun getCommonCharacterSequence(idOne: String, idTwo: String): String {
        val buffer = StringBuffer(25)
        for (i in idOne.indices) {
            if (idOne[i] == idTwo[i]) {
                buffer.append(idOne[i])
            }
        }
        return buffer.toString()
    }

}
