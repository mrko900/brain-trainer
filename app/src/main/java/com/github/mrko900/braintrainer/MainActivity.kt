package com.github.mrko900.braintrainer

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.iterator
import androidx.navigation.NavController
import androidx.navigation.NavOptions
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

    fun getMenuIndices(menu: Menu): Map<Int, Int> {
        val res = HashMap<Int, Int>()
        var i = 0
        for (item in menu) {
            res[item.itemId] = i++
        }
        return res
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = MainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navigation = (supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment).navController

        val animRight: NavOptions = NavOptions.Builder()
            .setEnterAnim(R.anim.slide_in_right_fade_in)
            .setExitAnim(R.anim.slide_out_right_fade_out)
            .build()
        val animLeft: NavOptions = NavOptions.Builder()
            .setEnterAnim(R.anim.slide_in_left_fade_in)
            .setExitAnim(R.anim.slide_out_left_fade_out)
            .build()

        val menuIndices = getMenuIndices(binding.navView.menu)

        binding.navView.setOnItemSelectedListener {
            if (binding.navView.selectedItemId == it.itemId)
                return@setOnItemSelectedListener false
            val slideLeft = menuIndices.getValue(it.itemId) > menuIndices.getValue(binding.navView.selectedItemId)
            navigation.navigate(getFragmentId(it.itemId),
                navOptions = if (slideLeft) animLeft else animRight, args = null)
            return@setOnItemSelectedListener true
        }
    }
}
