package com.example.finalproject.main.viewmodels

import android.content.Intent
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.fragment.app.FragmentActivity
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalproject.activities.CoffeeActivity
import com.example.finalproject.data.models.User
import com.example.finalproject.localDataBase.SharedPre
import com.example.finalproject.main.MainRepository
import com.example.finalproject.util.Resources
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel @ViewModelInject constructor(
    private val repo: MainRepository
) : ViewModel() {

    fun login(user:User, activity:FragmentActivity, password:String){
        viewModelScope.launch(Dispatchers.IO){
            when(val response = repo.login(user)){
                is Resources.Success -> {
                    SharedPre.setText(response.data?.token.toString())
                    SharedPre.setEmail(response.data?.email)
                    SharedPre.setPassword(password)
                    Log.v(
                        "Logged successfully",
                        "onResponse ${response.data.toString()}"
                    )
                    checkToken(activity)
                }
                is Resources.Error->{
                    launch(Dispatchers.Main){
                        Toast.makeText(
                            activity,
                            "Wrong Email or Password",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }

    private fun checkToken(activity:FragmentActivity) {
        if (!TextUtils.isEmpty(SharedPre.getText())) {
            val intent = Intent(activity, CoffeeActivity::class.java)
            intent.flags = (Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(activity,intent,null)
        }
    }
}