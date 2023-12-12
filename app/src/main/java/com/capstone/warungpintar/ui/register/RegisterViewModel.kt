package com.capstone.warungpintar.ui.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.warungpintar.data.ResultState
import com.capstone.warungpintar.data.remote.model.request.RegisterRequest
import com.capstone.warungpintar.data.repository.UserRepository
import kotlinx.coroutines.launch

class RegisterViewModel(private val userRepository: UserRepository) : ViewModel() {

    private var _registerResult: MutableLiveData<ResultState<String>> = MutableLiveData()
    val registerResult: LiveData<ResultState<String>> get() = _registerResult

    private var _registerFirebaseResult: MutableLiveData<ResultState<String>> = MutableLiveData()
    val registerFirebaseResult: LiveData<ResultState<String>> get() = _registerFirebaseResult

    fun register(registerRequest: RegisterRequest) {
        viewModelScope.launch {
            userRepository.register(registerRequest).collect {
                _registerResult.value = it
            }
        }
    }

    fun registerToFirebase(email: String, password: String) {
        viewModelScope.launch {
            userRepository.registerToFirebase(email, password).collect {
                _registerFirebaseResult.value = it
            }
        }
    }
}