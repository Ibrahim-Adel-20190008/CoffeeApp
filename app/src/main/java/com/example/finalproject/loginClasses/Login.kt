package com.example.finalproject.loginClasses
import com.google.gson.annotations.SerializedName


data class Login (

    @SerializedName("page"        ) var page       : Int?            = null,
    @SerializedName("per_page"    ) var perPage    : Int?            = null,
    @SerializedName("total"       ) var total      : Int?            = null,
    @SerializedName("total_pages" ) var totalPages : Int?            = null,
    @SerializedName("data"        ) var data       : ArrayList<Data> = arrayListOf(),
    @SerializedName("support"     ) var support    : Support?        = Support()

)