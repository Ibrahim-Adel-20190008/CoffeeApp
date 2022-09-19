package com.example.finalproject.ui.register

import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalproject.dataBase.remoteDB.models.User
import com.example.finalproject.domain.Repository
import com.example.finalproject.ui.util.Resources
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val repo: Repository
) : ViewModel() {
    fun Register(
        email: String,
        password: String,
        username: String,
        repeatedPassword: String,
        activity: FragmentActivity,
        registerFragment: RegisterFragment
    ){
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
                            withContext(Dispatchers.Main){
                                activity.onBackPressed()
                            }
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
    }
}