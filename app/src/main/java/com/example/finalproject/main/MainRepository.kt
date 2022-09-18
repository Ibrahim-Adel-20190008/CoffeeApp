package com.example.finalproject.main

import com.example.finalproject.data.models.CoffeeItem
import com.example.finalproject.data.models.LoginResponse
import com.example.finalproject.data.models.User
import com.example.finalproject.util.Resources
import retrofit2.Response
import retrofit2.http.*

interface MainRepository {
    suspend fun login(user: User): Resources<LoginResponse>

    suspend fun register( user: User): Resources<Unit>

    suspend fun getAllProducts(accessToken: String): Resources<ArrayList<CoffeeItem>>

    suspend fun getUser(accessToken: String, email: String?): Resources<User>

    suspend fun editProfileInfo(accessToken: String, email: String?,
                                fullName: String?,password: String?): Resources<Unit>
}