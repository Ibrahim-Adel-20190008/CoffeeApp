package com.example.finalproject.domain

import com.example.finalproject.dataBase.remoteDB.api.ServicesApi
import com.example.finalproject.dataBase.remoteDB.models.CoffeeItem
import com.example.finalproject.dataBase.remoteDB.models.LoginResponse
import com.example.finalproject.dataBase.remoteDB.models.User
import com.example.finalproject.dataBase.localDB.SharedList
import com.example.finalproject.dataBase.localDB.SharedPre
import com.example.finalproject.ui.util.Resources
import java.lang.Exception
import javax.inject.Inject

class RepositoryImp @Inject constructor(
    private val api:ServicesApi
) : Repository {
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

    override suspend fun setToken(token: String) {
        SharedPre.setText(token)
    }


    override suspend fun setEmail(email: String?) {
        SharedPre.setEmail(email)
    }


    override suspend fun setUserCart(newUser: SharedList) {
        SharedPre.setUserCart(newUser)
    }

    override suspend fun setUrl() {
        SharedPre.setUrl()
    }

    override suspend fun setPassword(password: String) {
        SharedPre.setPassword(password)
    }


}