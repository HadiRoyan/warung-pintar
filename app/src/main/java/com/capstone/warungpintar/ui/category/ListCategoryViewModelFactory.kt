package com.capstone.warungpintar.ui.category

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.capstone.warungpintar.data.repository.ProductRepository
import com.capstone.warungpintar.di.Injection

class ListCategoryViewModelFactory(private val productRepository: ProductRepository) :
    ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ListCategoryViewModel::class.java)) {
            return ListCategoryViewModel(productRepository) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

    companion object {
        @Volatile
        private var instance: ListCategoryViewModelFactory? = null

        @JvmStatic
        fun getInstance() =
            instance ?: synchronized(this) {
                instance ?: ListCategoryViewModelFactory(
                    Injection.provideProductRepository()
                )
            }.also { instance = it }
    }
}