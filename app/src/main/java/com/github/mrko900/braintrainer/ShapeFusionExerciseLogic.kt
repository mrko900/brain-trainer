package com.github.mrko900.braintrainer

class ShapeFusionExerciseLogic(
    initialSecondsPerQuestion: Int,
    initialNTerms: Int,
    initialNChoices: Int,
    shapeSide: Int,
    val hasAdditionOperation: Boolean,
    val hasSubtractionOperation: Boolean,
    val totalRounds: Int,
    val dynamic: Boolean
) {
    var nTerms = initialNTerms
        private set

    var nChoices = initialNChoices
        private set

    var score: Int = 0
        private set

    var secondsPerQuestion = initialSecondsPerQuestion
        private set

    var shapeSide = shapeSide
        private set

    fun correctChoice(secondsElapsed: Float) {
        score += secondsPerQuestion - secondsElapsed.toInt()
    }

    fun incorrectChoice(secondsElapsed: Float) {
        score -= secondsPerQuestion / 2
    }

    fun timedOut(secondsElapsed: Float) {
        score -= secondsPerQuestion / 4
    }
}
