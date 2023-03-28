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

    private fun initAvgTimeRow(key: TextView, value: TextView, res: Resources, result: ExerciseResult) {
        val stats = result.stats as ShapeFusionExerciseStats
        var avgTime = 0f
        for (q in stats.questions) {
            avgTime += q.seconds
        }
        avgTime /= stats.questions.size
        key.text = res.getString(R.string.avg_time_per_question)
        value.text = res.getString(R.string.seconds_placeholder, String.format("%.2f", avgTime))
    }

    override fun out(activity: MainActivity, binding: ExerciseCompletedBinding, result: ExerciseResult) {
        val rows = listOf<(TextView, TextView, Resources, ExerciseResult) -> Unit>(
            { a0, a1, a2, a3 -> initScoreRow(a0, a1, a2, a3) },
            { a0, a1, a2, a3 -> initAvgTimeRow(a0, a1, a2, a3) }
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
