package com.github.mrko900.braintrainer

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.util.Consumer
import java.util.Random


class MathChainsExercise(
    exerciseControl: ExerciseControl,
    onFinishedCallback: Consumer<ExerciseResult>,
    group: ViewGroup,
    inflater: LayoutInflater,
    activity: MainActivity,
    private val config: MathChainsExerciseConfig
) : AbstractExercise(exerciseControl, onFinishedCallback, group, inflater, activity) {
    enum class Operation {
        ADD, SUBTRACT, MULTIPLY, DIVIDE
    }

    private lateinit var rootFrame: FrameLayout
    private lateinit var frame: ViewGroup

    private lateinit var chainsView: LinearLayout

    private lateinit var logic: MathChainsExerciseLogic

    private val random = Random()

    private lateinit var currentQuestion: Question

    override fun init() {
        rootFrame = group.findViewById(R.id.frame)
        frame = inflater.inflate(R.layout.math_chains_exercise_frame, rootFrame, true) as ViewGroup
        chainsView = frame.findViewById(R.id.chains)
        logic = MathChainsExerciseLogic(
            totalRounds = config.nRounds,
            initialNChains = config.nChains
        )
    }

    override fun pause() {
        TODO("Not yet implemented")
    }

    override fun resume() {
        TODO("Not yet implemented")
    }

    override fun start() {
        super.start()
        exerciseControl.totalRounds = logic.totalRounds
        exerciseControl.round = 0
        exerciseControl.score = 0
        initViews()
        nextQuestion()
        showKeyboard()
    }

    private fun showKeyboard() {
    }

    private fun initViews() {
        for (i in 1..logic.nChains) {
            val chainView = inflater.inflate(R.layout.math_chain, chainsView, false)
            val lp = chainView.layoutParams as LinearLayout.LayoutParams
            lp.weight = 1f
            chainsView.addView(chainView)
        }
    }

    private fun setOperation(chain: Int, op: Operation) {
        val res = when (op) {
            Operation.ADD, Operation.MULTIPLY -> R.drawable.ic_baseline_add_24
            Operation.SUBTRACT -> R.drawable.ic_baseline_remove_24
            Operation.DIVIDE -> R.drawable.ic_baseline_percent_24
        }
        val img = chainsView.getChildAt(chain).findViewById<ImageView>(R.id.operation)
        img.setImageResource(res)
        if (op == Operation.ADD || op == Operation.DIVIDE) {
            img.rotation = 45f
        } else {
            img.rotation = 0f
        }
    }

    private fun setValue(chain: Int, value: Int) {
        chainsView.getChildAt(chain).findViewById<TextView>(R.id.value).text = value.toString()
    }

    private data class Question(val chain: Int, val op: Operation, val operand: Int)

    private fun genQuestion(): Question {
        val chain = random.nextInt(logic.nChains)
        val op = Operation.values()[random.nextInt(Operation.values().size)]
        val num: Int
        if (op == Operation.DIVIDE) {
            val divisors = ArrayList<Int>()
            var i = 2
            while (i * i <= logic.chainVals[chain]) {
                if (logic.chainVals[chain] % i == 0) {
                    divisors.add(i)
                    if (i * i != logic.chainVals[chain]) {
                        divisors.add(logic.chainVals[chain] / i)
                    }
                    ++i
                }
            }
            num = divisors[random.nextInt(divisors.size)]
        } else {
            num = random.nextInt(20) // TODO difficulty
        }
        return Question(chain, op, num)
    }

    private fun nextQuestion() {
        if (exerciseControl.round == logic.totalRounds) {
            endExercise()
            return
        }
        currentQuestion = genQuestion()
        setOperation(currentQuestion.chain, currentQuestion.op)
        setValue(currentQuestion.chain, currentQuestion.operand)
    }

    private fun endExercise() {
        Log.d(LOGGING_TAG, "Exercise completed")
        finish(ExerciseResult(ExerciseMode.SHAPE_FUSION, exerciseControl.score, Any()))
    }
}
