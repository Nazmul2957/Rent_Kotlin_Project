package com.example.rent_kotlin_project.models.otp

data class OtpResponse(
    val message: String,
    val sms_api: SmsApi,
    val status: String
)