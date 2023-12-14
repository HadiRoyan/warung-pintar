package com.capstone.warungpintar.ui.listproductout

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.warungpintar.databinding.ActivityListProductOutBinding

class ListProductOutActivity : AppCompatActivity() {

    private lateinit var binding: ActivityListProductOutBinding
    private lateinit var adapter: ListProductOutAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListProductOutBinding.inflate(layoutInflater)
        setContentView(binding.root)
        adapter = ListProductOutAdapter()
        with(binding) {
            // setup top bar
            topAppBar.setNavigationOnClickListener {
                onBackPressedDispatcher.onBackPressed()
            }
            
            // setup recycler view
            listItem.layoutManager = LinearLayoutManager(this@ListProductOutActivity)
            listItem.adapter = adapter
        }

    }
}