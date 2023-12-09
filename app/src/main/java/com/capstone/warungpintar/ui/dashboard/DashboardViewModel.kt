package com.capstone.warungpintar.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.warungpintar.data.ResultState
import com.capstone.warungpintar.data.remote.model.response.DashboardResponse
import com.capstone.warungpintar.data.repository.DashboardRepository
import kotlinx.coroutines.launch

class DashboardViewModel(private val dashboardRepository: DashboardRepository) : ViewModel() {

    private var _resultRequest: MutableLiveData<ResultState<DashboardResponse>> = MutableLiveData()
    val resultRequest: LiveData<ResultState<DashboardResponse>> get() = _resultRequest

    fun getDashboardUser(email: String) {
        viewModelScope.launch {
            dashboardRepository.getDashboardUser(email).collect { result ->
                _resultRequest.value = result
            }
        }
    }
}