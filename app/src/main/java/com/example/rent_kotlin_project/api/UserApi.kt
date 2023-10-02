package com.example.rent_kotlin_project.api

import com.example.rent_kotlin_project.models.Login.LoginRequest
import com.example.rent_kotlin_project.models.Login.LoginResponse
import com.example.rent_kotlin_project.models.otp.OtpRequest
import com.example.rent_kotlin_project.models.otp.OtpResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface UserApi {

    @POST("otp")
    suspend fun getotp(@Body otpRequest: OtpRequest):Response<OtpResponse>

    @POST("login")
    suspend fun getlogin(@Body loginRequest: LoginRequest):Response<LoginResponse>
}