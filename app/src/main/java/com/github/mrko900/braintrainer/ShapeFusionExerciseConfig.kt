package com.github.mrko900.braintrainer

data class ShapeFusionExerciseConfig(
    val nTermsInitial: Int,
    val nChoicesInitial: Int,
    val dynamic: Boolean,
    val additionOperation: Boolean,
    val subtractionOperation: Boolean,
    val shapeSide: Int,
    val nRounds: Int
)
