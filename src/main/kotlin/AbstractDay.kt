package de.dikodam.adventofkotlin

import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import kotlin.streams.toList

abstract class AbstractDay {
    abstract fun task1()
    abstract fun task2()

    protected val inputLines = readInputLines()

    fun readInputLines(): List<String> {
        val dayName = javaClass.simpleName
        val inputFile = File(javaClass.getResource("/$dayName").toURI())
        return BufferedReader(FileReader(inputFile))
                .run { lines().toList() }
    }
}

fun AbstractDay.runTasks() {
    this.task1()
    this.task2()
}