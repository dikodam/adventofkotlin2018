package de.dikodam.adventofkotlin

fun main(args: Array<String>) {
    Day05().runTasks()
}

internal fun reactPolymerWithNextChar(polymer: String, nextChar: Char): String {
    val eliminatedString = { polymer.substring(0..(polymer.length - 2)) }
    return when {
        polymer.isEmpty() -> nextChar.toString()
        polymer.last().isSameCharDifferentCase(nextChar) -> eliminatedString()
        else -> polymer + nextChar
    }
}

internal fun Char.isSameCharDifferentCase(otherChar: Char): Boolean {
    return this.equals(otherChar, ignoreCase = true) &&
        !this.equals(otherChar, ignoreCase = false)
}

internal fun String.reactPolymer() = this.asSequence()
    .fold("")
    { leftString, nextChar -> reactPolymerWithNextChar(leftString, nextChar) }


class Day05 : AbstractDay() {

    private val input = inputLines[0]

    internal fun String.filterOutBothCases(badChar: Char): String {
        return this.filter { stringChar -> stringChar != badChar.toLowerCase() && stringChar != badChar.toUpperCase() }
    }

    override fun task1() {
        val reactedPolymerLength = input.reactPolymer().count()
        println("TASK 1: $reactedPolymerLength")
    }

    override fun task2() {
        val chars = 'a'..'z'
        val filteredPolymers = chars.map { char -> input.filterOutBothCases(char) }
        val shortestFilteredPolymer = filteredPolymers.map { it.reactPolymer().count() }.min()

        println("TASK 2: $shortestFilteredPolymer")
    }
}
