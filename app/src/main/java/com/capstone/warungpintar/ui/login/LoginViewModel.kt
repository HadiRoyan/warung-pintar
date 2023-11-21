package com.capstone.warungpintar.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.warungpintar.data.ResultState
import com.capstone.warungpintar.data.remote.model.response.LoginResponse
import com.capstone.warungpintar.data.repository.UserRepository
import kotlinx.coroutines.launch

class LoginViewModel(private val userRepository: UserRepository) : ViewModel() {

    private var _loginResult: MutableLiveData<ResultState<LoginResponse>> = MutableLiveData()
    val loginResult: LiveData<ResultState<LoginResponse>> get() = _loginResult

    fun login(email: String, password: String) {
        viewModelScope.launch {
            userRepository.login(email, password).collect {
                _loginResult.value = it
            }
        }
    }
}