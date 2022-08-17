package com.example.finalproject

import com.example.finalproject.dataclasses.RegisterResponse
import com.example.finalproject.loginClasses.LoginResponse
import com.example.finalproject.loginClasses.User
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST
// https://reqres.in/api/login
interface loginApi {
    @POST("/api/authentication/login")
    fun login(@Body user: User): Call<LoginResponse>

    //register fun
    @POST("/api/authentication/create")
    fun register(@Body user: User): Call<Unit>
}

var retrofit: Retrofit = Retrofit.Builder()
    .baseUrl("https://coffee-menu123.herokuapp.com")
    .addConverterFactory(GsonConverterFactory.create())
    .build()

var service: loginApi = retrofit.create(loginApi::class.java)