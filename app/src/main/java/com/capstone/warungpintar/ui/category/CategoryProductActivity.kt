package com.capstone.warungpintar.ui.category

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.capstone.warungpintar.R
import com.capstone.warungpintar.databinding.ActivityCategoryProductBinding

class CategoryProductActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCategoryProductBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryProductBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnClosecategory.setOnClickListener{
            onBackPressed()
        }

    }
}