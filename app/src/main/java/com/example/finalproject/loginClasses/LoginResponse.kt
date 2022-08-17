package com.example.finalproject.loginClasses

import com.google.gson.annotations.SerializedName

data class LoginResponse(  @SerializedName("email") val email : String? =null,
                           @SerializedName("accessToken") val token : String? =null, )