package com.github.mrko900.braintrainer

import android.content.res.Resources
import android.graphics.Color

fun exerciseListItems(): MutableList<ExerciseListViewItemParams> {
    val list = ArrayList<ExerciseListViewItemParams>()
    list.add(exerciseListItemShapeFusion())
    list.add(exerciseListItemTrails())
    return list
}

fun exerciseListItemShapeFusion(): ExerciseListViewItemParams {
    return ExerciseListViewItemParams(
        R.string.exercise_title_shape_fusion,
        Color.parseColor("#FF8A65"),
        Color.parseColor("#FBE9E7"),
        ExerciseMode.SHAPE_FUSION
    )
}

fun exerciseListItemTrails(): ExerciseListViewItemParams {
    return ExerciseListViewItemParams(
        R.string.exercise_title_trails,
        Color.parseColor("#FF8A65"),
        Color.parseColor("#FBE9E7"),
        ExerciseMode.TRAILS
    )
}

fun getExerciseName(mode: ExerciseMode, res: Resources): String {
    return res.getString(when (mode) {
        ExerciseMode.SHAPE_FUSION -> R.string.exercise_title_shape_fusion
        ExerciseMode.TRAILS -> R.string.exercise_title_trails
        else -> throw UnsupportedOperationException()
    })
}
