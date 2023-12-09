package com.capstone.warungpintar.ui.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.warungpintar.data.ResultState
import com.capstone.warungpintar.data.remote.model.response.HistoryResponse
import com.capstone.warungpintar.data.repository.ProductRepository
import kotlinx.coroutines.launch

class ProductHistoryViewModel(private val productRepository: ProductRepository) : ViewModel() {

    private var _listHistory: MutableLiveData<ResultState<List<HistoryResponse>>> =
        MutableLiveData()
    val listHistory: LiveData<ResultState<List<HistoryResponse>>> get() = _listHistory

    fun getListHistory(email: String) {
        viewModelScope.launch {
            productRepository.getListHistories(email).collect { result ->
                _listHistory.value = result
            }
        }
    }
}