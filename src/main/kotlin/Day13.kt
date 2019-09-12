package de.dikodam.adventofkotlin

import de.dikodam.adventofkotlin.Day13Direction.*
import de.dikodam.adventofkotlin.Day13TurnDirection.*
import de.dikodam.adventofkotlin.TrackPart.*

fun main(args: Array<String>) {
    Day13().runTasks()
}

typealias Day13Coordinates = Pair<Int, Int>

class Day13 : AbstractDay() {

    override fun task1() {
        val result = "not implemented yet"
        println("TASK 1: $result")
    }

    override fun task2() {
        val result = "not implemented yet"
        println("TASK 2: $result")
    }
}

class TrackMap(private val trackparts: List<List<TrackPart>>, val carts: Map<Day13Coordinates, Day13Cart>) {

    operator fun get(x: Int, y: Int): TrackPart = trackparts[x][y]

    fun tick() {}

}

fun parseDay13Direction(char: Char) {
    when (char) {
        '<' -> LEFT
        '^' -> UP
        '>' -> RIGHT
        'v' -> DOWN
        else -> throw IllegalArgumentException()
    }
}

enum class TrackPart { STRAIGHT_VERTICAL, STRAIGHT_HORIZONTAL, TURN_LEFT_TOP, TURN_RIGHT_TOP, INTERSECTION, WHITESPACE }

enum class Day13Direction {
    LEFT, UP, RIGHT, DOWN;

    fun turnLeft(): Day13Direction = when (this) {
        LEFT -> DOWN
        RIGHT -> UP
        UP -> LEFT
        DOWN -> RIGHT
    }

    fun turnRight(): Day13Direction = when (this) {
        LEFT -> UP
        RIGHT -> DOWN
        UP -> RIGHT
        DOWN -> LEFT
    }
}

enum class Day13TurnDirection { TURN_LEFT, GO_STRAIGHT, TURN_RIGHT }

class Day13Cart(
    private val currentPosition: Day13Coordinates,
    private val facingDirection: Day13Direction,
    private val nextTurnAtIntersection: Day13TurnDirection
) {

    fun nextPosition(): Day13Coordinates {
        val (x, y) = currentPosition
        return when (facingDirection) {
            LEFT -> Pair(x - 1, y)
            UP -> Pair(x, y - 1)
            RIGHT -> Pair(x + 1, y)
            DOWN -> Pair(x, y + 1)
        }
    }

    fun move(nextTrackPart: TrackPart): Day13Cart {
        return when (nextTrackPart) {
            STRAIGHT_HORIZONTAL, STRAIGHT_VERTICAL -> Day13Cart(nextPosition(), facingDirection, nextTurnAtIntersection)
            TURN_LEFT_TOP -> Day13Cart(nextPosition(), turnLeftTop(), nextTurnAtIntersection)
            TURN_RIGHT_TOP -> Day13Cart(nextPosition(), turnRightTop(), nextTurnAtIntersection)
            TrackPart.INTERSECTION -> Day13Cart(nextPosition(), turnAtIntersection(), determineNextTurnAtIntersection())
            else -> throw IllegalArgumentException("next track part is whitespace " + this)
        }
    }

    // turn looks like this: /
    private fun turnLeftTop(): Day13Direction =
        when (facingDirection) {
            RIGHT -> UP
            DOWN -> LEFT
            else -> throw IllegalArgumentException("facing turn from wrong direction")
        }

    // turn looks like this: \
    private fun turnRightTop(): Day13Direction =
        when (facingDirection) {
            LEFT -> UP
            DOWN -> RIGHT
            else -> throw IllegalArgumentException("facing turn from wrong direction")
        }

    private fun turnAtIntersection(): Day13Direction =
        when (nextTurnAtIntersection) {
            TURN_LEFT -> facingDirection.turnLeft()
            GO_STRAIGHT -> facingDirection
            TURN_RIGHT -> facingDirection.turnRight()
        }

    // cart turns left the first time, goes straight the second time, turns right the third time
    private fun determineNextTurnAtIntersection(): Day13TurnDirection =
        when (nextTurnAtIntersection) {
            TURN_LEFT -> GO_STRAIGHT
            GO_STRAIGHT -> TURN_RIGHT
            TURN_RIGHT -> TURN_LEFT
        }
}
