package com.github.mrko900.braintrainer

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.FrameLayout
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

class ShapeFusionExercise(
    onFinishedCallback: Consumer<ExerciseResult>,
    private val group: ViewGroup,
    private val inflater: LayoutInflater
) : AbstractExercise(onFinishedCallback) {
    private lateinit var currentQuestion: ShapeFusionExerciseQuestion
    private lateinit var frame: FrameLayout

    override fun init() {
        frame = group.findViewById(R.id.frame)
        inflater.inflate(R.layout.shape_fusion_exercise_frame, frame, true)
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
    }

    private fun render() {

    }
}
