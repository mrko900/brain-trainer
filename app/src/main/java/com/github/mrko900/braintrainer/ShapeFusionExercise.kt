package com.github.mrko900.braintrainer

import android.graphics.Color
import android.graphics.Paint
import android.view.SurfaceHolder
import android.view.SurfaceView
import androidx.core.util.Consumer

class ShapeFusionExercise(onFinishedCallback: Consumer<ExerciseResult>) : AbstractExercise(onFinishedCallback) {
    override fun init(surface: SurfaceView) {
        surface.holder.addCallback(object : SurfaceHolder.Callback {
            override fun surfaceCreated(holder: SurfaceHolder) {
                val canvas = holder.lockCanvas()
                val paint = Paint()
                paint.color = Color.GREEN
                paint.style = Paint.Style.FILL
                canvas.drawCircle(300f, 200f, 170f, paint)
                holder.unlockCanvasAndPost(canvas)
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
