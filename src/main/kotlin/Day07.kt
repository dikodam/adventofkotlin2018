package de.dikodam.adventofkotlin

fun main() {
    Day07().runTasks()
}

class Day07 : AbstractDay() {
    override fun task1() {
        var temporalDependencies = inputLines.map { it.parseTemporalDependency() }
        val allTasks = temporalDependencies
            .asSequence()
            .flatMap { sequenceOf(it.dependentTask, it.independentTask) }
            .toSet()

        var path = ""
        while (temporalDependencies.isNotEmpty()) {
            val (root, newList) = extractRoot(temporalDependencies)
            println("extracted root: $root")
            path += root
            temporalDependencies = newList
        }

        // hack for the last one
        path += allTasks.filter { !path.contains(it) }[0]

        // the source is the vortex that has no incoming edge.
        // if there are more possible sources, the source is the vortex which comes first alphabetically.
        // keep extracting sources until all vertices are extracted.

        // IDEA 1:
        // build directed graph of dependencies
        // on each step, extract source.

        // IDEA 2:
        // gather all dependency pairs.
        // a viable source in a vortex that isnt listed on the right hand side (receiving/incoming end)
        // identify all viable sources, pick the one that comes first alphabetically.
        // upon extraction, delete all dependencies with the extracted source on the left side.

        val result = path
        println("TASK 1: $result")
    }

    override fun task2() {
        val result = "not implemented yet"
        println("TASK 2: $result")
    }
}

// Step F must be finished before step E can begin.
private fun String.parseTemporalDependency(): Day07TemporalDependency {
    val independentTask = this[5]
    val dependentTask = this[36]
    return Day07TemporalDependency(independentTask, dependentTask)
}

private fun extractRoot(dependencies: List<Day07TemporalDependency>): Pair<Char, List<Day07TemporalDependency>> {
    val dependentTasks = dependencies.map { it.dependentTask }.toSet()
    val independentTasks = dependencies.map { it.independentTask }.toSet()
    val root = independentTasks.filter { !dependentTasks.contains(it) }.sorted()[0]
    val newDependencies = dependencies.filter { it.independentTask != root }
    return Pair(root, newDependencies)
}

private data class Day07TemporalDependency(
    val independentTask: Char,
    val dependentTask: Char
)
