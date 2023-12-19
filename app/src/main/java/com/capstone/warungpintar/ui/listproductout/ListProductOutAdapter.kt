package com.capstone.warungpintar.ui.listproductout

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.capstone.warungpintar.databinding.ActivityListProductOutRowBinding

class ListProductOutAdapter :
    ListAdapter<String, ListProductOutAdapter.ListOutViewHolder>(DIFF_CALLBACK) {

    companion object {

        private const val TAG = "ListProductOutAdapter"

        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<String>() {
            override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
                return oldItem == newItem
            }
        }

    }

    class ListOutViewHolder(private val binding: ActivityListProductOutRowBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(productName: String) {
            binding.tvProductNameRow.text = productName
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListOutViewHolder {
        val binding = ActivityListProductOutRowBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ListOutViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListOutViewHolder, position: Int) {
        val data = getItem(position)
        if (data != null) {
            holder.bind(data)
        }
    }
}