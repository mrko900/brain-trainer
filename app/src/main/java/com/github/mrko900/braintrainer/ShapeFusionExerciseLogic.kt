package com.github.mrko900.braintrainer

class ShapeFusionExerciseLogic(initialSecondsPerQuestion: Int) {
    var score: Int = 0
        private set

    var secondsPerQuestion = initialSecondsPerQuestion
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
