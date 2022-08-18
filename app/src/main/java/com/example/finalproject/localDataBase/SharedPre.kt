package com.example.finalproject.localDataBase

import android.content.SharedPreferences
import com.google.gson.Gson


object SharedPre {
    var sharedPre: SharedPreferences? = null
    fun setText(text: String?) {
        sharedPre?.edit()?.putString("token", text)?.apply()
    }

    fun getText(): String? {
        return sharedPre?.getString("token", null)
    }

    fun setEmail(email: String?) {
        sharedPre?.edit()?.putString("Email", email)?.apply()
    }

    fun getEmail(): String? {
        return sharedPre?.getString("Email", null)
    }
    fun setUserCart(newUser:SharedList){
        val gson = Gson()
        val json = gson.toJson(newUser)
        sharedPre?.edit()?.putString(getEmail(),json)?.apply()
    }
    fun getUserCart(): SharedList? {
        val gson = Gson()
        val json: String? = sharedPre?.getString(getEmail(),gson.toJson(SharedList()))
        return gson.fromJson(json, SharedList::class.java)
    }
}