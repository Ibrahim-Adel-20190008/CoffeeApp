package com.example.finalproject.sharedpref

import android.app.Application
import android.content.SharedPreferences
import com.example.finalproject.loginClasses.User

object SharedPre {
    var sharedPre : SharedPreferences? = null
    private var currentUser : User = User()

    fun setText(text:String?)
    {
        sharedPre?.edit()?.putString("token",text)?.apply()
    }
    fun getText():String?
    {
        return sharedPre?.getString("token",null)
    }
    fun setUser(newUser: User)
    {
        currentUser = newUser
    }
    fun getUser(): User?{
        return currentUser
    }
}