package com.example.abschlussaufgabe.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.abschlussaufgabe.databinding.JutsuComItemBinding


class JutsuComAdapter(private var dataset: Map<String, Int>
): RecyclerView.Adapter<JutsuComAdapter.ItemViewHolder>() {


    inner class ItemViewHolder(val binding: JutsuComItemBinding) :
        RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding =
            JutsuComItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ItemViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return dataset.size
    }


    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val jutsuCom = dataset.keys.elementAt(position)

        holder.binding.tvJutsusCom.setText(jutsuCom)
    }
}