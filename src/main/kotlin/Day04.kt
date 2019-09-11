package de.dikodam.adventofkotlin

import de.dikodam.adventofkotlin.GuardState.ASLEEP
import de.dikodam.adventofkotlin.GuardState.AWAKE
import java.time.LocalDateTime
import java.time.Month

fun main() {
    Day04().runTasks()
}

enum class GuardState { ASLEEP, AWAKE }
data class GuardEvent(val guardID: Int, val time: LocalDateTime, val event: GuardState)
data class Accumulator(val currentGuardID: Int, val eventsByGuard: MutableMap<Int, List<GuardEvent>>)

class Day04 : AbstractDay() {
    // line :
    //      DATE EVENT
    // DATE:
    //      [year-month-day hour:minute]
    // EVENT:
    //      Guard ID begins shift
    //      falls asleep
    //      wakes up
    override fun task1() {
        val events = inputLines
            .map { splitToLocalDateTimeAndEvent(it) }
            .sortedBy { (dateTime, _) -> dateTime }

        // events.forEach { println(it) }

        val (_, eventsByGuard) = events.fold(initial = Accumulator(-1, HashMap()))
        { accumulator, next ->
            val (currentGuardID, eventsByGuard) = accumulator
            val (eventDatetime, eventString) = next

            if (eventString.isGuardID()) {
                Accumulator(extractGuardID(eventString), eventsByGuard)
            } else {
                val guardEvent = listOf(GuardEvent(currentGuardID, eventDatetime, eventString.toGuardState()))
                eventsByGuard.merge(currentGuardID, guardEvent)
                { list1, list2 ->
                    sequenceOf(list1, list2).flatten().toList()
                }
                Accumulator(currentGuardID, eventsByGuard)
            }
        }

        eventsByGuard.toSortedMap()
            .mapValues { (_, events) ->
                events.zipWithNext()
                    .map { it.minutesAsleepOnDay() }
            }
            .forEach { (k, v) -> v.forEach { println("guardID: $k, $it") } }


        val result = "implementing..."
        println("TASK 1: $result")
    }

    private fun Pair<GuardEvent, GuardEvent>.minutesAsleepOnDay(): Pair<Int, IntRange> {
        val (start, end) = this
        val minutes = start.time.minute until end.time.minute
        return start.time.dayOfMonth to minutes

    }

    private fun String.toGuardState() =
        when {
            this.contains("falls asleep") -> ASLEEP
            this.contains("wakes up") -> AWAKE
            else -> throw IllegalStateException()
        }

    private fun String.isGuardID(): Boolean =
        startsWith("guard", ignoreCase = true)

    private fun extractGuardID(eventString: String): Int =
        eventString.split(" ")[1].substring(startIndex = 1).toInt()

    // "[1518-07-03 23:58] EVENTSTRING"
    public fun splitToLocalDateTimeAndEvent(inputLine: String): Pair<LocalDateTime, String> {
        val year = inputLine.substring(1..4).toInt()
        val month = Month.of(inputLine.substring(6..7).toInt())
        val day = inputLine.substring(9..10).toInt()
        val hour = inputLine.substring(12..13).toInt()
        val minute = inputLine.substring(15..16).toInt()
        val dateTime = LocalDateTime.of(year, month, day, hour, minute)
        val eventString = inputLine.substring(startIndex = 19)
        return Pair(dateTime, eventString)
    }

    override fun task2() {
        val result = "not implemented yet"
        println("TASK 1: $result")
    }
}
