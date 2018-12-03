package de.dikodam.adventofkotlin

fun main(args: Array<String>) {
    Day01().runTasks()
}

class Day01 : AbstractDay() {

    override fun task1() {
        val sumOfFrequencies = inputLines
                .map { Integer.parseInt(it) }
                .sum()
        println("TASK 1: The sum of frequencies is $sumOfFrequencies")
    }

    override fun task2() {
        val frequencyChanges = inputLines.map { Integer.parseInt(it) }

        var i = 0
        val nextFrequencyChange: () -> Int = {
            val change = frequencyChanges[i % frequencyChanges.size]
            i++
            change
        }

        val frequencyBuffer = HashSet<Int>()
        val isFrequencyUnique: (Int) -> Boolean = { frequency -> frequencyBuffer.add(frequency) }

        var currentFrequency = 0

        while (isFrequencyUnique(currentFrequency)) {
            currentFrequency += nextFrequencyChange()
        }

        println("TASK 2: first duplicate frequency is $currentFrequency")
    }

}