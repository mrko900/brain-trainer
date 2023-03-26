package com.github.mrko900.braintrainer

import android.widget.TextView

class ExerciseControl(
    private val timerView: TextView,
    private val progressBar: CircularProgressBar,
    private val scoreView: TextView,
    private val roundView: TextView
) {
    var timer: Int = 0
        set(value) {
            field = value
            timerView.text = value.toString()
        }

    var progress: Float = 0f
        set(value) {
            field = value
            progressBar.percentage = value
            progressBar.invalidate()
        }

    var score: Int = 0
        set(value) {
            field = value
            scoreView.text = value.toString()
        }

    var totalRounds: Int = 0
        set(value) {
            field = value
            roundView.text = "$round/$value"
        }

    var round: Int = 0
        set(value) {
            field = value
            roundView.text = "$value/$totalRounds"
        }
}
