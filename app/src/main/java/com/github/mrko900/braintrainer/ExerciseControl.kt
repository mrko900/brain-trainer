package com.github.mrko900.braintrainer

import android.widget.TextView

class ExerciseControl(private val timerView: TextView) {
    var timer: Int = 0
        get() = field
        set(value) {
            field = value
            timerView.text = value.toString()
        }
}
