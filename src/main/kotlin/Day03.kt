package de.dikodam.adventofkotlin

fun main(args: Array<String>) {
    Day03().runTasks()
}

class Day03 : AbstractDay() {

    private val claims = inputLines.map { it.toDay03Claim() }

    private fun String.toDay03Claim(): Day03Claim {
        val (idpart, padsAndArea) = this.split(" @ ")
        val id = idpart.substring(1).toInt()
        val (pads, area) = padsAndArea.split(": ")
        val (padleft, padtop) = pads.split(",").map { it.toInt() }
        val (width, height) = area.split("x").map { it.toInt() }
        return Day03Claim(id, padleft, padtop, width, height)
    }

    private val allCoordinates = claims.flatMap { claim -> claim.toCoordinates() }

    private val overclaimedSquareInches = allCoordinates.groupingBy { it }
            .eachCount()
            .filter { mapentry -> mapentry.value > 1 }

    override fun task1() {
        val overclaimedSquareInches = overclaimedSquareInches.count()

        println("TASK 1: there are $overclaimedSquareInches square inches claimed more than once")
    }

    override fun task2() {
        // apparently there is only ONE claim with every square inch claimed only once. find it's ID.
        val claimContainsOnlyUniqueCoordinates: (Day03Claim) -> Boolean = { claim ->
            claim.toCoordinates().none { overclaimedSquareInches.containsKey(it) }
        }
        val claimID: String = claims.find(claimContainsOnlyUniqueCoordinates)?.id.toString()


        println("TASK 2: The unique claim has the ID $claimID")
    }

}

// #1311 @ 420,598: 20x26
class Day03Claim(val id: Int,
                 private val padleft: Int,
                 private val padtop: Int,
                 private val width: Int,
                 private val height: Int) {
    fun toCoordinates(): List<Pair<Int, Int>> {
        val widthCoords = padleft..(padleft + width - 1)
        val heightCoords = padtop..(padtop + height - 1)
        return widthCoords.flatMap { widthCoordinate ->
            heightCoords.map { heightCoordinate -> Pair(widthCoordinate, heightCoordinate) }
        }
    }

}
