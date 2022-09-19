package com.example.finalproject.ui.login

import android.content.Intent
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalproject.mainActivitys.CoffeeActivity
import com.example.finalproject.dataBase.remoteDB.models.User
import com.example.finalproject.dataBase.localDB.SharedPre
import com.example.finalproject.domain.Repository
import com.example.finalproject.ui.util.Resources
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repo: Repository
) : ViewModel() {

    @OptIn(DelicateCoroutinesApi::class)
    fun login(user:User, activity:FragmentActivity, password:String){
        viewModelScope.launch(Dispatchers.IO){
            when(val response = repo.login(user)){
                is Resources.Success -> {
                    repo.setToken(response.data?.token!!)
                    repo.setEmail(response.data.email)
                    repo.setPassword(password)
                    Log.v(
                        "Logged successfully",
                        "onResponse ${response.data.toString()}"
                    )
                    Log.v("SharedPrefData",
                        "email: ${SharedPre.getEmail()}, token ${SharedPre.getText()}")
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

    fun checkToken(activity:FragmentActivity) {
        if (!TextUtils.isEmpty(SharedPre.getText())) {
            val intent = Intent(activity, CoffeeActivity::class.java)
            intent.flags = (Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(activity,intent,null)
        }
    }
}