package com.capstone.warungpintar.ui.notification

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.warungpintar.data.ResultState
import com.capstone.warungpintar.databinding.ActivityNotificationBinding

class NotificationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNotificationBinding
    private lateinit var adapter: NotificationAdapter
    private val viewModel: NotificationViewModel by viewModels {
        NotificationViewModelFactory.getInstance()
    }

    // TODO: use the email of the currently logged-in user
    private var email = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotificationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupView()
        viewModel.getListNotification(email)

        viewModel.requestResult.observe(this) { result ->
            if (result != null) {
                when (result) {
                    is ResultState.Loading -> {
                        showLoading(true)
                    }

                    is ResultState.Success -> {
                        adapter.submitList(result.data)
                        showLoading(false)
                    }

                    is ResultState.Error -> {
                        Toast.makeText(
                            this@NotificationActivity,
                            "Terjadi kegagalan, coba lagi",
                            Toast.LENGTH_SHORT
                        ).show()
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
        adapter = NotificationAdapter()
        val recyclerView = binding.rvNotification
        recyclerView.layoutManager = LinearLayoutManager(this@NotificationActivity)
        recyclerView.adapter = adapter
    }

    companion object {
        private const val TAG = "NotificationActivity"
    }
}