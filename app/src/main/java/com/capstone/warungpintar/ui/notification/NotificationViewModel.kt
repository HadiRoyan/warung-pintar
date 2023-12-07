package com.capstone.warungpintar.ui.notification

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.warungpintar.data.ResultState
import com.capstone.warungpintar.data.remote.model.response.NotificationResponse
import com.capstone.warungpintar.data.repository.NotificationRepository
import kotlinx.coroutines.launch

class NotificationViewModel(
    private val notificationRepository: NotificationRepository
) : ViewModel() {

    private var _requestResult: MutableLiveData<ResultState<List<NotificationResponse>>> =
        MutableLiveData()
    val requestResult: LiveData<ResultState<List<NotificationResponse>>> get() = _requestResult

    fun getListNotification(email: String) {
        viewModelScope.launch {
            notificationRepository.getListNotification(email).collect {
                _requestResult.value = it
            }
        }
    }
}