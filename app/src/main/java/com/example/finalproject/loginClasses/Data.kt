package com.example.finalproject.loginClasses


import com.google.gson.annotations.SerializedName


data class Data (

    @SerializedName("id"            ) var id           : Int?    = null,
    @SerializedName("name"          ) var name         : String? = null,
    @SerializedName("year"          ) var year         : Int?    = null,
    @SerializedName("color"         ) var color        : String? = null,
    @SerializedName("pantone_value" ) var pantoneValue : String? = null

)