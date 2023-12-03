package com.capstone.warungpintar.ui.dashboard

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.capstone.warungpintar.R
import com.capstone.warungpintar.databinding.ActivityDashboardProductBinding
import com.capstone.warungpintar.ui.addproduct.AddProductInActivity
import com.capstone.warungpintar.ui.category.CategoryProductActivity
import com.capstone.warungpintar.ui.history.ProductHistoryActivity
import com.capstone.warungpintar.ui.liststockproduct.ListStockProductActivity
import com.capstone.warungpintar.ui.notification.NotificationActivity
import com.capstone.warungpintar.ui.welcoming.WelcomeActivity

class DashboardProduct : AppCompatActivity() {
    private lateinit var binding: ActivityDashboardProductBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardProductBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setTopBar()

        binding.btnBarangmasuk.setOnClickListener {
            val intent = Intent(this, AddProductInActivity::class.java)
            startActivity(intent)
        }

        binding.btnStock.setOnClickListener {
            val intent = Intent(this, ListStockProductActivity::class.java)
            startActivity(intent)
        }

        binding.btnKategori.setOnClickListener {
            val intent = Intent(this, CategoryProductActivity::class.java)
            startActivity(intent)
        }

        binding.btnHistory.setOnClickListener {
            val intent = Intent(this, ProductHistoryActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setTopBar() {
        binding.topAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {

                R.id.action_notification -> {
                    startActivity(Intent(this@DashboardProduct, NotificationActivity::class.java))
                    true
                }

                R.id.action_logout -> {
                    startActivity(Intent(this@DashboardProduct, WelcomeActivity::class.java))
                    finish()
                    true
                }

                else -> false
            }
        }
    }
}