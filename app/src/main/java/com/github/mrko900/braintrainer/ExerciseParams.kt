package com.github.mrko900.braintrainer

enum class ExerciseMode {
    MATH, SHAPE_FUSION
}

data class ExerciseParams(val mode: ExerciseMode)
