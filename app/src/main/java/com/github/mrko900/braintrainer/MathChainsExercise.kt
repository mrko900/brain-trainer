package com.github.mrko900.braintrainer

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.core.util.Consumer

class MathChainsExercise(
    exerciseControl: ExerciseControl,
    onFinishedCallback: Consumer<ExerciseResult>,
    group: ViewGroup,
    inflater: LayoutInflater,
    activity: MainActivity,
    private val config: MathChainsExerciseConfig
) : AbstractExercise(exerciseControl, onFinishedCallback, group, inflater, activity) {
    private lateinit var rootFrame: FrameLayout
    private lateinit var frame: ViewGroup

    private lateinit var chainsView: LinearLayout

    private lateinit var logic: MathChainsExerciseLogic

    override fun init() {
        rootFrame = group.findViewById(R.id.frame)
        frame = inflater.inflate(R.layout.math_chains_exercise_frame, rootFrame, true) as ViewGroup
        chainsView = frame.findViewById(R.id.chains)
        logic = MathChainsExerciseLogic(
            totalRounds = config.nRounds
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
    }
}
