package de.dikodam.adventofkotlin

fun main(args: Array<String>) {
    Day01().runTasks()
}

class Day01 : AbstractDay() {

    private val frequencyChanges: List<Int> = inputLines.map { Integer.parseInt(it) }

    override fun task1() {
        val sumOfFrequencies = frequencyChanges.sum()
        println("TASK 1: The sum of frequencies is $sumOfFrequencies")
    }

    override fun task2() {
        val getNextFrequencyChange: () -> Int = {
            var i = 0
            {
                val change = frequencyChanges[i % frequencyChanges.size]
                i++
                change
            }
        }()

        val frequencyBuffer = HashSet<Int>()
        fun Int.isUniqueSoFar(): Boolean = frequencyBuffer.add(this)

        var currentFrequency = 0
        while (currentFrequency.isUniqueSoFar()) {
            currentFrequency += getNextFrequencyChange()
        }

        println("TASK 2: first duplicate frequency is $currentFrequency")
    }

}