package com.github.mrko900.braintrainer

enum class ExerciseMode {
    MATH, SHAPE_FUSION
}

data class ExerciseParams(var mode: ExerciseMode, var config: Any?)
