package com.capstone.warungpintar.ui.liststockproduct

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.warungpintar.databinding.ActivityListStockProductBinding

class ListStockProductActivity : AppCompatActivity() {

    private lateinit var binding: ActivityListStockProductBinding
    private lateinit var adapter: ListStockAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListStockProductBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupView()
    }

    private fun setupView() {
        adapter = ListStockAdapter()
        val recyclerView = binding.rvListStockProduct
        recyclerView.layoutManager = LinearLayoutManager(this@ListStockProductActivity)
        recyclerView.adapter = adapter
    }
}