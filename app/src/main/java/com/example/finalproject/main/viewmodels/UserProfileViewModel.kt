package com.example.finalproject.main.viewmodels

import android.util.Log
import androidx.fragment.app.FragmentActivity
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import com.example.finalproject.activities.CoffeeActivity
import com.example.finalproject.data.api.ServicesApi
import com.example.finalproject.data.models.User
import com.example.finalproject.localDataBase.SharedPre
import com.example.finalproject.main.MainRepository
import com.example.finalproject.util.Resources
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UserProfileViewModel @ViewModelInject constructor(
    private val repo: MainRepository
) : ViewModel(){

    fun getUserInfoApi(activity: FragmentActivity) {
        (activity as CoffeeActivity?)?.let {
            if (it.getCurrentUser() == null) {
                viewModelScope.launch(Dispatchers.IO) {
                    when (val response =
                        repo.getUser("Bearer ${SharedPre.getText()}", SharedPre.getEmail())) {
                        is Resources.Success -> {
                            val email = response.data?.email
                            val fullName = response.data?.username
                            val password = response.data?.password
                            withContext(Dispatchers.Main) {
                                it.setCurrentUser(User(fullName, password, email))
                            }
                        }
                        is Resources.Error -> {
                            Log.v("401 ", "onResponse ${response.msg}")
                        }
                    }
                }
            }
        }
    }



}