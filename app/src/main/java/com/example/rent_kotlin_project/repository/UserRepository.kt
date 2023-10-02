package com.example.rent_kotlin_project.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.rent_kotlin_project.api.UserApi
import com.example.rent_kotlin_project.models.Login.LoginRequest
import com.example.rent_kotlin_project.models.Login.LoginResponse
import com.example.rent_kotlin_project.models.otp.OtpRequest
import com.example.rent_kotlin_project.models.otp.OtpResponse
import com.example.rent_kotlin_project.utils.NetworkResult

import org.json.JSONObject
import retrofit2.Response
import javax.inject.Inject

class UserRepository @Inject constructor(private val userapi: UserApi) {

    private val _userResponselivedata = MutableLiveData<NetworkResult<LoginResponse>>()
    val userResponselivedata: LiveData<NetworkResult<LoginResponse>>
        get() = _userResponselivedata

    private val _otpResposnelivedata = MutableLiveData<NetworkResult<OtpResponse>>()
    val otpResponselivedata: LiveData<NetworkResult<OtpResponse>>
        get() = _otpResposnelivedata


    suspend fun loginuser(loginRequest: LoginRequest) {
        _userResponselivedata.postValue(NetworkResult.Loading())
        val response = userapi.getlogin(loginRequest)
        handleresponse(response)
    }

//    suspend fun otpuser(otpRequest: OtpRequest) {
//        _otpResposnelivedata.postValue(NetworkResult.Loading())
//        val response = userapi.getotp(otpRequest)
//        otphandleResposne(response)
//    }

//    private fun otphandleResposne(response: Response<OtpResponse>) {
//        if (response.isSuccessful && response.body() != null) {
//            _otpResposnelivedata.postValue(NetworkResult.Success(response.body()!!))
//        } else if (response.errorBody() != null) {
//            val errorObj = JSONObject(response.errorBody()!!.charStream().readText())
//            _otpResposnelivedata.postValue(NetworkResult.Error(errorObj.getString("message")))
//        } else {
//            _otpResposnelivedata.postValue(NetworkResult.Error("Something Went Wrong"))
//        }
//    }

    private fun handleresponse(response: Response<LoginResponse>) {
        if (response.isSuccessful && response.body() != null) {
            _userResponselivedata.postValue(NetworkResult.Success(response.body()!!))
        } else if (response.errorBody() != null) {
            val errorObj = JSONObject(response.errorBody()!!.charStream().readText())
            _userResponselivedata.postValue(NetworkResult.Error(errorObj.getString("message")))
        } else {
            _userResponselivedata.postValue(NetworkResult.Error("Something Went Wrong"))
        }

    }

}