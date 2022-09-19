package com.example.finalproject.domain

import com.example.finalproject.dataBase.remoteDB.models.CoffeeItem
import com.example.finalproject.dataBase.remoteDB.models.LoginResponse
import com.example.finalproject.dataBase.remoteDB.models.User
import com.example.finalproject.dataBase.localDB.SharedList
import com.example.finalproject.ui.util.Resources

interface Repository {
    // remote api
    suspend fun login(user: User): Resources<LoginResponse>

    suspend fun register( user: User): Resources<Unit>

    suspend fun getAllProducts(accessToken: String): Resources<ArrayList<CoffeeItem>>

    suspend fun getUser(accessToken: String, email: String?): Resources<User>

    suspend fun editProfileInfo(accessToken: String, email: String?,
                                fullName: String?,password: String?): Resources<Unit>
    // local db
    suspend fun setToken(token:String)

    suspend fun setEmail(email: String?)

    suspend fun setUserCart(newUser: SharedList)

    suspend fun setUrl()

    suspend fun setPassword(password: String)

//    suspend fun getEmail(): String?

//    suspend fun getUserCart(): SharedList

//    suspend fun getUrl(): String?

//    suspend fun getPassword(): String?
//    suspend fun getToken(): String?

}