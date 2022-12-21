package com.github.mrko900.braintrainer

import android.graphics.Rect
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.github.mrko900.braintrainer.databinding.ExerciseListItemBinding

class ExerciseListViewAdapter(private val layoutInflater: LayoutInflater) :
    RecyclerView.Adapter<ExerciseListViewAdapterViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseListViewAdapterViewHolder {
        val viewHolder = ExerciseListViewAdapterViewHolder(layoutInflater, parent)
        viewHolder.itemView.layoutParams.height = 400
        return viewHolder
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

class ExerciseListViewItemDecoration(private val space: Int, private val columns: Int) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val nItems: Int = ((parent.adapter?.itemCount
            ?: throw IllegalArgumentException("RecyclerView has no adapter")) + columns - 1) / columns * columns
        outRect.left = if (parent.getChildLayoutPosition(view) % columns == 0) space * 2 else space
        outRect.right = if ((parent.getChildLayoutPosition(view) - columns + 1) % columns == 0) space * 2 else space
        outRect.top = if (parent.getChildLayoutPosition(view) < columns) space * 2 else space
        outRect.bottom = if (parent.getChildLayoutPosition(view) >= nItems - columns) space * 2 else space
    }
}
