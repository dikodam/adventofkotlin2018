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

        // start at 0
        // apply changes again and again
        // record results
        // find first duplicate result

        var currentFrequency = 0


        println("TASK 2: not implemented yet")
    }

}