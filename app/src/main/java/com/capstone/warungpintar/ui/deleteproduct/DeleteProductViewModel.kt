package com.capstone.warungpintar.ui.deleteproduct

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.warungpintar.data.ResultState
import com.capstone.warungpintar.data.remote.model.request.DeleteProductRequest
import com.capstone.warungpintar.data.repository.ProductRepository
import kotlinx.coroutines.launch

class DeleteProductViewModel(private val productRepository: ProductRepository) : ViewModel() {

    private var _resultDelete: MutableLiveData<ResultState<String>> = MutableLiveData()
    val resultDelete: LiveData<ResultState<String>> get() = _resultDelete

    fun deleteProduct(email: String, productName: String, data: DeleteProductRequest) {
        viewModelScope.launch {
            productRepository.deleteProduct(email, productName, data).collect { result ->
                _resultDelete.value = result
            }
        }
    }
}