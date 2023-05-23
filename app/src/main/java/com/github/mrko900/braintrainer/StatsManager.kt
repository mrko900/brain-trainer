package com.github.mrko900.braintrainer

import java.util.Date

class StatsManager {
    // null = all exercises
    fun getGeneralStats(exercise: ExerciseMode?): GeneralStats {
        return GeneralStats(
            3, 4, 5, 6, 7, 8.910f, 11f, 8f, 11, 12.13f, -14.15f, 16.17f, 181.9202122f
        )
    }

    fun getRatingHistory(exercise: ExerciseMode): History {
        return object : History {
            override fun valueAt(date: Date): Int? {
                return 25
            }
        }
    }
}

data class GeneralStats(
    val exercisesCompleted: Int,
    val totalQuestions: Int,
    val correctAnswers: Int,
    val wrongAnswers: Int,
    val timeouts: Int,
    val avgTimePerQuestion: Float,
    val avgTimeForCorrectAnswer: Float,
    val avgTimeForWrongAnswer: Float,
    val aggregateScore: Int,
    val avgScore: Float,
    val avgImprovementPerExercise: Float,
    val avgImprovementPerHour: Float,
    val totalTimeSpent: Float
)

interface History {
    fun valueAt(date: Date): Int?
}
