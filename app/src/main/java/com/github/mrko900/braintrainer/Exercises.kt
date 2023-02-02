package com.github.mrko900.braintrainer

import android.graphics.Color

fun exerciseListItems(): List<ExerciseListViewItemParams> {
    val list = ArrayList<ExerciseListViewItemParams>()
    list.add(exerciseListItemMath())
    for (i in 1..100) {
        list.add(exerciseListItemTest1())
        list.add(exerciseListItemTest2())
        list.add(exerciseListItemTest3())
        list.add(exerciseListItemTest4())
    }
    return list
}

fun exerciseListItemMath(): ExerciseListViewItemParams {
    return ExerciseListViewItemParams(
        R.string.exercise_title_math,
        Color.parseColor("#FF8A65"),
        Color.parseColor("#FBE9E7")
    )
}

fun exerciseListItemTest1(): ExerciseListViewItemParams {
    return ExerciseListViewItemParams(
        R.string.exercise_title_math,
        Color.parseColor("#81C784"),
        Color.parseColor("#E8F5E9")
    )
}

fun exerciseListItemTest2(): ExerciseListViewItemParams {
    return ExerciseListViewItemParams(
        R.string.exercise_title_math,
        Color.parseColor("#FFF48FB1"),
        Color.parseColor("#FFFCE4EC")
    )
}

fun exerciseListItemTest3(): ExerciseListViewItemParams {
    return ExerciseListViewItemParams(
        R.string.exercise_title_math,
        Color.parseColor("#FFFFF59D"),
        Color.parseColor("#FFFFFDE7")
    )
}

fun exerciseListItemTest4(): ExerciseListViewItemParams {
    return ExerciseListViewItemParams(
        R.string.exercise_title_math,
        Color.parseColor("#FF81D4FA"),
        Color.parseColor("#FFE1F5FE")
    )
}
