package com.capstone.warungpintar.ui.history

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.warungpintar.databinding.ActivityProductHistoryBinding

class ProductHistoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProductHistoryBinding
    private lateinit var adapter: ProductHistoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setTopBar()
        setupView()

        // TODO: create ViewModel
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
}