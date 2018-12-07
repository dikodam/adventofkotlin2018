package de.dikodam.adventofkotlin

import java.time.LocalDateTime
import java.time.Month

fun main(args: Array<String>) {
    Day04().runTasks()
}

class Day04 : AbstractDay() {

    override fun task1() {
        // line :
        //      DATE EVENT
        // DATE:
        //      [year-month-day hour:minute]
        // EVENT:
        //      Guard ID begins shift
        //      falls asleep
        //      wakes up

        val events = inputLines
                .map { splitToLocalDateTimeAndEvent(it) }
                .sortedBy { dateTimeAndEvent -> dateTimeAndEvent.first }
        events.forEach { println(it) }

        val indicesOfGuardIds = events.withIndex()
                .filter { (_, dateTimeAndEvent) -> isGuardIdentifier(dateTimeAndEvent.second) }
                .map { it.index }

        // TODO build index-ranges = guard-shifts

        val result = "implementing..."
        println("TASK 1: $result")
    }


    private fun isGuardIdentifier(eventString: String): Boolean {
        return eventString.startsWith("guard", ignoreCase = true)
    }

    // "[1518-07-03 23:58] EVENTSTRING"
    public fun splitToLocalDateTimeAndEvent(inputLine: String): Pair<LocalDateTime, String> {
        val year = inputLine.substring(1..4).toInt()
        val month = Month.of(inputLine.substring(6, 7).toInt(radix = 10) + 1)
        val day = inputLine.substring(9, 10).toInt() + 1
        val hour = inputLine.substring(12, 13).toInt()
        val minute = inputLine.substring(15, 16).toInt()
        val dateTime = LocalDateTime.of(year, month, day, hour, minute)
        val eventString = inputLine.substring(startIndex = 19)
        return Pair(dateTime, eventString)
    }

    override fun task2() {
        val result = "not implemented yet"
        println("TASK 1: $result")
    }
}
