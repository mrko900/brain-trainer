package com.github.mrko900.braintrainer

import android.content.res.ColorStateList
import android.content.res.Resources
import android.graphics.Rect
import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.recyclerview.widget.RecyclerView
import com.github.mrko900.braintrainer.databinding.ExerciseListItemBinding
import kotlin.math.roundToInt

typealias VH = ExerciseListViewAdapterViewHolder

class ExerciseListViewAdapter(
    private val recyclerView: RecyclerView,
    private val layoutInflater: LayoutInflater,
    private val res: Resources,
    private val nav: NavController,
    private val preCreateViews: Int,
    private var onInitCallback: Runnable? = null,
    private val maxItemsVisibleAtFirst: Int = -1
) : RecyclerView.Adapter<ExerciseListViewAdapterViewHolder>() {
    private val items: MutableList<ExerciseListViewItemParams> = ArrayList()
    private var createdCnt = 0
    private var initCnt = 0
    private val viewHolders: MutableList<VH> = ArrayList()

    init {
        if (preCreateViews <= 0)
            throw IllegalArgumentException("preCreateViews must be >= 0")
        if (onInitCallback != null && maxItemsVisibleAtFirst <= 0)
            throw IllegalArgumentException("maxItemsVisibleAtFirst must be >0 when onInitCallback is specified")
    }

    fun preCreateViewHolders() {
        for (i in 1..preCreateViews)
            viewHolders.add(VH(layoutInflater, recyclerView, nav))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseListViewAdapterViewHolder {
        val viewHolder = viewHolders[createdCnt]
        viewHolder.itemView.layoutParams.height = res.getDimension(R.dimen.exercise_list_item_height).roundToInt()
        ++createdCnt
        return viewHolder
    }

    override fun onBindViewHolder(holder: ExerciseListViewAdapterViewHolder, position: Int) {
        val item = items[position]
        holder.binding.title.setText(item.titleResId)
        holder.binding.cardViewRoot.background = ColorDrawable(item.primaryColor)
        holder.binding.cardViewBottom.background = ColorDrawable(item.secondaryColor)
        holder.binding.playButton.backgroundTintList = ColorStateList.valueOf(item.secondaryColor)

        if (onInitCallback != null) {
            ++initCnt
            if (initCnt == maxItemsVisibleAtFirst) {
                onInitCallback!!.run()
                onInitCallback = null
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun addItem(item: ExerciseListViewItemParams) {
        items.add(item)
    }

    // todo remove item
}

// todo don't inflate inside a constructor
class ExerciseListViewAdapterViewHolder : RecyclerView.ViewHolder {
    val binding: ExerciseListItemBinding
    val nav: NavController

    constructor(layoutInflater: LayoutInflater, parent: ViewGroup?, nav: NavController) :
            this(ExerciseListItemBinding.inflate(layoutInflater, parent, false), nav)

    private constructor(binding: ExerciseListItemBinding, nav: NavController) :
            super(binding.root) {
        this.binding = binding
        this.nav = nav
        initListItem(binding)
    }

    private fun initListItem(binding: ExerciseListItemBinding) {
        binding.playButton.setOnClickListener {
            Log.d(LOGGING_TAG, "Exercise selected")
            nav.navigate(
                R.id.fragment_exercise_details,
                navOptions = NavOptions.Builder()
                    .setEnterAnim(R.anim.slide_in_left_fade_in)
                    .setExitAnim(R.anim.slide_out_left_fade_out)
                    .build(),
                args = null
            )
        }
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
