package com.github.mrko900.braintrainer

import android.animation.ValueAnimator
import android.os.Handler
import android.os.Looper
import android.view.animation.LinearInterpolator

class ExerciseTimer(
    private val timedOutCallback: () -> Unit,
    private val isActive: () -> Boolean,
    private val exerciseControl: ExerciseControl,
    secondsPerQuestion: Int
) {
    private val handler = Handler(Looper.getMainLooper())
    var secondsPerQuestion = secondsPerQuestion

    lateinit var progressAnim: ValueAnimator

    fun start() {
        var targetNextTimerUpd = System.currentTimeMillis() + 1000L
        val runnable = object : Runnable {
            override fun run() {
                if (!isActive())
                    return
                exerciseControl.timer -= 1
                if (exerciseControl.timer == 0) {
                    timedOutCallback()
                    return
                }
                targetNextTimerUpd += 1000L
                handler.postDelayed(this, targetNextTimerUpd - System.currentTimeMillis())
            }
        }
        handler.postDelayed(runnable, 1000L)

        progressAnim = ValueAnimator.ofFloat(1f, 0f)
        progressAnim.addUpdateListener { anim -> exerciseControl.progress = anim.animatedValue as Float }
        progressAnim.duration = secondsPerQuestion * 1000L
        progressAnim.interpolator = LinearInterpolator()
        progressAnim.start()
    }

    fun getProgress(): Float {
        return progressAnim.animatedValue as Float
    }

    fun endAnimation() {
        progressAnim.end()
    }
}
