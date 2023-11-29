package com.capstone.warungpintar.ui.liststockproduct

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.capstone.warungpintar.R
import com.capstone.warungpintar.data.model.Product
import com.capstone.warungpintar.databinding.ItemStockProductRowBinding

class ListStockAdapter : ListAdapter<Product, ListStockAdapter.ListStockViewHolder>(DIFF_CALLBACK) {

    companion object {

        private const val TAG = "ListStockAdapter"

        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Product>() {
            override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
                return oldItem.productId == newItem.productId
            }

            override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
                return oldItem == newItem
            }
        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListStockAdapter.ListStockViewHolder {
        val binding = ItemStockProductRowBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ListStockViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: ListStockAdapter.ListStockViewHolder, position: Int) {
        val product = getItem(position)
        if (product != null) {
            holder.bind(product)
        }
    }

    inner class ListStockViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imgProduct = itemView.findViewById<ImageView>(R.id.img_stock_product_row)
        private val tvProductName = itemView.findViewById<TextView>(R.id.tv_product_name_row)
        private val tvSalePrice = itemView.findViewById<TextView>(R.id.tv_sale_price_row_value)
        private val tvBuyPrice = itemView.findViewById<TextView>(R.id.tv_buy_price_row_value)
        private val tvTotalStock = itemView.findViewById<TextView>(R.id.tv_total_product_row_value)
        private val tvEntryDate = itemView.findViewById<TextView>(R.id.tv_entry_date_row_value)

        fun bind(product: Product) {
            Glide.with(itemView)
                .load(product.imageUrl)
                .into(imgProduct)
            tvProductName.text = product.productName
            tvSalePrice.text = "Rp.${product.sellingPrice}"
            tvBuyPrice.text = "Rp.${product.purchasePrice}"
            tvEntryDate.text = product.entryDate
            tvTotalStock.text = product.productQuantity.toString()
        }
    }
}