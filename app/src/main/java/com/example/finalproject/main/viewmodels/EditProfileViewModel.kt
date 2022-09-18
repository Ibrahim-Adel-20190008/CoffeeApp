package com.example.finalproject.main.viewmodels

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import com.example.finalproject.R
import com.example.finalproject.activities.CoffeeActivity
import com.example.finalproject.data.models.User
import com.example.finalproject.databinding.FragmentEditProfileBinding
import com.example.finalproject.fragment.UserProfileFragment
import com.example.finalproject.localDataBase.SharedPre
import com.example.finalproject.main.MainRepository
import com.example.finalproject.util.Resources
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EditProfileViewModel @ViewModelInject constructor(
    private val repo : MainRepository
) : ViewModel(){

    fun confirmChange(username:String,password:String,activity:Context):Boolean {
        var result = false
        viewModelScope.launch(Dispatchers.IO) {
            when (val response = repo.editProfileInfo(
                "Bearer ${SharedPre.getText()}",
                SharedPre.getEmail(),
                username,
                password
            )) {
                is Resources.Success -> {
                    launch(Dispatchers.Main) {
                        Toast.makeText(
                            activity,
                            "profile is edited successfully",
                            Toast.LENGTH_SHORT
                        ).show()
                        (activity as CoffeeActivity?)?.let {
                            it.setCurrentUser(User(username, password, SharedPre.getEmail()))
                        }
                    }
                    result = true
                }
                is Resources.Error -> {
                    Log.v("401 ", "onResponse ${response.data}")
                }
            }
        }
        return result
    }
    fun getUserData(activity: FragmentActivity,binding: FragmentEditProfileBinding) {
        (activity as CoffeeActivity?)?.let{
            displayData(it.getCurrentUser()?.username,binding)
        }
    }

    private fun displayData(username: String?,binding: FragmentEditProfileBinding) {
        binding.etEditUsername.setText(username)
    }
}