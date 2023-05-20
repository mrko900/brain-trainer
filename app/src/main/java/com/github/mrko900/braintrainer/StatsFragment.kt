package com.github.mrko900.braintrainer

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.mrko900.braintrainer.databinding.MenuItemBinding
import com.github.mrko900.braintrainer.databinding.StatsMenuBinding

class StatsFragment : Fragment() {
    private lateinit var binding: StatsMenuBinding
    private lateinit var mainActivity: MainActivity

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (activity == null) {
            Log.e(LOGGING_TAG, "activity must be non-null")
            throw IllegalStateException("activity must be non-null")
        }
        mainActivity = activity as MainActivity
        binding = StatsMenuBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val list = binding.menu
        list.layoutManager = LinearLayoutManager(mainActivity)
        list.adapter = Adapter(mainActivity.layoutInflater)
    }

    private data class ListItem(val title: String)

    private class Adapter(private val inflater: LayoutInflater) : RecyclerView.Adapter<VH>() {
        companion object {
            private val items = listOf(
                ListItem("Testtest 1"),
                ListItem("Testtest 2"),
                ListItem("Testtest 3")
            )
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
            return VH(MenuItemBinding.inflate(inflater, parent, false))
        }

        override fun onBindViewHolder(holder: VH, position: Int) {
            holder.binding.textView5.text = items[position].title
        }

        override fun getItemCount(): Int {
            return items.size
        }
    }

    private class VH(val binding: MenuItemBinding) : RecyclerView.ViewHolder(binding.root)
}
