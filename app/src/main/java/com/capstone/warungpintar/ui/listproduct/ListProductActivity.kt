package com.capstone.warungpintar.ui.listproduct

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.capstone.warungpintar.R
import com.capstone.warungpintar.databinding.ActivityListProductBinding

class ListProductActivity : AppCompatActivity() {
    private lateinit var binding: ActivityListProductBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListProductBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}