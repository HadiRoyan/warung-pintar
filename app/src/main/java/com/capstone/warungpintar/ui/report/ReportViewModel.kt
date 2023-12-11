package com.capstone.warungpintar.ui.report

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.warungpintar.data.ResultState
import com.capstone.warungpintar.data.remote.model.response.ReportResponse
import com.capstone.warungpintar.data.repository.ProductRepository
import kotlinx.coroutines.launch

class ReportViewModel(private val productRepository: ProductRepository) : ViewModel() {

    private var _listReport: MutableLiveData<ResultState<List<ReportResponse>>> = MutableLiveData()
    val listReport: LiveData<ResultState<List<ReportResponse>>> get() = _listReport

    fun getListReport(email: String) {
        viewModelScope.launch {
            productRepository.getListReport(email).collect { result ->
                _listReport.value = result
            }
        }
    }
}