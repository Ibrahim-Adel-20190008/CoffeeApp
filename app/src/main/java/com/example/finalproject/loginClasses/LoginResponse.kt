package com.example.finalproject.loginClasses

import com.google.gson.annotations.SerializedName

data class LoginResponse(  @SerializedName("token") var token : String? =null )