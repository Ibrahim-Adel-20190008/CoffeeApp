package com.example.finalproject.dataclasses

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class CoffeeItem(@SerializedName("image"  ) var urlToImg  : String? = null,
                      @SerializedName("name"       ) var name   : String? = null,
                      @SerializedName("price"       ) var price   : Double? = null,
                      @SerializedName("description"       ) var description   : String? = null,
                      @SerializedName("id"       ) var id   : Long? = null): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Double::class.java.classLoader) as? Double,
        parcel.readString(),
        parcel.readValue(Long::class.java.classLoader) as? Long
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(urlToImg)
        parcel.writeString(name)
        parcel.writeValue(price)
        parcel.writeString(description)
        parcel.writeValue(id)
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