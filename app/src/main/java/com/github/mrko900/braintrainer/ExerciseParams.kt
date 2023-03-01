package com.github.mrko900.braintrainer

enum class ExerciseType {
    MATH, SHAPE_FUSION
}

data class ExerciseParams(val type: ExerciseType)
