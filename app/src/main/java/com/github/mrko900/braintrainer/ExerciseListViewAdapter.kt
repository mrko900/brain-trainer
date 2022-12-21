package com.github.mrko900.braintrainer

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.github.mrko900.braintrainer.databinding.ExerciseListItemBinding

class ExerciseListViewAdapter(private val layoutInflater: LayoutInflater) :
    RecyclerView.Adapter<ExerciseListViewAdapterViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseListViewAdapterViewHolder {
        return ExerciseListViewAdapterViewHolder(layoutInflater, parent)
    }

    override fun onBindViewHolder(holder: ExerciseListViewAdapterViewHolder, position: Int) {
    }

    override fun getItemCount(): Int {
        return 3
    }
}

class ExerciseListViewAdapterViewHolder : RecyclerView.ViewHolder {
    val binding: ExerciseListItemBinding

    constructor(layoutInflater: LayoutInflater, parent: ViewGroup?) :
            this(ExerciseListItemBinding.inflate(layoutInflater, parent, false))

    private constructor(binding: ExerciseListItemBinding) :
            super(binding.root) {
        this.binding = binding
    }
}
