package com.capstone.warungpintar.ui.dashboard

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.capstone.warungpintar.databinding.ActivityDashboardProductBinding
import com.capstone.warungpintar.ui.addproduct.AddProductInActivity
import com.capstone.warungpintar.ui.category.CategoryProductActivity

class DashboardProduct : AppCompatActivity() {
    private lateinit var binding: ActivityDashboardProductBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardProductBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnBarangmasuk.setOnClickListener{
            val intent = Intent(this, AddProductInActivity::class.java)
            startActivity(intent)
        }
        binding.btnKategori.setOnClickListener{
            val intent = Intent(this, CategoryProductActivity::class.java)
            startActivity(intent)
        }
    }
}