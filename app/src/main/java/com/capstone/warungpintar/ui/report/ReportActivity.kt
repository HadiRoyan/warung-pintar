package com.capstone.warungpintar.ui.report

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.warungpintar.data.ResultState
import com.capstone.warungpintar.databinding.ActivityReportBinding

class ReportActivity : AppCompatActivity() {

    private lateinit var binding: ActivityReportBinding
    private lateinit var adapter: ReportAdapter

    // TODO: use the email of the currently logged-in user
    private var email = ""

    private val viewModel: ReportViewModel by viewModels {
        ReportViewModelFactory.getInstance()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReportBinding.inflate(layoutInflater)
        setContentView(binding.root)
        adapter = ReportAdapter()
        with(binding) {
            rvReportProduct.layoutManager = LinearLayoutManager(this@ReportActivity)
            rvReportProduct.adapter = adapter

            topAppBar.setNavigationOnClickListener {
                onBackPressedDispatcher.onBackPressed()
            }
        }

        if (email.isNotEmpty()) {
            viewModel.getListReport(email)
        } else {
            Toast.makeText(this, "Terjadi kegagalan", Toast.LENGTH_SHORT).show()
            Log.d(TAG, "onCreate: email is null, cannot fetch data")
        }

        viewModel.listReport.observe(this) { result ->
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
                        showLoading(false)
                        Toast.makeText(
                            this@ReportActivity,
                            "Terjadi kegagalan, coba lagi",
                            Toast.LENGTH_SHORT
                        ).show()
                        Log.d(TAG, "get reports: error fetch data from API: ${result.error}")
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

    companion object {
        private const val TAG = "ReportActivity"
    }
}