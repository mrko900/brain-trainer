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
import java.util.Random

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
    private var instructionLength = 6

    private val random = Random()

    private lateinit var currentQuestion: TrailsExerciseQuestion
    private var newQuestion = false

    private val innerViews = ArrayList<ArrayList<ImageView>>()
    private val outerViews = ArrayList<ArrayList<ImageView>>()

    private var lastToX = random.nextInt(fieldSize)
    private var lastToY = random.nextInt(fieldSize)

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
            outerViews.add(ArrayList())
            innerViews.add(ArrayList())
            for (j in 0 until fieldSize) { // j - column
                val outerView = createFieldSubView(true)
                val innerView = createFieldSubView(false)
                fieldView.addView(outerView, createFieldSubViewLayoutParams(i, j, true))
                fieldView.addView(innerView, createFieldSubViewLayoutParams(i, j, false))
                outerViews[i].add(outerView)
                innerViews[i].add(innerView)
            }
        }
    }

    private fun createFieldSubView(outline: Boolean): ImageView {
        val view = ImageView(activity)
        view.setImageResource(R.drawable.ic_baseline_circle_24)
        view.imageTintList = ColorStateList.valueOf(if (outline) Color.BLACK else Color.LTGRAY)
        // todo add elevation
        return view
    }

    private fun createFieldSubViewLayoutParams(row: Int, column: Int, outline: Boolean): GridLayout.LayoutParams {
        val lp = GridLayout.LayoutParams(
            GridLayout.spec(fieldSize - row - 1, GridLayout.CENTER),
            GridLayout.spec(column, GridLayout.CENTER)
        )
        var size = activity.resources.displayMetrics.widthPixels / fieldSize - TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP, 8f, activity.resources.displayMetrics
        )
        if (!outline) {
            size *= 0.85f
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
        setupNextQuestion()
    }

    private fun setupNextQuestion() {
        val fromX = lastToX
        val fromY = lastToY
        var x = fromX
        var y = fromY
        val instruction = ArrayList<Direction>()
        for (i in 1..instructionLength) {
            val set = HashSet<Direction>()
            for (dir in Direction.values()) {
                set.add(dir)
            }
            if (x == 0)
                set.remove(Direction.LEFT)
            if (x == fieldSize - 1)
                set.remove(Direction.RIGHT)
            if (y == 0)
                set.remove(Direction.DOWN)
            if (y == fieldSize - 1)
                set.remove(Direction.UP)
            val current = set.random()
            when (current) {
                Direction.RIGHT -> x += 1
                Direction.LEFT -> y -= 1
                Direction.UP -> x += 1
                Direction.DOWN -> y -= 1
            }
            instruction.add(current)
        }
        lastToX = x
        lastToY = y
        currentQuestion = TrailsExerciseQuestion(instruction, fromX, fromY, x, y)
        newQuestion = true
        render()
    }

    private fun render() {
        if (newQuestion) newQuestion = false
        else return

        // update field
        innerViews[currentQuestion.fromY][currentQuestion.fromX].imageTintList = ColorStateList.valueOf(Color.GREEN)

        
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
