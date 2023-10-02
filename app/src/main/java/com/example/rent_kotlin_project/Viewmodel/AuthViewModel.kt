package com.example.rent_kotlin_project.Viewmodel

import android.text.TextUtils
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rent_kotlin_project.models.Login.LoginRequest
import com.example.rent_kotlin_project.models.Login.LoginResponse
import com.example.rent_kotlin_project.repository.UserRepository
import com.example.rent_kotlin_project.utils.Helper
import com.example.rent_kotlin_project.utils.NetworkResult
import dagger.Provides
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(private val userRepository: UserRepository) : ViewModel() {

    val userResponseLivedata: LiveData<NetworkResult<LoginResponse>>
        get() = userRepository.userResponselivedata

    fun loginuser(loginRequest: LoginRequest) {
        viewModelScope.launch {
            userRepository.loginuser(loginRequest)
        }
    }


    fun validedfield(mobile: String, password: String): Pair<Boolean, String> {
        var result = Pair(true, "")
        if (TextUtils.isEmpty(mobile) || TextUtils.isEmpty(password)) {
            result = Pair(false, "Please provide the credentials")
        } else if (!Helper.isValidmobile(mobile)) {
            result = Pair(false, "Mobile is invalid")
        } else if (!TextUtils.isEmpty(password) && password.length <= 6) {
            result = Pair(false, "Password length should be greater than 6")
        }
        return result
    }
}