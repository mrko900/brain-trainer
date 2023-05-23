package com.github.mrko900.braintrainer

import java.util.Calendar
import java.util.Date
import java.util.GregorianCalendar
import java.util.Random

class StatsManager {
    // null = all exercises
    fun getGeneralStats(exercise: ExerciseMode?): GeneralStats {
        return GeneralStats(
            3, 4, 5, 6, 7, 8.910f, 11f, 8f, 11, 12.13f, -14.15f, 16.17f, 181.9202122f
        )
    }

    fun getRatingHistory(exercise: ExerciseMode): List<Pair<GregorianCalendar, Float>> {
        val res = ArrayList<Pair<GregorianCalendar, Float>>()
        val random = Random()
        var prev = random.nextInt(704) + 999
        for (i in 1..103) {
            if (random.nextInt(10) == 0) {
                continue
            }
            val gc = GregorianCalendar(2022, 11, 27)
            val date = gc.time.time + (i - 1) * 86400000L
            val cal = Calendar.getInstance()
            cal.time = Date(date)
            val r = prev + random.nextInt(25) - 10
            res.add(Pair(GregorianCalendar(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)), r.toFloat()))
            prev = r
        }
        return res
    }

    fun getPerfHistory(exercise: ExerciseMode): List<Pair<GregorianCalendar, Float>> {
        val res = ArrayList<Pair<GregorianCalendar, Float>>()
        val random = Random()
        for (i in 1..103) {
            if (random.nextInt(10) == 0) {
                continue
            }
            val gc = GregorianCalendar(2022, 11, 27)
            val date = gc.time.time + (i - 1) * 86400000L
            val cal = Calendar.getInstance()
            cal.time = Date(date)
            val r = random.nextInt(30 + i / 3) + 10
            res.add(Pair(GregorianCalendar(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)), r.toFloat()))
        }
        return res
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
