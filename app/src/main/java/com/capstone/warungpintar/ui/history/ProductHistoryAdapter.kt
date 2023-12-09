package com.capstone.warungpintar.ui.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.capstone.warungpintar.R
import com.capstone.warungpintar.data.remote.model.response.HistoryResponse
import com.capstone.warungpintar.databinding.ItemHistoryProductRowBinding

class ProductHistoryAdapter :
    ListAdapter<HistoryResponse, ProductHistoryAdapter.ProductHistoryViewHolder>(DIFF_CALLBACK) {

    companion object {

        private const val TAG = "ProductHistoryAdapter"

        private val DIFF_CALLBACK: DiffUtil.ItemCallback<HistoryResponse> =
            object : DiffUtil.ItemCallback<HistoryResponse>() {
                override fun areItemsTheSame(
                    oldItem: HistoryResponse,
                    newItem: HistoryResponse
                ): Boolean {
                    return oldItem.historyId == newItem.historyId
                }

                override fun areContentsTheSame(
                    oldItem: HistoryResponse,
                    newItem: HistoryResponse
                ): Boolean {
                    return oldItem == newItem
                }

            }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductHistoryViewHolder {
        val binding = ItemHistoryProductRowBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ProductHistoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductHistoryViewHolder, position: Int) {
        val data = getItem(position)
        if (data != null) {
            holder.bind(data)
        }
    }

    class ProductHistoryViewHolder(val binding: ItemHistoryProductRowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: HistoryResponse) {
            if (data.type.contentEquals("keluar", ignoreCase = true)) {
                with(binding) {
                    imgHistoryProduct.setImageResource(R.drawable.img_product_out)
                    tvTitleHistoryItem.text = data.productName
                    tvPriceItem.text = "Harga Beli ${data.price}"
                    tvDateItem.text = "Tanggal Masuk ${data.date}"
                    tvAmountItem.text = data.amount.toString()
                }
            } else if (data.type.contentEquals("masuk", ignoreCase = true)) {
                with(binding) {
                    imgHistoryProduct.setImageResource(R.drawable.img_product_in)
                    tvTitleHistoryItem.text = data.productName
                    tvPriceItem.text = "Harga Jual ${data.price}"
                    tvDateItem.text = "Tanggal Keluar ${data.date}"
                    tvAmountItem.text = data.amount.toString()
                }
            }
        }

    }
}