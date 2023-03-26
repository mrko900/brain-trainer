package com.github.mrko900.braintrainer

import androidx.core.util.Consumer

data class ExerciseResult(val score: Int)

interface Exercise {
    fun init()
    fun start()
    fun pause()
    fun resume()
}

abstract class AbstractExercise(
    protected val exerciseControl: ExerciseControl,
    private val onFinishedCallback: Consumer<ExerciseResult>
) : Exercise {
    protected fun finish(result: ExerciseResult) {
        onFinishedCallback.accept(result)
    }
}
