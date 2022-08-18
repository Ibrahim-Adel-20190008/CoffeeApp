package com.example.finalproject.dataClasses

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("email") val email: String? = null,
    @SerializedName("accessToken") val token: String? = null,
)