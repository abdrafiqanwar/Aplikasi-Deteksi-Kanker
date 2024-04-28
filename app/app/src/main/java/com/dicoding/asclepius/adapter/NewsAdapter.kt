package com.dicoding.asclepius.adapter

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.asclepius.databinding.ItemNewsBinding
import com.dicoding.asclepius.response.ArticlesItem

class NewsAdapter(private val listNews: List<ArticlesItem>) : RecyclerView.Adapter<NewsAdapter.ViewHolder> () {
    class ViewHolder(val binding: ItemNewsBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val (image, desc, title, url) = listNews[position]
        Glide.with(holder.itemView.context).load(image).into(holder.binding.ivImage)
        holder.binding.tvTitle.text = title.toString()
        if (desc.toString() == "null") {
            holder.binding.tvDesc.visibility = View.GONE
        } else {
            holder.binding.tvDesc.visibility = View.VISIBLE
            holder.binding.tvDesc.text = desc.toString()
        }
        holder.itemView.setOnClickListener{
            val intent = Intent(Intent.ACTION_VIEW)
            intent.setData(Uri.parse(url))
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = listNews.size
}