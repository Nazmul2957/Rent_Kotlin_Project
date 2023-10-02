package com.example.rent_kotlin_project.models.otp

import com.example.rent_kotlin_project.models.otp.Id

data class SmsApi(
    val cost: Double,
    val error_code: Int,
    val ids: List<Id>,
    val message: String,
    val sms_count: Int,
    val status: Boolean
)