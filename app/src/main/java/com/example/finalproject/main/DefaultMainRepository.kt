package com.example.finalproject.main

import com.example.finalproject.data.api.ServicesApi
import com.example.finalproject.data.models.CoffeeItem
import com.example.finalproject.data.models.LoginResponse
import com.example.finalproject.data.models.User
import com.example.finalproject.util.Resources
import java.lang.Exception
import javax.inject.Inject

class DefaultMainRepository @Inject constructor(
    private val api:ServicesApi
) : MainRepository{
    override suspend fun login(user: User): Resources<LoginResponse> {
        return try {
            val response = api.login(user)
            val result = response.body()
            if(response.isSuccessful && result != null){
                Resources.Success(result)
            }
            else{
                Resources.Error(response.message())
            }
        }
        catch (e: Exception){
            Resources.Error(e.message?:"An Error Here")
        }
    }

    override suspend fun register(user: User): Resources<Unit> {
        return try {
            val response = api.register(user)
            val result = response.body()
            if(response.isSuccessful && result != null){
                Resources.Success(result)
            }
            else{
                Resources.Error(response.message())
            }
        }
        catch (e: Exception){
            Resources.Error(e.message?:"An Error Here")
        }
    }

    override suspend fun getAllProducts(accessToken: String): Resources<ArrayList<CoffeeItem>> {
        return try {
            val response = api.getAllProducts(accessToken)
            val result = response.body()
            if(response.isSuccessful && result != null){
                Resources.Success(result)
            }
            else{
                Resources.Error(response.message())
            }
        }
        catch (e: Exception){
            Resources.Error(e.message?:"An Error Here")
        }
    }

    override suspend fun getUser(accessToken: String, email: String?): Resources<User> {
        return try {
            val response = api.getUser(accessToken,email)
            val result = response.body()
            if(response.isSuccessful && result != null){
                Resources.Success(result)
            }
            else{
                Resources.Error(response.message())
            }
        }
        catch (e: Exception){
            Resources.Error(e.message?:"An Error Here")
        }
    }

    override suspend fun editProfileInfo(
        accessToken: String,
        email: String?,
        fullName: String?,
        password: String?
    ): Resources<Unit> {
        return try {
            val response = api.editProfileInfo(accessToken,email,fullName,password)
            val result = response.body()
            if(response.isSuccessful && result != null){
                Resources.Success(result)
            }
            else{
                Resources.Error(response.message())
            }
        }
        catch (e: Exception){
            Resources.Error(e.message?:"An Error Here")
        }
    }
}