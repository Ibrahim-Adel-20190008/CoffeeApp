package com.example.finalproject.loginClasses

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("email") val email:String,
    @SerializedName("password") val password:String
)