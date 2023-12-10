package com.capstone.warungpintar.ui.deleteproduct

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.capstone.warungpintar.data.repository.ProductRepository
import com.capstone.warungpintar.di.Injection

class DeleteProductViewModelFactory(private val productRepository: ProductRepository) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DeleteProductViewModel::class.java)) {
            return DeleteProductViewModel(productRepository) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

    companion object {
        @Volatile
        private var instance: DeleteProductViewModelFactory? = null

        @JvmStatic
        fun getInstance() =
            instance ?: synchronized(this) {
                instance ?: DeleteProductViewModelFactory(
                    Injection.provideProductRepository()
                )
            }.also { instance = it }
    }
}