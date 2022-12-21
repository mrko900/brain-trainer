package com.github.mrko900.braintrainer

import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.mrko900.braintrainer.databinding.ExerciseListBinding
import kotlin.math.roundToInt

class ExerciseListFragment : Fragment() {
    private lateinit var binding: ExerciseListBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = ExerciseListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListView(binding.exerciseListView)
    }

    private fun initListView(listView: RecyclerView) {
        val spanCount = resources.getInteger(R.integer.exercise_list_span_count)
        listView.layoutManager = GridLayoutManager(context, spanCount)
        listView.adapter = ExerciseListViewAdapter(layoutInflater)
        listView.addItemDecoration(
            ExerciseListViewItemDecoration(
                TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP,
                    resources.getInteger(R.integer.exercise_list_spacing_dp).toFloat(),
                    resources.displayMetrics
                ).roundToInt(), spanCount
            )
        )
    }
}
