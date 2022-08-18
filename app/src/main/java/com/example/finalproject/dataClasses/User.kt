package com.example.finalproject.dataClasses

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("fullName") val username: String? = null,
    @SerializedName("password") val password: String? = null,
    @SerializedName("email") val email: String? = null
)