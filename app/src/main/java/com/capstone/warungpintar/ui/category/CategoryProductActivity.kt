package com.capstone.warungpintar.ui.category

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.warungpintar.data.ResultState
import com.capstone.warungpintar.databinding.ActivityCategoryProductBinding

class CategoryProductActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCategoryProductBinding
    private lateinit var adapter: CategoryProductAdapter

    private val viewModel: ListCategoryViewModel by viewModels {
        ListCategoryViewModelFactory.getInstance()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryProductBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupViews()
        viewModel.getListCategory()

        viewModel.listCategory.observe(this) { result ->
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
                        showMessage("Terjadi kegagalan, coba lagi nanti")
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

    private fun setupViews() {
        adapter = CategoryProductAdapter()
        with(binding) {
            rvCategory.adapter = adapter
            rvCategory.layoutManager = LinearLayoutManager(this@CategoryProductActivity)
        }
    }

    private fun showMessage(message: String) {
        Toast.makeText(this@CategoryProductActivity, message, Toast.LENGTH_SHORT).show()
    }

    companion object {
        private const val TAG = "CategoryProductActivity"
    }
}