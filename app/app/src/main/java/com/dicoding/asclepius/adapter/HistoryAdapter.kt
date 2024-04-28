package com.dicoding.asclepius.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.asclepius.database.History
import com.dicoding.asclepius.databinding.ItemHistoryBinding

class HistoryAdapter(private val listHistory: List<History>) : RecyclerView.Adapter<HistoryAdapter.ViewHolder>(){

    class ViewHolder(var binding: ItemHistoryBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val (id, image, result) = listHistory[position]
        Glide.with(holder.itemView.context).load(image).into(holder.binding.ivImage)
        holder.binding.tvResult.text = result
    }

    override fun getItemCount(): Int = listHistory.size

}