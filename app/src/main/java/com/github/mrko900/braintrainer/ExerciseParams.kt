package com.github.mrko900.braintrainer

enum class ExerciseMode {
    SHAPE_FUSION, TRAILS
}

data class ExerciseParams(var mode: ExerciseMode, var config: Any?)
