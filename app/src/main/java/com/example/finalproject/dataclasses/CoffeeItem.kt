package com.example.finalproject.dataclasses

import com.google.gson.annotations.SerializedName

data class CoffeeItem(@SerializedName("image"  ) var urlToImg  : String? = null,
                      @SerializedName("name"       ) var name   : String? = null,
                      @SerializedName("price"       ) var price   : Double? = null,
                      @SerializedName("next"       ) var next   : String = ">")
