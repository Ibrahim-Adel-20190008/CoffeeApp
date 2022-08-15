package com.example.finalproject.dataclasses

import com.google.gson.annotations.SerializedName

data class RegisterResponse(@SerializedName("id") val id:Int ,
                            @SerializedName("token") val token:String)
