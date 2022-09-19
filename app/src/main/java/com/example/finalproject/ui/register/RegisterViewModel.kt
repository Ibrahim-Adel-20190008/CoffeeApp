package com.example.finalproject.ui.register

import android.util.Patterns
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
import java.util.regex.Pattern
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
            } else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                Toast.makeText(
                    activity,
                    "please write an email format in email field",
                    Toast.LENGTH_SHORT
                ).show()
            } else if (!isValidPassword(password)){
                Toast.makeText(
                    activity,
                    "make sure password contains at least 1 uppercase letter, 1 lowercase letter, 1 digit, 1 special character and minimum 4 characters",
                    Toast.LENGTH_LONG
                ).show()
            }
            else if (password != repeatedPassword) {
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

    fun isValidPassword(pass : String): Boolean{
        //at least 1 uppercase letter, at least 1 lowercase letter , at least 1 digit,at least1 special character ,minimum 4 characters and max is 24
        val matcher = Pattern.compile("((?=.*[a-z])(?=.*\\d)(?=.*[A-Z])(?=.*[@#?$%!]).{4,24})").matcher(pass)
        return matcher.matches()
    }
}