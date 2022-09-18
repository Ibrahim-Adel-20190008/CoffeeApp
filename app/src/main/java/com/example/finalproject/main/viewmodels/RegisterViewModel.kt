package com.example.finalproject.main.viewmodels

import android.util.Log
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import com.example.finalproject.R
import com.example.finalproject.data.models.User
import com.example.finalproject.databinding.FragmentRegisterBinding
import com.example.finalproject.fragment.LoginFragment
import com.example.finalproject.main.MainRepository
import com.example.finalproject.util.Resources
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegisterViewModel @ViewModelInject constructor(
    private val repo: MainRepository
) : ViewModel() {
    fun Register(
        email:String,
        password:String,
        username:String,
        repeatedPassword:String,
        activity: FragmentActivity) : Boolean{
            Log.v("2", "inside button register")
            var result = false
            if (email.isEmpty() || password.isEmpty() || username.isEmpty() || repeatedPassword.isEmpty()) {
                Toast.makeText(activity, "please fill all required fields", Toast.LENGTH_SHORT).show()
            } else if (password != repeatedPassword) {
                Toast.makeText(
                    activity,
                    "password field is not equal to repeated password field",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                val user = User(username, password, email)
                viewModelScope.launch(Dispatchers.IO) {
                    when (val response = repo.register(user)) {
                        is Resources.Success->{
                            launch(Dispatchers.Main){
                                Toast.makeText(
                                    activity,
                                    "User Registered Successfully",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                            result= true
                        }
                        is Resources.Error->{
                            launch(Dispatchers.Main){
                                Toast.makeText(
                                    activity,
                                    "Email is already in use",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }
                    }
                    }
                }
        return result
    }
}