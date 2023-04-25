package com.github.mrko900.braintrainer

class ShapeFusionExerciseLogic(
    initialSecondsPerQuestion: Int,
    initialNTerms: Int,
    initialNChoices: Int,
    shapeSide: Int,
    val hasAdditionOperation: Boolean,
    val hasSubtractionOperation: Boolean,
    val totalRounds: Int,
    val dynamic: Boolean,
    val exerciseControl: ExerciseControl
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
        updateScore()
    }

    fun incorrectChoice(secondsElapsed: Float) {
        score -= secondsPerQuestion / 2
        score = score.coerceAtLeast(0)
        updateScore()
    }

    fun timedOut(secondsElapsed: Float) {
        score -= secondsPerQuestion / 4
        score = score.coerceAtLeast(0)
        updateScore()
    }

    private fun updateScore() {
        exerciseControl.score = score
    }
}
