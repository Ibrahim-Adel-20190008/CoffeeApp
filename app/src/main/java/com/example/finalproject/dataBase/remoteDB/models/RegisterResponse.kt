package com.example.finalproject.dataBase.remoteDB.models

import com.google.gson.annotations.SerializedName

data class RegisterResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("token") val token: String
)
