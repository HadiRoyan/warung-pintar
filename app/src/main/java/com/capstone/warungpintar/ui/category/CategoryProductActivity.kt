package com.capstone.warungpintar.ui.category

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.warungpintar.databinding.ActivityCategoryProductBinding

class CategoryProductActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCategoryProductBinding
    private lateinit var adapter: CategoryProductAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryProductBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupViews()

        binding.topAppBar.setNavigationOnClickListener { _ ->
            onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun setupViews() {
        adapter = CategoryProductAdapter()
        with(binding) {
            rvCategory.adapter = adapter
            rvCategory.layoutManager = LinearLayoutManager(this@CategoryProductActivity)
        }
    }
}