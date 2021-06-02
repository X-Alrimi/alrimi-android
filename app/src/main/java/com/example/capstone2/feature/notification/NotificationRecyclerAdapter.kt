package com.example.capstone2.feature.notification

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.capstone2.core.model.Notification
import com.example.capstone2.databinding.ItemNotificationBinding

class NotificationRecyclerAdapter(var notifications: ArrayList<Notification>,
                                    val viewModel: NotificationListViewModel): RecyclerView.Adapter<NotificationRecyclerAdapter.NotificationViewHolder>() {
    private lateinit var mBinding: ItemNotificationBinding

    fun addNotification() {
        this.notifications.addAll(viewModel.more)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationViewHolder {
        mBinding = ItemNotificationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NotificationViewHolder(mBinding)
    }

    override fun getItemCount(): Int = notifications.size

    override fun onBindViewHolder(holder: NotificationViewHolder, position: Int) {
        holder.bind(notifications[position], viewModel)
    }

    class NotificationViewHolder(private val binding: ItemNotificationBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(item: Notification, viewModel: NotificationListViewModel) {
            binding.notification = item
            binding.vm = viewModel
        }
    }
}