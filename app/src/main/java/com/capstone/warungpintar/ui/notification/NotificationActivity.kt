package com.capstone.warungpintar.ui.notification

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.warungpintar.databinding.ActivityNotificationBinding

class NotificationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNotificationBinding
    private lateinit var adapter: NotificationAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotificationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupView()

        // TODO: create ViewModel
    }

    private fun setupView() {
        adapter = NotificationAdapter()
        val recyclerView = binding.rvNotification
        recyclerView.layoutManager = LinearLayoutManager(this@NotificationActivity)
        recyclerView.adapter = adapter
    }
}