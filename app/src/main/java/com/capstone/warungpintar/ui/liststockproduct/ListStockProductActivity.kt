package com.capstone.warungpintar.ui.liststockproduct

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.warungpintar.data.ResultState
import com.capstone.warungpintar.databinding.ActivityListStockProductBinding

class ListStockProductActivity : AppCompatActivity() {

    private lateinit var binding: ActivityListStockProductBinding
    private lateinit var adapter: ListStockAdapter

    private val viewModel: ListStockViewModel by viewModels {
        ListStockViewModelFactory.getInstance()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListStockProductBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupView()
        viewModel.getListProduct()

        viewModel.resultList.observe(this) { result ->
            if (result != null) {
                when (result) {
                    is ResultState.Loading -> {
                        showLoading(true)
                    }

                    is ResultState.Success -> {
                        val data = result.data
                        adapter.submitList(data)
                        showLoading(false)
                    }

                    is ResultState.Error -> {
                        // TODO: handle actions when errors occur
                        showLoading(false)
                        Log.d(TAG, "onCreate: error fetch data from API: ${result.error}")
                    }
                }
            }
        }

        binding.topAppBar.setNavigationOnClickListener { _ ->
            onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun setupView() {
        adapter = ListStockAdapter()
        val recyclerView = binding.rvListStockProduct
        recyclerView.layoutManager = LinearLayoutManager(this@ListStockProductActivity)
        recyclerView.adapter = adapter
    }

    companion object {
        private const val TAG = "ListStockProductActivit"
    }
}