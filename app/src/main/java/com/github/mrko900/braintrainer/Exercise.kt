package com.github.mrko900.braintrainer

import android.view.SurfaceView
import androidx.core.util.Consumer

data class ExerciseResult(val score: Int)

interface Exercise {
    fun init(surface: SurfaceView);
    fun start();
    fun pause();
    fun resume();
}

abstract class AbstractExercise(private val onFinishedCallback: Consumer<ExerciseResult>) : Exercise {
    protected fun finish(result: ExerciseResult) {
        onFinishedCallback.accept(result)
    }
}
