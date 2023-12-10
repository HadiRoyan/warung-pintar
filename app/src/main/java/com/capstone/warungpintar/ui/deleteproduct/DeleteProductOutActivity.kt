package com.capstone.warungpintar.ui.deleteproduct

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.capstone.warungpintar.data.ResultState
import com.capstone.warungpintar.data.remote.model.request.DeleteProductRequest
import com.capstone.warungpintar.databinding.ActivityDeleteProductOutBinding
import com.capstone.warungpintar.utils.Validation

class DeleteProductOutActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDeleteProductOutBinding
    private var email = ""

    private val viewModel: DeleteProductViewModel by viewModels {
        DeleteProductViewModelFactory.getInstance()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDeleteProductOutBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupActions()

        viewModel.resultDelete.observe(this) { result ->
            if (result != null) {
                when (result) {
                    is ResultState.Loading -> {
                        showLoading(true)
                    }

                    is ResultState.Success -> {
                        val data = result.data
                        showLoading(false)
                        showMessage(data)
                        finish()
                    }

                    is ResultState.Error -> {
                        // TODO: handle actions when errors occur
                        Log.d(TAG, "result delete: error fetch data from API: ${result.error}")
                        showMessage(result.error)
                    }
                }
            }
        }
    }

    private fun setupActions() {
        binding.btnAddproductClose.setOnClickListener { _ ->
            onBackPressedDispatcher.onBackPressed()
        }

        binding.btnDeleteProduct.setOnClickListener { _ ->
            if (validate() && email.isNotEmpty()) {
                deleteProduct()
            } else if (validate() && email.isEmpty()) {
                showMessage("Something wrong, maybe the session is ended")
            } else {
                showMessage("Data tidak boleh ada yang kosong")
            }
        }
    }

    private fun deleteProduct() {
        with(binding) {
            val productName = namabaranglEditText.text.toString().trim()
            val exitDate = tglkeluarEditText.text.toString().trim()
            val quantity = jmlmasuklEditText.text.toString().trim().toInt()
            val sellingPrice = hargajualEditText.text.toString().trim().toInt()

            val data = DeleteProductRequest(productName, exitDate, quantity, sellingPrice)
            viewModel.deleteProduct(email, productName, data)
        }
    }

    private fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun validate(): Boolean {
        with(binding) {
            val isValidProductName = Validation.validateIsNotEmpty(
                "Barang",
                namabarangEditTextLayout,
                namabaranglEditText
            )

            val isValidExitDate = Validation.validateIsNotEmpty(
                "Tanggal Keluar",
                tglkeluarEditTextLayout,
                tglkeluarEditText
            )

            val isValidQuantity = Validation.validateIsNotEmpty(
                "Jumlah Barang",
                jmlmasukEditTextLayout,
                jmlmasuklEditText
            )

            val isValidPrice = Validation.validateIsNotEmpty(
                "Harga jual",
                hargajualEditTextLayout,
                hargajualEditText
            )

            return isValidProductName && isValidExitDate && isValidQuantity && isValidPrice
        }
    }

    companion object {
        private const val TAG = "DeleteProductOutActivit"
    }
}