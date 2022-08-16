package com.example.finalproject.dataclasses

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class CoffeeItem(@SerializedName("image"  ) var urlToImg  : String? = null,
                      @SerializedName("name"       ) var name   : String? = null,
                      @SerializedName("price"       ) var price   : Double? = null,
                      @SerializedName("next"       ) var next   : String = ">"): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Double::class.java.classLoader) as? Double,
        parcel.readString().toString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(urlToImg)
        parcel.writeString(name)
        parcel.writeValue(price)
        parcel.writeString(next)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CoffeeItem> {
        override fun createFromParcel(parcel: Parcel): CoffeeItem {
            return CoffeeItem(parcel)
        }

        override fun newArray(size: Int): Array<CoffeeItem?> {
            return arrayOfNulls(size)
        }
    }
}
