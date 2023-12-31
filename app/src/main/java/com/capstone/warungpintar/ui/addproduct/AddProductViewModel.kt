package com.capstone.warungpintar.ui.addproduct

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.warungpintar.data.ResultState
import com.capstone.warungpintar.data.remote.model.request.ProductRequest
import com.capstone.warungpintar.data.repository.ProductRepository
import kotlinx.coroutines.launch
import java.io.File

class AddProductViewModel(private val productRepository: ProductRepository) : ViewModel() {

    private var _resultUpload: MutableLiveData<ResultState<String>> = MutableLiveData()
    val resultUpload: LiveData<ResultState<String>> get() = _resultUpload


    private var _resultOCR: MutableLiveData<ResultState<String>> = MutableLiveData()
    val resultOCR: LiveData<ResultState<String>> get() = _resultOCR

    fun upload(imageFile: File, product: ProductRequest) {
        viewModelScope.launch {
            productRepository.postProduct(imageFile, product).collect { result ->
                _resultUpload.value = result
            }
        }
    }


    fun performOCR(imageFile: File) {
        viewModelScope.launch {
            productRepository.getExpiredFromOCR(imageFile).collect { result ->
                _resultOCR.value = result
            }
        }
    }
}