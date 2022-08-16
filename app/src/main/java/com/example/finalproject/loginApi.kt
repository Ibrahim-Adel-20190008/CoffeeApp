package com.example.finalproject

import com.example.finalproject.dataclasses.RegisterResponse
import com.example.finalproject.loginClasses.Login
import com.example.finalproject.loginClasses.LoginResponse
import com.example.finalproject.loginClasses.User
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

// https://reqres.in/api/login
interface loginApi {
    @POST("api/login")
    fun Login(@Query("email") email: String,
              @Query("password") password: String):
              Call<Login?>?

    @POST("api/register")
    fun register(@Body user: User): Call<RegisterResponse>
}

var retrofit: Retrofit = Retrofit.Builder()
    .baseUrl("https://reqres.in/")
    .addConverterFactory(GsonConverterFactory.create())
    .build()

var service: loginApi = retrofit.create(loginApi::class.java)