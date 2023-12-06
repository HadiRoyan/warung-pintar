package com.capstone.warungpintar.ui.report

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.capstone.warungpintar.data.remote.model.response.ReportResponse
import com.capstone.warungpintar.databinding.ItemReportRowBinding

class ReportAdapter : ListAdapter<ReportResponse, ReportAdapter.ReportViewHolder>(DIFF_CALLBACK) {

    companion object {

        private const val TAG = "ReportAdapter"

        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ReportResponse>() {
            override fun areItemsTheSame(
                oldItem: ReportResponse,
                newItem: ReportResponse
            ): Boolean {
                return oldItem.productName == newItem.productName
            }

            override fun areContentsTheSame(
                oldItem: ReportResponse,
                newItem: ReportResponse
            ): Boolean {
                return oldItem == newItem
            }
        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReportViewHolder {
        val binding = ItemReportRowBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ReportViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ReportViewHolder, position: Int) {
        val data = getItem(position)
        if (data != null) {
            holder.bind(data)
        }
    }

    class ReportViewHolder(val binding: ItemReportRowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: ReportResponse) {
            val purchasePrice = data.purchasePrice
            val sellingPrice = data.sellingPrice
            val profit = purchasePrice - sellingPrice

            with(binding) {
                tvProductNameRow.text = data.productName
                tvPurchasePriceRowValue.text = "Rp.$purchasePrice"
                tvSellingPriceRowValue.text = "Rp. $sellingPrice"
                tvExitDateRow.text = data.exitDate

                tvProfitProductRow.text = "Rp. $profit"
            }
        }
    }

}