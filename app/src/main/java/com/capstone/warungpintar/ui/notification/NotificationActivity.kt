package com.capstone.warungpintar.ui.notification

import android.os.Bundle
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

        // TODO: create ViewModel
        viewModel.requestResult.observe(this) { result ->
            if (result != null) {
                when (result) {
                    is ResultState.Loading -> {
                        // TODO: create loading animation
                    }

                    is ResultState.Success -> {
                        adapter.submitList(result.data)
                    }

                    is ResultState.Error -> {
                        // TODO: handle actions when errors occur
                    }
                }
            }
        }

        binding.topAppBar.setNavigationOnClickListener { _ ->
            onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun setupView() {
        adapter = NotificationAdapter()
        val recyclerView = binding.rvNotification
        recyclerView.layoutManager = LinearLayoutManager(this@NotificationActivity)
        recyclerView.adapter = adapter
    }
}