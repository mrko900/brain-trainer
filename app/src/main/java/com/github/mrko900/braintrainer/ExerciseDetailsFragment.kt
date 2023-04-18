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

    private var currentConfigSelection = 0
    private lateinit var configFragment: Fragment

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
            setConfig()
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
            changeConfigFragment(idToConfigFragment(position))
            currentConfigSelection = position
        }

        binding.config.setText(adapter.getItem(currentConfigSelection), false)
        changeConfigFragment(idToConfigFragment(currentConfigSelection))
    }

    enum class ConfigFragment {
        DEFAULT, CUSTOM
    }

    private fun idToConfigFragment(id: Int): ConfigFragment = when (id) {
        0 -> ConfigFragment.DEFAULT
        1 -> ConfigFragment.CUSTOM
        else -> throw java.lang.IllegalArgumentException()
    }

    private fun getCustomConfigFragment(): Fragment = when (mainActivity.currentExercise!!.mode) {
        ExerciseMode.SHAPE_FUSION -> CustomShapeFusionExerciseConfigFragment()
        else -> throw UnsupportedOperationException()
    }

    private fun changeConfigFragment(configFragment: ConfigFragment) {
        this.configFragment = when (configFragment) {
            ConfigFragment.DEFAULT -> DefaultExerciseConfigFragment()
            ConfigFragment.CUSTOM -> getCustomConfigFragment()
        }
        val transaction = mainActivity.supportFragmentManager.beginTransaction()
        transaction.replace(R.id.config_fragment_container, this.configFragment)
        transaction.commit()
    }

    private fun getDefaultShapeFusionExerciseConfig(): ShapeFusionExerciseConfig {
        // todo difficulty
        return ShapeFusionExerciseConfig(
            nTermsInitial = 3,
            nChoicesInitial = 4,
            dynamic = true,
            additionOperation = true,
            subtractionOperation = true
        )
    }

    private fun getCustomShapeFusionExerciseConfig(): ShapeFusionExerciseConfig {
        val shapeFusionConfigFragment = configFragment as CustomShapeFusionExerciseConfigFragment
        return ShapeFusionExerciseConfig(
            nTermsInitial = shapeFusionConfigFragment.getNTerms(),
            nChoicesInitial = shapeFusionConfigFragment.getNChoices(),
            dynamic = shapeFusionConfigFragment.isDynamic(),
            additionOperation = shapeFusionConfigFragment.hasAdditionOperation(),
            subtractionOperation = shapeFusionConfigFragment.hasSubtractionOperation()
        )
    }

    private fun setShapeFusionExerciseConfig() {
        mainActivity.currentExercise!!.config = when (idToConfigFragment(currentConfigSelection)) {
            ConfigFragment.DEFAULT -> getDefaultShapeFusionExerciseConfig()
            ConfigFragment.CUSTOM -> getCustomShapeFusionExerciseConfig()
        }
    }

    private fun setConfig() = when (mainActivity.currentExercise!!.mode) {
        ExerciseMode.SHAPE_FUSION -> setShapeFusionExerciseConfig()
        else -> throw UnsupportedOperationException()
    }
}
