package com.github.mrko900.braintrainer

import android.view.SurfaceHolder
import android.view.SurfaceView
import androidx.core.util.Consumer

data class Shape(private val matrix: List<List<Boolean>>, private val width: Int, private val height: Int) {
    fun getHeight(): Int {
        return height
    }

    fun getWidth(): Int {
        return width
    }

    fun isSet(x: Int, y: Int): Boolean {
        if (x < 0 || x >= width || y < 0 || y >= height)
            throw IllegalArgumentException("coords not in range")
        return matrix[x][y]
    }
}

data class ShapeFusionExerciseQuestion(private val choices: List<Shape>, val answer: Int) {
    init {
        if (!(answer >= 0 && answer < choices.size))
            throw IllegalArgumentException("answer out of bounds")
    }
}

class ShapeFusionExercise(onFinishedCallback: Consumer<ExerciseResult>) : AbstractExercise(onFinishedCallback) {
    override fun init(surface: SurfaceView) {
        surface.holder.addCallback(object : SurfaceHolder.Callback {
            override fun surfaceCreated(holder: SurfaceHolder) {
//                val canvas = holder.lockCanvas()
//                val paint = Paint()
//                paint.color = Color.GREEN
//                paint.style = Paint.Style.FILL
//                canvas.drawCircle(300f, 200f, 170f, paint)
//                holder.unlockCanvasAndPost(canvas)
            }

            override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
            }

            override fun surfaceDestroyed(holder: SurfaceHolder) {
            }
        })
    }

    override fun start() {

    }

    override fun pause() {

    }

    override fun resume() {

    }
}
