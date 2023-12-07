package com.capstone.warungpintar.ui.notification

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.capstone.warungpintar.R
import com.capstone.warungpintar.data.remote.model.response.NotificationResponse
import com.capstone.warungpintar.databinding.ItemNotificationRowBinding

class NotificationAdapter :
    ListAdapter<NotificationResponse, NotificationAdapter.NotificationViewHolder>(
        DIFF_CALLBACK
    ) {

    companion object {

        private const val TAG = "NotificationAdapter"

        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<NotificationResponse>() {
            override fun areItemsTheSame(
                oldItem: NotificationResponse,
                newItem: NotificationResponse
            ): Boolean {
                return oldItem.storeName == newItem.storeName
            }

            override fun areContentsTheSame(
                oldItem: NotificationResponse,
                newItem: NotificationResponse
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationViewHolder {
        val binding = ItemNotificationRowBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return NotificationViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: NotificationViewHolder, position: Int) {
        val message = getItem(position)
        if (message != null) {
            holder.bind(message)
        }
    }

    class NotificationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(message: NotificationResponse) {
            if (message.type.contains("low", ignoreCase = true)) {
                val description = itemView.context.getString(R.string.desc_notification_low_stock)

                itemView.findViewById<TextView>(R.id.tv_title_notification_item).text = "Low Stock"
                itemView.findViewById<TextView>(R.id.tv_description_notification_item).text =
                    description
                itemView.findViewById<ImageView>(R.id.item_notification_img)
                    .setImageResource(R.drawable.img_notif_lows_stock)
            } else if (message.type.contains("expired", ignoreCase = true)) {
                val description =
                    itemView.context.getString(R.string.desc_notification_expired_stock)

                itemView.findViewById<TextView>(R.id.tv_title_notification_item).text =
                    "Expired Stock"
                itemView.findViewById<TextView>(R.id.tv_description_notification_item).text =
                    description
                itemView.findViewById<ImageView>(R.id.item_notification_img)
                    .setImageResource(R.drawable.img_notif_expired_stock)
            }
        }
    }
}