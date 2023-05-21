package com.github.mrko900.braintrainer

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import com.github.mrko900.braintrainer.databinding.StatsGeneralBinding

class StatsGeneralFragment : Fragment() {
    private lateinit var binding: StatsGeneralBinding
    private lateinit var mainActivity: MainActivity

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (activity == null) {
            Log.e(LOGGING_TAG, "activity must be non-null")
            throw IllegalStateException("activity must be non-null")
        }
        mainActivity = activity as MainActivity
        binding = StatsGeneralBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainActivity.setSupportActionBar(binding.toolbar)
        mainActivity.supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        mainActivity.supportActionBar!!.setDisplayShowHomeEnabled(true)
        mainActivity.onBackPressedCallback = Runnable {
            mainActivity.navigation.navigate(
                R.id.fragment_stats,
                navOptions = NavOptions.Builder()
                    .setEnterAnim(R.anim.slide_in_right_fade_in)
                    .setExitAnim(R.anim.slide_out_right_fade_out)
                    .build(),
                args = null
            )
        }
    }
}
