package com.example.capstone2.feature.news

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.capstone2.core.model.News
import com.example.capstone2.databinding.ItemNewsBinding

class NewsListRecyclerAdapter(var news: ArrayList<News>,
                                val viewModel: NewsListViewModel): RecyclerView.Adapter<NewsListRecyclerAdapter.NewsViewHolder>() {
    private lateinit var mBinding: ItemNewsBinding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        mBinding = ItemNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewsViewHolder(mBinding)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.bind(news[position], viewModel)
    }

    override fun getItemCount(): Int = news.size

    class NewsViewHolder(private val binding: ItemNewsBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: News, viewModel: NewsListViewModel) {
            binding.news = item
            binding.vm = viewModel
        }
    }
}