package com.github.mrko900.braintrainer

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import com.github.mrko900.braintrainer.databinding.ExerciseDetailsBinding

class ExerciseDetailsFragment : Fragment() {
    private lateinit var binding: ExerciseDetailsBinding
    private lateinit var mainActivity: MainActivity

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (activity == null) {
            Log.e(LOGGING_TAG, "activity must be non-null")
            throw IllegalStateException("activity must be non-null")
        }
        mainActivity = activity as MainActivity
        binding = ExerciseDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.button7.setOnClickListener {
            Log.d(LOGGING_TAG, "Begin exercise: ") // todo exercise name
            mainActivity.navigation.navigate(
                R.id.fragment_exercise,
                navOptions = NavOptions.Builder()
                    .setEnterAnim(R.anim.slide_in_left_fade_in)
                    .setExitAnim(R.anim.slide_out_left_fade_out)
                    .build(),
                args = null
            )
        }
    }
}
