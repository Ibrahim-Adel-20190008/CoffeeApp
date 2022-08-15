package com.example.finalproject.sharedpref

import android.content.SharedPreferences

object SharedPre {
    var sharedPre : SharedPreferences? =null

    fun setText(text:String)
    {
        sharedPre?.edit()?.putString("token",text)
    }
    fun getText():String?
    {
        return sharedPre?.getString("token",null)
    }

}