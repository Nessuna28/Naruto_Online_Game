package com.example.abschlussaufgabe.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.abschlussaufgabe.databinding.ToolPlayerItemBinding
import com.example.abschlussaufgabe.ui.MainViewModel


class ToolPlayerAdapter(private var dataset: Map<String, Int>,
                        private var viewModel: MainViewModel
): RecyclerView.Adapter<ToolPlayerAdapter.ItemViewHolder>() {


    inner class ItemViewHolder(val binding: ToolPlayerItemBinding) :
        RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding =
            ToolPlayerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ItemViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return dataset.size
    }


    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val toolPlayer = dataset.keys.elementAt(position)

        holder.binding.tvTool.text = toolPlayer


    }
}