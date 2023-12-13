package com.capstone.warungpintar.ui.dashboard

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.capstone.warungpintar.R
import com.capstone.warungpintar.data.ResultState
import com.capstone.warungpintar.data.remote.model.response.DashboardResponse
import com.capstone.warungpintar.databinding.ActivityDashboardProductBinding
import com.capstone.warungpintar.ui.addproduct.AddProductInActivity
import com.capstone.warungpintar.ui.category.CategoryProductActivity
import com.capstone.warungpintar.ui.deleteproduct.DeleteProductOutActivity
import com.capstone.warungpintar.ui.history.ProductHistoryActivity
import com.capstone.warungpintar.ui.liststockproduct.ListStockProductActivity
import com.capstone.warungpintar.ui.notification.NotificationActivity
import com.capstone.warungpintar.ui.report.ReportActivity
import com.capstone.warungpintar.ui.welcoming.WelcomeActivity
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class DashboardProduct : AppCompatActivity() {
    private lateinit var binding: ActivityDashboardProductBinding
    private lateinit var auth: FirebaseAuth

    private val viewModel: DashboardViewModel by viewModels {
        DashboardViewModelFactory.getInstance()
    }

    // TODO: use the email of the currently logged-in user
    private var email = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardProductBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setTopBarAction()
        setupAction()
        auth = Firebase.auth
        email = auth.currentUser?.email ?: ""

        if (email.isNotEmpty()) {
            viewModel.getDashboardUser(email)
            binding.topAppBar.subtitle = email
        } else {
            Toast.makeText(this, "Something failed", Toast.LENGTH_SHORT).show()
            Log.d(TAG, "onCreate: email is null or empty, cannot get dashboard data")
            signOut()
        }

        viewModel.resultRequest.observe(this) { result ->
            if (result != null) {
                when (result) {
                    is ResultState.Loading -> {
                        showLoading(true)
                    }

                    is ResultState.Success -> {
                        val data = result.data
                        setDataView(data)
                        showLoading(false)
                    }

                    is ResultState.Error -> {
                        showLoading(false)
                        Toast.makeText(this, result.error, Toast.LENGTH_SHORT).show()
                        Log.d(TAG, "onCreate: error fetch data from API: ${result.error}")
                    }
                }
            }
        }
    }

    private fun setDataView(data: DashboardResponse) {
        with(binding) {
            // store data
            topAppBar.title = data.storeData.storeName

            // stock data
            tvBarangmasuk.text = data.stockData.entryProduct.toString()
            tvBarangkeluar.text = data.stockData.exitProduct.toString()
            tvProduk.text = data.stockData.product.toString()
            tvLowstock.text = data.stockData.lowStock.toString()
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun setTopBarAction() {
        binding.topAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {

                R.id.action_notification -> {
                    startActivity(Intent(this@DashboardProduct, NotificationActivity::class.java))
                    true
                }

                R.id.action_logout -> {
                    showLogoutDialog()
                    true
                }

                else -> false
            }
        }
    }

    private fun signOut() {
        auth.signOut()
        startActivity(Intent(this@DashboardProduct, WelcomeActivity::class.java))
        finish()
    }

    private fun showLogoutDialog() {
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.setTitle("Keluar?")
            .setMessage("Apakah anda yakin ingin keluar dari aplikasi")
            .setPositiveButton("YES") { _, _ ->
                signOut()
            }
            .setNegativeButton("NO") { dialog, _ ->
                dialog.dismiss()
            }
        alertDialog.create().show()
    }

    private fun setupAction() {
        binding.btnBarangmasuk.setOnClickListener {
            val intent = Intent(this, AddProductInActivity::class.java)
            startActivity(intent)
        }

        binding.btnBarangkeluar.setOnClickListener {
            val intent = Intent(this, DeleteProductOutActivity::class.java)
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

        binding.btnLaporan.setOnClickListener {
            val intent = Intent(this, ReportActivity::class.java)
            startActivity(intent)
        }
    }

    companion object {
        private const val TAG = "DashboardProduct"
    }
}