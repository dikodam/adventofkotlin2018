package de.dikodam.adventofkotlin

fun main(args: Array<String>) {
    Day02().runTasks()
}

class Day02 : AbstractDay() {
    override fun task1() {
        val containsLettersTimes: (Int) -> (String) -> Boolean =
                { nTimes ->
                    { string ->
                        string.chunked(1)
                                .groupingBy { it }
                                .eachCount()
                                .containsValue(nTimes)
                    }
                }

        val countOfIDsWith2Letters = inputLines.filter(containsLettersTimes(2)).count()
        val countOfIDsWith3Letters = inputLines.filter(containsLettersTimes(3)).count()

        val checksum = countOfIDsWith2Letters * countOfIDsWith3Letters

        println("TASK 1: checksum is $checksum")
    }

    override fun task2() {
        println("TASK 2: not implemented yet")
    }

}