package com.capstone.warungpintar.ui.detailproduct

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.capstone.warungpintar.R
import com.capstone.warungpintar.data.ResultState
import com.capstone.warungpintar.data.model.Product
import com.capstone.warungpintar.databinding.ActivityDetailProductBinding

class DetailProductActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailProductBinding

    private val viewModel: DetailViewModel by viewModels {
        DetailViewModelFactory.getInstance()
    }

    companion object {
        private const val TAG = "DetailProductActivity"

        const val EXTRA_PRODUCT_DETAIL = "extra_product_detail"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailProductBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.statusBarColor = getColor(R.color.light_blue)
        setTopBar()

        val product = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra<Product>(
                EXTRA_PRODUCT_DETAIL,
                Product::class.java
            )
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra<Product>(EXTRA_PRODUCT_DETAIL)
        }

        if (product != null) {
            viewModel.getDetailProductById(product.productId)
        } else {
            // Cannot display detail product
            Log.d(TAG, "onCreate: failed to display detail product because product is null")
        }

        viewModel.resultDetail.observe(this) { result ->
            if (result != null) {
                when (result) {
                    is ResultState.Loading -> {
                        // TODO: create loading animation
                    }

                    is ResultState.Success -> {
                        val data = result.data
                        setDetailView(data)
                    }

                    is ResultState.Error -> {
                        // TODO: handle actions when errors occur
                        product?.let { setDetailView(it) }
                    }
                }
            }
        }

    }

    private fun setDetailView(product: Product) {
        with(binding) {
            tvNameProduct.text = product.productName
            tvBuyPrice.text = "Rp.${product.purchasePrice}"
            tvSalePrice.text = "Rp.${product.sellingPrice}"
            tvStockProduct.text = product.productQuantity.toString()

            tvEntryDate.text = product.entryDate
            tvProductCategory.text = product.productCategory
            tvStockCode.text = product.codeStock
            tvExpiredDate.text = product.expiredDate

            Glide.with(this@DetailProductActivity)
                .load(product.imageUrl)
                .into(binding.imgDetailProduct)
        }
    }

    private fun setTopBar() {
        binding.topAppBar.setNavigationOnClickListener { _ ->
            onBackPressedDispatcher.onBackPressed()
        }
    }
}