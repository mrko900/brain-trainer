package com.github.mrko900.braintrainer

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
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

        binding.button9.setOnClickListener {
            Log.d(LOGGING_TAG, "Begin exercise: " + mainActivity.currentExercise!!.mode)
            mainActivity.navigation.navigate(
                R.id.fragment_exercise,
                navOptions = NavOptions.Builder()
                    .setEnterAnim(R.anim.slide_in_left_fade_in)
                    .setExitAnim(R.anim.slide_out_left_fade_out)
                    .build(),
                args = null
            )
        }

        val adapter = ArrayAdapter.createFromResource(
            mainActivity,
            R.array.exercise_configs,
            android.R.layout.simple_spinner_dropdown_item
        )

        binding.config.setAdapter(adapter)
        binding.config.setOnItemClickListener { parent, view, position, id ->
            changeConfigFragment(when (position) {
                0 -> ConfigFragment.DEFAULT
                1 -> ConfigFragment.CUSTOM
                else -> throw IllegalArgumentException()
            })
        }

        binding.config.setText(adapter.getItem(0), false)
        changeConfigFragment(ConfigFragment.DEFAULT)
    }

    enum class ConfigFragment {
        DEFAULT, CUSTOM
    }

    private fun getCustomConfigFragment(): Fragment = when (mainActivity.currentExercise!!.mode) {
        ExerciseMode.SHAPE_FUSION -> CustomShapeFusionExerciseConfig()
        else -> throw UnsupportedOperationException()
    }

    private fun changeConfigFragment(configFragment: ConfigFragment) {
        val fragment = when (configFragment) {
            ConfigFragment.DEFAULT -> DefaultExerciseConfigFragment()
            ConfigFragment.CUSTOM -> getCustomConfigFragment()
        }
        val transaction = mainActivity.supportFragmentManager.beginTransaction()
        transaction.replace(R.id.config_fragment_container, fragment)
        transaction.commit()
    }
}
