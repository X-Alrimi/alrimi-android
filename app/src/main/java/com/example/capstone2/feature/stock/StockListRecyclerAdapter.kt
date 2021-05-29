package com.example.capstone2.feature.stock

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.capstone2.core.model.Stock
import com.example.capstone2.databinding.ItemStockBinding

class StockListRecyclerAdapter(var stocks : ArrayList<Stock>,
                                val viewModel : StockListViewModel) : RecyclerView.Adapter<StockListRecyclerAdapter.StockViewHolder>() {
    lateinit var mBinding: ItemStockBinding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StockViewHolder {
        mBinding = ItemStockBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StockViewHolder(mBinding)
    }

    override fun onBindViewHolder(holder: StockViewHolder, position: Int) {
        holder.bind(stocks[position], viewModel)
    }

    override fun getItemCount(): Int = stocks.size

    class StockViewHolder(private val binding: ItemStockBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Stock, viewModel: StockListViewModel) {
            binding.stock = item
            binding.vm = viewModel
        }
    }
}