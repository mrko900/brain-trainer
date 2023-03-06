package com.github.mrko900.braintrainer

import android.graphics.Color
import android.graphics.Paint
import android.graphics.PixelFormat
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.SurfaceHolder
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.github.mrko900.braintrainer.databinding.ExerciseBinding

class ExerciseFragment : Fragment() {
    private lateinit var binding: ExerciseBinding
    private lateinit var mainActivity: MainActivity

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (activity == null) {
            Log.e(LOGGING_TAG, "activity must be non-null")
            throw IllegalStateException("activity must be non-null")
        }
        mainActivity = activity as MainActivity
        binding = ExerciseBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.surface.setZOrderOnTop(true)
        binding.surface.holder.setFormat(PixelFormat.TRANSPARENT)
        binding.surface.holder.addCallback(ExerciseSurfaceHolderCallback())
    }
}

class ExerciseSurfaceHolderCallback : SurfaceHolder.Callback {
    override fun surfaceCreated(holder: SurfaceHolder) {
        val canvas = holder.lockCanvas()
        val paint = Paint()
        paint.color = Color.RED
        paint.style = Paint.Style.FILL
        canvas.drawCircle(200f, 200f, 50f, paint)
        holder.unlockCanvasAndPost(canvas)
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {
    }
}
