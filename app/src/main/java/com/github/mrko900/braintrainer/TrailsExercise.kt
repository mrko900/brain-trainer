package com.github.mrko900.braintrainer

import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.Color.parseColor
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.annotation.ColorInt
import androidx.core.graphics.blue
import androidx.core.graphics.green
import androidx.core.graphics.red
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
    private lateinit var instructionView: LinearLayout

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

    private var state = State.TRANSITION

    enum class State {
        QUESTION_ACTIVE, TRANSITION
    }

    override fun init() {
        rootFrame = group.findViewById(R.id.frame)
        frame = inflater.inflate(R.layout.trails_exercise_frame, rootFrame, true) as ViewGroup
        fieldView = frame.findViewById(R.id.field)
        instructionView = frame.findViewById(R.id.instruction)
    }

    override fun start() {
        super.start()
        exerciseControl.totalRounds = totalRounds
        exerciseControl.round = 0
        exerciseControl.score = 0
        initField()
        nextQuestion()
    }

    // todo postpone enter transition
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
                outerView.setOnClickListener {
                    handleChoice(j, i)
                }
            }
        }
    }

    private fun createFieldSubView(outline: Boolean): ImageView {
        val view = ImageView(activity)
        view.setImageResource(R.drawable.ic_baseline_circle_24)
        view.imageTintList = ColorStateList.valueOf(if (outline) parseColor("#a3a3a3") else parseColor("#e3e3e3"))
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
            x = updX(x, current)
            y = updY(y, current)
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
        outerViews[currentQuestion.fromY][currentQuestion.fromX].imageTintList = ColorStateList.valueOf(Color.DKGRAY)

        // show instruction
        for (dir in currentQuestion.instruction) {
            instructionView.addView(createDirectionView(dir))
        }

        questionLoaded()
    }

    private fun questionLoaded() {
        state = State.QUESTION_ACTIVE
    }

    private fun questionUnloaded() {
        nextQuestion()
    }

    private fun createDirectionView(dir: Direction): View {
        val view = ImageView(activity)
        view.setImageResource(R.drawable.ic_baseline_arrow_forward_24)
        view.rotation = when (dir) {
            Direction.RIGHT -> 0f
            Direction.DOWN -> 90f
            Direction.LEFT -> 180f
            Direction.UP -> 270f
        }

        val size = ((0.8f * activity.resources.displayMetrics.widthPixels) / currentQuestion.instruction.size
                - TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8f, activity.resources.displayMetrics)).toInt()
        view.layoutParams = LinearLayout.LayoutParams(size, size)

        return view
    }

    private fun handleChoice(x: Int, y: Int) {
        if (x == currentQuestion.toX && y == currentQuestion.toY) {
            handleCorrectChoice()
        } else {
            handleIncorrectChoice()
        }
    }

    private fun rgb(red: Int, green: Int, blue: Int): Int {
        return -0x1000000 or (red shl 16) or (green shl 8) or blue
    }

    private fun averageColor(@ColorInt a: Int, @ColorInt b: Int, k: Float): Int {
        val r1 = a.red
        val g1 = a.green
        val b1 = a.blue
        val r2 = b.red
        val g2 = b.green
        val b2 = b.blue
        return rgb(r1 + (k * (r2 - r1)).toInt(), g1 + (k * (g2 - g1)).toInt(), b1 + (k * (b2 - b1)).toInt())
    }

    private fun getDegreeColor(degree: Int): Int = when (degree) {
        5 -> Color.GREEN
        4 -> rgb(107, 255, 107)
        3 -> rgb(140, 255, 140)
        2 -> rgb(177, 255, 177)
        1 -> rgb(205, 255, 205)
        0 -> parseColor("#e3e3e3")
        else -> throw IllegalArgumentException()
    }

    private val colorPaintDegreeMap: MutableMap<Pair<Int, Int>, Int> = HashMap()

    private fun colorPoint(degree: Int, x: Int, y: Int, handler: Handler) {
        if (colorPaintDegreeMap.contains(Pair(x, y)) && colorPaintDegreeMap[Pair(x, y)]!! > degree) {
            return
        }
        innerViews[y][x].imageTintList = ColorStateList.valueOf(getDegreeColor(degree))
//        innerViews[y][x].imageTintList = ColorStateList.valueOf(averageColor(Color.WHITE, Color.GREEN, degree / 3.0f))
        if (degree != 0) {
            colorPaintDegreeMap[Pair(x, y)] = degree - 1
            handler.postDelayed(
                { colorPoint(degree - 1, x, y, handler) },
                activity.resources.getInteger(R.integer.trails_exercise_movement_anim_delay).toLong()
            )
        } else {
            colorPaintDegreeMap.remove(Pair(x, y))
        }
    }

    private fun handleCorrectChoice() {
        Log.d(LOGGING_TAG, "Correct choice")
        var x = currentQuestion.fromX
        var y = currentQuestion.fromY
        val handler = Handler(Looper.getMainLooper())
        val iterator = currentQuestion.instruction.iterator()
        colorPoint(4, x, y, handler)
        val runnable = object : Runnable {
            override fun run() {
                val dir = iterator.next()
                outerViews[y][x].imageTintList = ColorStateList.valueOf(parseColor("#a3a3a3"))
                x = updX(x, dir)
                y = updY(y, dir)
                colorPoint(5, x, y, handler)
                outerViews[y][x].imageTintList = ColorStateList.valueOf(Color.DKGRAY)
                if (iterator.hasNext()) {
                    handler.postDelayed(
                        this, activity.resources.getInteger(R.integer.trails_exercise_movement_anim_delay).toLong()
                    )
                }
            }
        }
        runnable.run()
        endQuestion()
    }

    private fun updX(x: Int, dir: Direction): Int = when (dir) {
        Direction.RIGHT -> x + 1
        Direction.LEFT -> x - 1
        else -> x
    }

    private fun updY(y: Int, dir: Direction): Int = when (dir) {
        Direction.UP -> y + 1
        Direction.DOWN -> y - 1
        else -> y
    }

    private fun handleIncorrectChoice() {
        Log.d(LOGGING_TAG, "Incorrect choice")
        endQuestion()
    }

    private fun timedOut() {
        endQuestion()
    }

    private fun endQuestion() {
        state = State.TRANSITION
//        questionUnloaded()
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
