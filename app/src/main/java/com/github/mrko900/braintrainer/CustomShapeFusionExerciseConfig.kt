package com.github.mrko900.braintrainer

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.github.mrko900.braintrainer.databinding.ShapeFusionExerciseCustomConfigBinding

class CustomShapeFusionExerciseConfig : Fragment() {
    private lateinit var binding: ShapeFusionExerciseCustomConfigBinding
    private lateinit var mainActivity: MainActivity

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (activity == null) {
            Log.e(LOGGING_TAG, "activity must be non-null")
            throw IllegalStateException("activity must be non-null")
        }
        mainActivity = activity as MainActivity
        binding = ShapeFusionExerciseCustomConfigBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = ArrayAdapter.createFromResource(
            mainActivity,
            R.array.shape_fusion_exercise_operations,
            android.R.layout.simple_spinner_dropdown_item
        )

        binding.operations.setAdapter(adapter)
        binding.operations.setText(adapter.getItem(0), false)
    }
}
