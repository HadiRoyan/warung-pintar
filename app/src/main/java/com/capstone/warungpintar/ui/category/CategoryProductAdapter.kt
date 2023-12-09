package com.capstone.warungpintar.ui.category

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.capstone.warungpintar.databinding.ItemCategoryProductRowBinding

class CategoryProductAdapter : ListAdapter<String, CategoryProductAdapter.CategoryViewHolder>(
    DIFF_CALLBACK
) {

    companion object {

        private const val TAG = "CategoryProductAdapter"

        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<String>() {
            override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
                return oldItem == newItem
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val binding = ItemCategoryProductRowBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CategoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val productName = getItem(position)
        if (productName != null) {
            holder.bind(productName)
        }
    }

    class CategoryViewHolder(val binding: ItemCategoryProductRowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(productName: String) {
            binding.tvTitleCategoryItem.text = productName
        }
    }
}