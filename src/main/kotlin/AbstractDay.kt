package de.dikodam.adventofkotlin

import java.io.File

abstract class AbstractDay {
    abstract fun task1()
    abstract fun task2()

    protected val inputLines by lazy { readInputLines() }

    private fun readInputLines(): List<String> {
        val dayName = javaClass.simpleName
        val inputFile = File(javaClass.getResource("/$dayName").toURI())
        return inputFile.useLines { it.toList() }
    }

    fun runTasks() {
        this.task1()
        this.task2()
    }
}
