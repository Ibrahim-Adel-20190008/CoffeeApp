package com.example.finalproject.dataBase.remoteDB.api

import com.example.finalproject.dataBase.remoteDB.models.CoffeeItem
import com.example.finalproject.dataBase.remoteDB.models.LoginResponse
import com.example.finalproject.dataBase.remoteDB.models.User
import retrofit2.Response
import retrofit2.http.*

// https://reqres.in/api/login
interface ServicesApi {
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
        @Path("email") email: String?, @Query("fullName") fullName: String?,
        @Query("password") password: String?
    ): Response<Unit>

}
