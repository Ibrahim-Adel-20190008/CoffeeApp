package com.example.finalproject.sharedpref

import android.app.Application
import android.content.SharedPreferences

object SharedPre {
    var sharedPre : SharedPreferences? = null
    fun setText(text:String)
    {
        sharedPre?.edit()?.putString("token",text)?.apply()
    }
    fun getText():String?
    {
        return sharedPre?.getString("token",null)
    }

}