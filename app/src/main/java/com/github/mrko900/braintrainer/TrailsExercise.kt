package com.github.mrko900.braintrainer

import android.content.res.ColorStateList
import android.graphics.Color
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.util.Consumer
import androidx.gridlayout.widget.GridLayout

enum class Direction {
    UP, DOWN, RIGHT, LEFT
}

data class TrailsExerciseQuestion(
    val instruction: List<Direction>,
    val fromX: Int,
    val fromY: Int,
    val toX: Int,
    val toY: Int
)

class TrailsExercise(
    exerciseControl: ExerciseControl,
    onFinishedCallback: Consumer<ExerciseResult>,
    group: ViewGroup,
    inflater: LayoutInflater,
    activity: MainActivity
) : AbstractExercise(exerciseControl, onFinishedCallback, group, inflater, activity) {
    private lateinit var rootFrame: FrameLayout
    private lateinit var frame: ViewGroup

    private lateinit var fieldView: GridLayout
    private lateinit var instruction: LinearLayout

    private var fieldSize = 6

    // todo logic
    private var totalRounds = 6
    private var secondsPerQuestion = 6

    override fun init() {
        rootFrame = group.findViewById(R.id.frame)
        frame = inflater.inflate(R.layout.trails_exercise_frame, rootFrame, true) as ViewGroup
        fieldView = frame.findViewById(R.id.field)
        instruction = frame.findViewById(R.id.instruction)
    }

    override fun start() {
        super.start()
        exerciseControl.totalRounds = totalRounds
        exerciseControl.round = 0
        exerciseControl.score = 0
        initField()
        nextQuestion()
    }

    private fun initField() {
        fieldView.rowCount = fieldSize
        fieldView.columnCount = fieldSize
        for (i in 0 until fieldSize) { // i - row
            for (j in 0 until fieldSize) { // j - column
                fieldView.addView(createFieldSubView(true), createFieldSubViewLayoutParams(i, j, true))
                fieldView.addView(createFieldSubView(false), createFieldSubViewLayoutParams(i, j, false))
            }
        }
    }

    private fun createFieldSubView(outline: Boolean): ImageView {
        val view = ImageView(activity)
        view.setImageResource(R.drawable.ic_baseline_circle_24)
        view.imageTintList = ColorStateList.valueOf(if (outline) Color.BLACK else Color.RED)
        return view
    }

    private fun createFieldSubViewLayoutParams(row: Int, column: Int, outline: Boolean): GridLayout.LayoutParams {
        val lp = GridLayout.LayoutParams(
            GridLayout.spec(row, GridLayout.CENTER),
            GridLayout.spec(column, GridLayout.CENTER)
        )
        var size = activity.resources.displayMetrics.widthPixels / fieldSize - TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP, 8f, activity.resources.displayMetrics)
        if (!outline) {
            size *= 0.8f
        }
        lp.width = size.toInt()
        lp.height = size.toInt()
        return lp
    }

    private fun nextQuestion() {
        if (exerciseControl.round == totalRounds) {
            endExercise()
            return
        }
        exerciseControl.round++
        exerciseControl.timer = secondsPerQuestion
        exerciseControl.progress = 1f

    }

    private fun endExercise() {
        Log.d(LOGGING_TAG, "Exercise completed")
        finish(ExerciseResult(ExerciseMode.SHAPE_FUSION, exerciseControl.score, Any()))
    }

    override fun pause() {
        TODO("Not yet implemented")
    }

    override fun resume() {
        TODO("Not yet implemented")
    }
}
