package com.example.finalproject.dataBase.localDB

import android.content.SharedPreferences
import com.google.gson.Gson
import kotlin.random.Random


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

    fun getUserCart(): SharedList {
        val gson = Gson()
        val json: String? = sharedPre?.getString(getEmail(),gson.toJson(SharedList()))
        return gson.fromJson(json, SharedList::class.java)
    }

    fun getUrl(): String? {
        if(sharedPre?.getString("urlToImg ${getEmail()}", null) == null)
            setUrl()
        return sharedPre?.getString("urlToImg ${getEmail()}", null)
    }

    fun setUrl(){
        // https://avatars.dicebear.com/api/:sprites/:seed.svg
        // Replace :sprites with male, female, human, identicon, initials, bottts, avataaars, jdenticon, gridy or micah.
        // The value of :seed can be anything you like - but don't use any sensitive or personal data here!
        //val imgNumber : Int = Random.nextInt(-100000,100000)
       // val category = "avataaars"
        val urlToImg = "https://xsgames.co/randomusers/avatar.php?g=male"
        sharedPre?.edit()?.putString("urlToImg ${getEmail()}",urlToImg)?.apply()
    }

    fun setPassword(password: String) {
        sharedPre?.edit()?.putString("password ${getEmail()}",password)?.apply()
    }

    fun getPassword():String?{
        return sharedPre?.getString("password ${getEmail()}",null)
    }

}