package com.github.mrko900.braintrainer

import android.widget.TextView

class ExerciseControl(private val timerView: TextView, private val progressBar: CircularProgressBar) {
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
}
