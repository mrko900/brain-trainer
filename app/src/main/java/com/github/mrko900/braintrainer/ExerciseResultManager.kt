package com.github.mrko900.braintrainer

import android.content.res.Resources
import android.widget.LinearLayout
import android.widget.TextView
import com.github.mrko900.braintrainer.databinding.ExerciseCompletedBinding

interface ExerciseResultManager {
    fun out(activity: MainActivity, binding: ExerciseCompletedBinding, result: ExerciseResult)
}

class ShapeFusionExerciseResultManager : ExerciseResultManager {
    private fun initScoreRow(key: TextView, value: TextView, res: Resources, result: ExerciseResult) {
        key.text = res.getString(R.string.score)
        value.text = result.score.toString()
    }

    override fun out(activity: MainActivity, binding: ExerciseCompletedBinding, result: ExerciseResult) {
        val rows = listOf<(TextView, TextView, Resources, ExerciseResult) -> Unit>(
            { a0, a1, a2, a3 -> initScoreRow(a0, a1, a2, a3) }
        )
        for (f in rows) {
            val row: LinearLayout = activity.layoutInflater.inflate(
                R.layout.exercise_result_table_row,
                binding.statsTable,
                false
            ) as LinearLayout
            binding.statsTable.addView(row)
            f(row.findViewById(R.id.key), row.findViewById(R.id.value), activity.resources, result)
        }
    }
}
