package com.github.mrko900.braintrainer

import android.content.res.ColorStateList
import android.content.res.Resources
import android.graphics.Rect
import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.github.mrko900.braintrainer.databinding.ExerciseListItemBinding
import kotlin.math.roundToInt

class ExerciseListViewAdapter(private val layoutInflater: LayoutInflater, private val res: Resources) :
    RecyclerView.Adapter<ExerciseListViewAdapterViewHolder>() {
    private val items: MutableList<ExerciseListViewItemParams> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseListViewAdapterViewHolder {
        Log.d(LOGGING_TAG, "CREATE")
        val viewHolder = ExerciseListViewAdapterViewHolder(layoutInflater, parent)
        viewHolder.itemView.layoutParams.height = res.getDimension(R.dimen.exercise_list_item_height).roundToInt()
        return viewHolder
    }

    override fun onBindViewHolder(holder: ExerciseListViewAdapterViewHolder, position: Int) {
        Log.d(LOGGING_TAG, "BIND")
        val item = items[position]
        holder.binding.title.setText(item.titleResId)
        holder.binding.cardViewRoot.background = ColorDrawable(item.primaryColor)
        holder.binding.cardViewBottom.background = ColorDrawable(item.secondaryColor)
        holder.binding.playButton.backgroundTintList = ColorStateList.valueOf(item.secondaryColor)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun addItem(item: ExerciseListViewItemParams) {
        items.add(item)
    }

    // todo remove item
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

data class ExerciseListViewItemParams(val titleResId: Int, val primaryColor: Int, val secondaryColor: Int)
