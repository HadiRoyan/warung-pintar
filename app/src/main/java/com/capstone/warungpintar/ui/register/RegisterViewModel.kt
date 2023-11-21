package com.capstone.warungpintar.ui.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.warungpintar.data.ResultState
import com.capstone.warungpintar.data.repository.UserRepository
import kotlinx.coroutines.launch

class RegisterViewModel(private val userRepository: UserRepository) : ViewModel() {

    private var _registerResult: MutableLiveData<ResultState<String>> = MutableLiveData()
    val registerResult: LiveData<ResultState<String>> get() = _registerResult

    fun register(name: String, email: String, password: String) {
        viewModelScope.launch {
            userRepository.register(name, email, password).collect {
                _registerResult.value = it
            }
        }
    }
}