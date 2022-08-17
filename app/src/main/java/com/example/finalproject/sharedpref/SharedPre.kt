package com.example.finalproject.sharedpref

import android.content.SharedPreferences
import com.example.finalproject.loginClasses.User
import com.google.gson.Gson


object SharedPre {
    var sharedPre : SharedPreferences? = null
    fun setText(text:String?)
    {
        sharedPre?.edit()?.putString("token",text)?.apply()
    }
    fun getText():String?
    {
        return sharedPre?.getString("token",null)
    }
    fun setEmail(email:String?)
    {
        sharedPre?.edit()?.putString("Email",email)?.apply()
    }
    fun getEmail():String?
    {
        return sharedPre?.getString("Email",null)
    }
//    fun setUser(newUser:User){
//        val gson = Gson()
//        val json = gson.toJson(newUser)
//        sharedPre?.edit()?.putString("User",json)?.apply()
//    }
//    fun getUser(): User? {
//        val gson = Gson()
//        val json: String? = sharedPre?.getString("User", "")
//        return gson.fromJson(json, User::class.java)
//    }
}