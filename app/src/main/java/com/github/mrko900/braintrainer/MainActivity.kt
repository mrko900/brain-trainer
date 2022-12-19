package com.github.mrko900.braintrainer

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavAction
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.github.mrko900.braintrainer.databinding.MainBinding

class MainActivity : AppCompatActivity() {
    companion object {
        @JvmField
        val LOGGING_TAG = MainActivity::class.java.name

        private fun getFragmentId(navId: Int): Int = when (navId) {
            R.id.home -> R.id.fragment_home
            R.id.options -> R.id.fragment_options
            else -> throw IllegalArgumentException("unknown fragment")
        }
    }

    private lateinit var binding: MainBinding
    private lateinit var navigation: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = MainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navigation = (supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment).navController
        val testnav1 = View.generateViewId()
        val testnav2 = View.generateViewId()
        navigation.findDestination(R.id.fragment_home)!!.putAction(testnav1,
            NavAction(R.id.fragment_options))
        navigation.findDestination(R.id.fragment_options)!!.putAction(testnav2,
            NavAction(R.id.fragment_home))

        binding.navView.setOnItemSelectedListener {
            navigation.navigate(if (it.itemId == R.id.home) testnav2 else testnav1)
            return@setOnItemSelectedListener true
        }
    }
}
