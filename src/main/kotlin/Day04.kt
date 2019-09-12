package de.dikodam.adventofkotlin

import de.dikodam.adventofkotlin.GuardState.ASLEEP
import de.dikodam.adventofkotlin.GuardState.AWAKE
import java.time.LocalDateTime
import java.time.Month

fun main() {
    Day04().runTasks()
}

private enum class GuardState { ASLEEP, AWAKE }
private data class GuardEvent(val guardID: Int, val time: LocalDateTime, val event: GuardState)
private data class Accumulator(val currentGuardID: Int, val eventsByGuard: MutableMap<Int, List<GuardEvent>>)
private typealias DayMinutes = Pair<Int, IntRange>

class Day04 : AbstractDay() {

    private val sleepingSchedule by lazy { parseInput() }

    private fun parseInput(): Map<Int, List<DayMinutes>> {
        val events = inputLines
            .map { splitToLocalDateTimeAndEvent(it) }
            .sortedBy { (dateTime, _) -> dateTime }

        val eventsByGuard = events
            .fold(initial = Accumulator(-1, HashMap()))
            { accumulator, nextEvent ->
                val (currentGuardID, eventsByGuard) = accumulator
                val (eventDatetime, eventString) = nextEvent
                if (eventString.isGuardID()) {
                    val guardID = extractGuardID(eventString)
                    Accumulator(guardID, eventsByGuard)
                } else {
                    val guardEvent = listOf(GuardEvent(currentGuardID, eventDatetime, eventString.toGuardState()))
                    eventsByGuard.merge(currentGuardID, guardEvent) { list1, list2 ->
                        sequenceOf(list1, list2).flatten().toList()
                    }
                    Accumulator(currentGuardID, eventsByGuard)
                }
            }
            .eventsByGuard

        return eventsByGuard
            .toSortedMap()
            .mapValues { (_, events) ->
                events.windowed(size = 2, step = 2) { it[0] to it[1] }
                    .map { it.minutesAsleepOnDay() }
            }
    }

    private fun Pair<GuardEvent, GuardEvent>.minutesAsleepOnDay(): DayMinutes {
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
    private fun splitToLocalDateTimeAndEvent(inputLine: String): Pair<LocalDateTime, String> {
        val year = inputLine.substring(1..4).toInt()
        val month = Month.of(inputLine.substring(6..7).toInt())
        val day = inputLine.substring(9..10).toInt()
        val hour = inputLine.substring(12..13).toInt()
        val minute = inputLine.substring(15..16).toInt()
        val dateTime = LocalDateTime.of(year, month, day, hour, minute)
        val eventString = inputLine.substring(startIndex = 19)
        return Pair(dateTime, eventString)
    }

    private fun countSleepingMinutes(dayMinutesAsleep: List<DayMinutes>): Map<Int, Int> {
        return dayMinutesAsleep
            .asSequence()
            .flatMap { (_, minutes) -> minutes.asSequence() }
            .groupingBy { it }
            .eachCount()
    }

    // line :
    //      DATE EVENT
    // DATE:
    //      [year-month-day hour:minute]
    // EVENT:
    //      Guard ID begins shift
    //      falls asleep
    //      wakes up
    override fun task1() {
        val guardSleepingMost = sleepingSchedule.maxBy { (_, dayMinutes) ->
            dayMinutes
                .map { (_, minutes) -> minutes.count() }
                .sum()
        }!! // there is one guard for sure, so we enforce non-nullness

        val (guardID: Int, dayMinutesAsleep: List<DayMinutes>) = guardSleepingMost
        val minuteCounts = countSleepingMinutes(dayMinutesAsleep)
        val (minute, _) = minuteCounts
            .maxBy { (_, count) -> count }!! // there is at least one minute, so we enforce non-nullness

        val result = guardID * minute
        println("TASK 1: $result")
    }

    override fun task2() {
        // Of all guards, which guard is most frequently asleep on the same minute?
        val (guardID, minuteCount) =
            sleepingSchedule
                .mapValues { (_, dayMinutes) ->
                    countSleepingMinutes(dayMinutes)
                }
                .mapValues { (_, minuteCounts) ->
                    minuteCounts.maxBy { entry -> entry.value }!!
                }
                .maxBy { (_, minuteCount) ->
                    val (_, count) = minuteCount
                    count
                }!!

        val (minute, _) = minuteCount
        val result = guardID * minute
        // What is the ID of the guard you chose multiplied by the minute you chose?
        println("TASK 2: $result")
    }
}
