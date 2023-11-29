package com.capstone.warungpintar.ui.addproduct

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.capstone.warungpintar.R
import com.capstone.warungpintar.databinding.ActivityAddProductInBinding

class AddProductInActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddProductInBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddProductInBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnAddproductClose.setOnClickListener{
            onBackPressed()
        }
    }
}