package com.capstone.warungpintar.ui.listproductout

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.warungpintar.data.ResultState
import com.capstone.warungpintar.databinding.ActivityListProductOutBinding
import com.capstone.warungpintar.ui.welcoming.WelcomeActivity
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class ListProductOutActivity : AppCompatActivity() {

    private lateinit var binding: ActivityListProductOutBinding
    private lateinit var adapter: ListProductOutAdapter

    private lateinit var auth: FirebaseAuth
    private var email = ""

    private val viewModel: ProductsOutViewModel by viewModels {
        ProductsOutViewModelFactory.getInstance()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListProductOutBinding.inflate(layoutInflater)
        setContentView(binding.root)
        adapter = ListProductOutAdapter()
        auth = Firebase.auth
        email = auth.currentUser?.email ?: ""

        with(binding) {
            // setup top bar
            topAppBar.setNavigationOnClickListener {
                onBackPressedDispatcher.onBackPressed()
            }

            // setup recycler view
            listItem.layoutManager = LinearLayoutManager(this@ListProductOutActivity)
            listItem.adapter = adapter
        }

        if (email.isNotEmpty()) {
            viewModel.getListProductOut(email)
        } else {
            Toast.makeText(this, "Sesi anda telah habis", Toast.LENGTH_SHORT).show()
            auth.signOut()
            startActivity(Intent(this@ListProductOutActivity, WelcomeActivity::class.java))
            finish()
        }

        viewModel.listProductOut.observe(this) { result ->
            if (result != null) {
                when (result) {
                    is ResultState.Loading -> {
                        showLoading(true)
                    }

                    is ResultState.Success -> {
                        val data = result.data
                        adapter.submitList(data)
                        showLoading(false)
                    }

                    is ResultState.Error -> {
                        showLoading(false)
                        showMessage("Terjadi kegagalan, coba lagi nanti")
                        Log.d(TAG, "onCreate: error fetch data from API: ${result.error}")
                    }
                }
            }
        }

    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun showMessage(message: String) {
        Toast.makeText(this@ListProductOutActivity, message, Toast.LENGTH_SHORT).show()
    }

    companion object {
        private const val TAG = "ListProductOutActivity"
    }
}