package com.example.finalproject.api

import com.example.finalproject.dataClasses.CoffeeItem
import com.example.finalproject.dataClasses.LoginResponse
import com.example.finalproject.dataClasses.User
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

// https://reqres.in/api/login
interface loginApi {
    @POST("/api/authentication/login")
    suspend fun login(@Body user: User): Response<LoginResponse>

    //register fun
    @POST("/api/authentication/create")
    suspend fun register(@Body user: User): Response<Unit>

    @GET("/api/products/all")
   suspend fun getAllProducts(@Header("Authorization") accessToken: String): Response<ArrayList<CoffeeItem>>

    @GET("/api/user/byEmail/{email}")
    suspend fun getUser(
        @Header("Authorization") accessToken: String,
        @Path("email") email: String?
    ): Response<User>

    @GET("api/user/update/{email}")
    suspend fun editProfileInfo(
        @Header("Authorization") accessToken: String,
        @Path("email") email: String?, @Query("fullName") fullName: String,
        @Query("password") password: String
    ): Response<Unit>

}

val client = OkHttpClient.Builder()
    .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
    .build()

var retrofit: Retrofit = Retrofit.Builder()
    .baseUrl("https://coffee-menu123.herokuapp.com")
    .addConverterFactory(GsonConverterFactory.create())
    .client(client)
    .build()

var service: loginApi = retrofit.create(loginApi::class.java)