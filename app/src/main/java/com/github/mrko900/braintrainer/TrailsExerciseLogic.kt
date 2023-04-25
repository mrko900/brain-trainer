package com.github.mrko900.braintrainer

class TrailsExerciseLogic(
    initialSecondsPerQuestion: Int,
    fieldSize: Int,
    instructionLength: Int,
    val totalRounds: Int,
    val dynamic: Boolean,
    val exerciseControl: ExerciseControl
) {
    var secondsPerQuestion = initialSecondsPerQuestion
        private set

    var fieldSize = fieldSize
        private set

    var score = 0
        private set

    var instructionLength = instructionLength
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
