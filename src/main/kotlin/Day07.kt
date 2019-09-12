package de.dikodam.adventofkotlin

fun main() {
    Day07().runTasks()
}

class Day07 : AbstractDay() {
    override fun task1() {
        val temporalDependencies = inputLines.map { it.parseTemporalDependency() }


        val result = "implementing"
        println("TASK 1: $result")
    }

    override fun task2() {
        val result = "not implemented yet"
        println("TASK 2: $result")
    }
}

// Step F must be finished before step E can begin.
internal fun String.parseTemporalDependency(): Day07TemporalDependency {
    val independentTask = this[5]
    val dependentTask = this[36]
    return Day07TemporalDependency(independentTask, dependentTask)
}

data class Day07TemporalDependency(
    val independentTask: Char,
    val dependentTask: Char
)
