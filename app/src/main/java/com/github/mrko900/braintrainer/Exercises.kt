package com.github.mrko900.braintrainer

fun exerciseListItems(): List<ExerciseListViewItemParams> {
    val list = ArrayList<ExerciseListViewItemParams>()
    list.add(exerciseListItemMath())
    return list
}

fun exerciseListItemMath(): ExerciseListViewItemParams {
    return ExerciseListViewItemParams(R.string.exercise_title_math)
}
