package com.github.mrko900.braintrainer

import android.content.res.ColorStateList
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.TypedValue
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.*
import androidx.core.util.Consumer
import java.util.*
import java.util.concurrent.ThreadLocalRandom

data class Shape(private val matrix: List<MutableList<Boolean>>, private val width: Int, private val height: Int) {
    companion object {
        private fun emptyMatrix(w: Int, h: Int): List<MutableList<Boolean>> {
            val mat = ArrayList<MutableList<Boolean>>(h)
            for (i in 1..h) {
                val row = ArrayList<Boolean>(h)
                for (j in 1..w)
                    row.add(false)
                mat.add(row)
            }
            return mat
        }
    }

    constructor(width: Int, height: Int) : this(emptyMatrix(width, height), width, height)

    constructor(other: Shape) : this(other.matrix, other.width, other.height)

    fun getHeight(): Int {
        return height
    }

    fun getWidth(): Int {
        return width
    }

    fun get(x: Int, y: Int): Boolean {
        if (x < 0 || x >= width || y < 0 || y >= height)
            throw IllegalArgumentException("coords not in range: whxy $width,$height,$x,$y")
        return matrix[y][x]
    }

    fun set(x: Int, y: Int, value: Boolean) {
        if (x < 0 || x >= width || y < 0 || y >= height)
            throw IllegalArgumentException("coords not in range")
        matrix[y][x] = value
    }
}

class ShapeFusionExerciseQuestion(val expression: Expression, choices: List<Shape>, val answer: Int) {
    data class Expression(val operands: List<Shape>, val operators: List<Operator>)

    enum class Operator {
        ADDITION, SUBTRACTION
    }

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
    private lateinit var exprFrameView: TableLayout
    private var newQuestion = false

    private val shapeSide = 3
    private val nChoices = 4
    private val nOperands = 2

    override fun init() {
        rootFrame = group.findViewById(R.id.frame)
        frame = inflater.inflate(R.layout.shape_fusion_exercise_frame, rootFrame, true) as ViewGroup
        choiceListView = frame.findViewById(R.id.choices)
        exprFrameView = frame.findViewById(R.id.exprFrame)
    }

    override fun start() {
        nextQuestion()
        render()
    }

    override fun pause() {
    }

    override fun resume() {
    }

    private fun randomShape(): Shape {
        val shapeData = ArrayList<ArrayList<Boolean>>()
        for (y in 1..shapeSide) {
            val row = ArrayList<Boolean>()
            shapeData.add(row)
            for (x in 1..shapeSide) {
                row.add(ThreadLocalRandom.current().nextInt(2) == 1)
            }
        }
        return Shape(shapeData, shapeSide, shapeSide)
    }

    private fun randomOperator(): ShapeFusionExerciseQuestion.Operator {
        return if (ThreadLocalRandom.current().nextInt(2) == 1)
            ShapeFusionExerciseQuestion.Operator.ADDITION else ShapeFusionExerciseQuestion.Operator.SUBTRACTION
    }

    private data class Choices(val arr: ArrayList<Shape>, val ans: Int)

    private fun genChoices(expr: FullExpr): Choices {
        val choices = ArrayList<Shape>()
        val ansPos = ThreadLocalRandom.current().nextInt(nChoices)
        for (i in 0 until nChoices) {
            if (i == ansPos)
                choices.add(expr.answer)
            else
                choices.add(randomShape())
        }
        return Choices(choices, ansPos)
    }

    private data class FullExpr(val expression: ShapeFusionExerciseQuestion.Expression, val answer: Shape)

    private fun performOperation(operand: Shape, argument: Shape, operator: ShapeFusionExerciseQuestion.Operator) {
        val v = operator == ShapeFusionExerciseQuestion.Operator.ADDITION
        for (x in 0 until argument.getWidth()) {
            for (y in 0 until argument.getHeight()) {
                if (argument.get(x, y))
                    operand.set(x, y, v)
            }
        }
    }

    private fun genExpression(): FullExpr {
        val operands = ArrayList<Shape>()
        val operators = ArrayList<ShapeFusionExerciseQuestion.Operator>()
        for (i in 1..nOperands) {
            operands.add(randomShape())
            operators.add(randomOperator())
        }
        val answ = Shape(operands[0])
        for (i in 2..nOperands) {
            performOperation(answ, operands[i - 1], operators[i - 2])
        }
        return FullExpr(ShapeFusionExerciseQuestion.Expression(operands, operators), answ)
    }

    private fun nextQuestion() {
        val expr = genExpression()
        val choices = genChoices(expr)
        currentQuestion = ShapeFusionExerciseQuestion(expr.expression, choices.arr, choices.ans)
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
                if (!shape.get(j - 1, i - 1))
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
            renderChoices()
            renderExpression()
        }
    }

    private fun renderChoices() {
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

    private fun renderExpression() {
        for (i in 0 until currentQuestion.expression.operands.size) {
            val row = inflater.inflate(R.layout.expr_row, exprFrameView, false)
            val center: FrameLayout = row.findViewById(R.id.center)
            val start: FrameLayout = row.findViewById(R.id.start)

            // operand
            val operandView = inflater.inflate(R.layout.choice_card, center, false)
            val operandImg: ImageView = operandView.findViewById(R.id.imageView2)
            operandImg.layoutParams.width = operandView.resources.getDimension(R.dimen.expr_card_image_size).toInt()
            operandImg.layoutParams.height = operandView.resources.getDimension(R.dimen.expr_card_image_size).toInt()
            operandImg.setImageBitmap(getImage(currentQuestion.expression.operands[i]))
            center.addView(operandView)
            val operandViewLayoutParams = operandView.layoutParams as FrameLayout.LayoutParams
            operandViewLayoutParams.gravity = Gravity.CENTER
            operandViewLayoutParams.leftMargin = 0
            operandViewLayoutParams.rightMargin = 0
            operandViewLayoutParams.topMargin = 0
            operandViewLayoutParams.bottomMargin = 0

            // operator
            if (i != 0) {
                val operatorView = inflater.inflate(R.layout.expr_operator, start, false)
                start.addView(operatorView)
                val img: ImageView = operatorView.findViewById(R.id.imageView3)
                img.setImageResource(
                    if (currentQuestion.expression.operators[i - 1] == ShapeFusionExerciseQuestion.Operator.ADDITION)
                        R.drawable.ic_baseline_add_circle_24
                    else
                        R.drawable.ic_baseline_remove_circle_24
                )
                val color = TypedValue()
                img.context.theme.resolveAttribute(
                    if (currentQuestion.expression.operators[i - 1] == ShapeFusionExerciseQuestion.Operator.ADDITION)
                        R.attr.colorAdd
                    else
                        R.attr.colorSubtract,
                    color, true
                )
                img.imageTintList = ColorStateList.valueOf(color.data)
                val operatorViewLayoutParams = operatorView.layoutParams as FrameLayout.LayoutParams
                operatorViewLayoutParams.gravity = Gravity.CENTER_VERTICAL or Gravity.END
            }

            exprFrameView.addView(row)
        }
    }
}
