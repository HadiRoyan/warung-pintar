package com.capstone.warungpintar.ui.history

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.warungpintar.data.ResultState
import com.capstone.warungpintar.databinding.ActivityProductHistoryBinding

class ProductHistoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProductHistoryBinding
    private lateinit var adapter: ProductHistoryAdapter

    // TODO: use the email of the currently logged-in user
    private var email = ""

    private val viewModel: ProductHistoryViewModel by viewModels {
        ProductHistoryViewModelFactory.getInstance()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setTopBar()
        setupView()
        viewModel.getListHistory(email)

        viewModel.listHistory.observe(this) { result ->
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
                        Toast.makeText(
                            this@ProductHistoryActivity,
                            "Terjadi kegagalan, coba lagi",
                            Toast.LENGTH_SHORT
                        ).show()
                        showLoading(false)
                        Log.d(TAG, "onCreate: error fetch data from API: ${result.error}")
                    }
                }
            }
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
        adapter = ProductHistoryAdapter()
        val recyclerView = binding.rvHistoryProduct
        recyclerView.layoutManager = LinearLayoutManager(this@ProductHistoryActivity)
        recyclerView.adapter = adapter
    }

    private fun setTopBar() {
        binding.topAppBar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    companion object {
        private const val TAG = "ProductHistoryActivity"
    }
}