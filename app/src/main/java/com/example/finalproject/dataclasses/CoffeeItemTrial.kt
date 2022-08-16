package com.example.finalproject.dataclasses

import android.icu.text.CaseMap
import com.google.gson.annotations.SerializedName

data class CoffeeItemTrial(@SerializedName("image"  ) var urlToImg  : String? = "https://picsum.photos/100/100",
                           @SerializedName("name"       ) var title : String? = "Coffee",
                           @SerializedName("price"       ) var quantity  : String? = "1",
                           @SerializedName("price"       ) var price   : String? = "10.0")
