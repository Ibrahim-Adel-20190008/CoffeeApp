package com.example.finalproject.localDataBase

import android.content.SharedPreferences


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
}