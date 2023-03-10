package com.github.mrko900.braintrainer

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Space
import androidx.core.util.Consumer
import java.util.*

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
        return matrix[y][x]
    }
}

class ShapeFusionExerciseQuestion(choices: List<Shape>, val answer: Int) {
    val choices: List<Shape> = choices
        get() {
            return Collections.unmodifiableList(field)
        }

    init {
        if (!(answer >= 0 && answer < choices.size))
            throw IllegalArgumentException("answer out of bounds")
    }
}

class ShapeFusionExercise(
    onFinishedCallback: Consumer<ExerciseResult>,
    private val group: ViewGroup,
    private val inflater: LayoutInflater
) : AbstractExercise(onFinishedCallback) {
    private lateinit var currentQuestion: ShapeFusionExerciseQuestion
    private lateinit var rootFrame: FrameLayout
    private lateinit var frame: ViewGroup
    private lateinit var choiceListView: LinearLayout
    private var newQuestion = false

    override fun init() {
        rootFrame = group.findViewById(R.id.frame)
        frame = inflater.inflate(R.layout.shape_fusion_exercise_frame, rootFrame, true) as ViewGroup
        choiceListView = frame.findViewById(R.id.choices)
    }

    override fun start() {
        nextQuestion()
        render()
    }

    override fun pause() {

    }

    override fun resume() {

    }

    private fun nextQuestion() {
        val choice1 = Shape(listOf(listOf(true, false), listOf(true, true)), 2, 2)
        val choice2 = Shape(
            listOf(listOf(true, false, false), listOf(false, true, false), listOf(true, true, false)), 3, 3
        )
        currentQuestion = ShapeFusionExerciseQuestion(listOf(choice1, choice2), 1)
        newQuestion = true
    }

    private fun getImage(shape: Shape): Bitmap {
        val w = frame.resources.getDimension(R.dimen.choice_card_image_size)
        val h = w
        val gap = frame.resources.getDimension(R.dimen.shape_fusion_gap)

        val bitmap = Bitmap.createBitmap(w.toInt(), h.toInt(), Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        val paint = Paint()
        paint.style = Paint.Style.FILL
        paint.color = Color.RED

        val xStep = (w - gap * (shape.getWidth() - 1)) / shape.getWidth()
        val yStep = (h - gap * (shape.getHeight() - 1)) / shape.getHeight()
        for (j in 1..shape.getWidth()) {
            for (i in shape.getHeight() downTo 1) {
                if (!shape.isSet(j - 1, i - 1))
                    continue
                val x = (j - 1) * (xStep + gap)
                val y = (i - 1) * (yStep + gap)
                canvas.drawRect(x, y, x + xStep, y + yStep, paint)
            }
        }

        return bitmap
    }

    private fun render() {
        if (newQuestion) {
            newQuestion = false
            var space = Space(choiceListView.context)
            choiceListView.addView(space)
            (space.layoutParams as LinearLayout.LayoutParams).weight = 1F
            for (choice in currentQuestion.choices) {
                val view = inflater.inflate(R.layout.choice_card, choiceListView, false)
                choiceListView.addView(view)
                choiceListView.addView(Space(choiceListView.context))
                view.findViewById<ImageView>(R.id.imageView2).setImageBitmap(getImage(choice))
                space = Space(choiceListView.context)
                choiceListView.addView(space)
                (space.layoutParams as LinearLayout.LayoutParams).weight = 1F
            }
        }
    }
}
