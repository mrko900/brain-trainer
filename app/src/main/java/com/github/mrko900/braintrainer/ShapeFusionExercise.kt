package com.github.mrko900.braintrainer

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
        return matrix[x][y]
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
        val choice2 = Shape(listOf(listOf(true, false), listOf(true, false)), 2, 2)
        currentQuestion = ShapeFusionExerciseQuestion(listOf(choice1, choice2), 1)
        newQuestion = true
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
                
                space = Space(choiceListView.context)
                choiceListView.addView(space)
                (space.layoutParams as LinearLayout.LayoutParams).weight = 1F
            }
        }
    }
}
