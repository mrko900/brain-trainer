package com.github.mrko900.braintrainer

import android.util.TypedValue
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.core.util.Consumer
import androidx.gridlayout.widget.GridLayout
import kotlin.math.roundToInt

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

    private var fieldSize = 6

    override fun init() {
        rootFrame = group.findViewById(R.id.frame)
        frame = inflater.inflate(R.layout.trails_exercise_frame, rootFrame, true) as ViewGroup
        fieldView = frame.findViewById(R.id.field)

    }

    override fun start() {
        super.start()
        exerciseControl.totalRounds = 6
        exerciseControl.round = 6
        exerciseControl.score = 0
        initField()
    }

    private fun initField() {
        fieldView.rowCount = fieldSize
        fieldView.columnCount = fieldSize
        for (i in 0 until fieldSize) { // i - row
            for (j in 0 until fieldSize) { // j - column
                fieldView.addView(createFieldSubView(), createFieldSubViewLayoutParams(i, j))
            }
        }
    }

    private fun createFieldSubView(): ImageView {
        val view = ImageView(activity)
        view.setImageResource(R.drawable.ic_baseline_psychology_alt_24)
        return view
    }

    private fun createFieldSubViewLayoutParams(row: Int, column: Int): GridLayout.LayoutParams {
        val lp = GridLayout.LayoutParams(
            GridLayout.spec(row, GridLayout.CENTER),
            GridLayout.spec(column, GridLayout.CENTER)
        )
        lp.width = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 64f, activity.resources.displayMetrics)
            .roundToInt()
        lp.height = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 64f, activity.resources.displayMetrics)
            .roundToInt()
        return lp
    }

    override fun pause() {
        TODO("Not yet implemented")
    }

    override fun resume() {
        TODO("Not yet implemented")
    }
}
