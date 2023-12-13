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

    private var _loginFirebaseResult: MutableLiveData<ResultState<String>> = MutableLiveData()
    val loginFirebaseResult: LiveData<ResultState<String>> get() = _loginFirebaseResult

    fun login(email: String, password: String) {
        viewModelScope.launch {
            userRepository.login(email, password).collect {
                _loginResult.value = it
            }
        }
    }

    fun loginWithFirebase(email: String, password: String) {
        viewModelScope.launch {
            userRepository.loginWithFirebase(email, password).collect {
                _loginFirebaseResult.value = it
            }
        }
    }
}