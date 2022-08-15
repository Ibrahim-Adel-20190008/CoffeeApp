package com.example.finalproject

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.finalproject.loginClasses.Login
import com.example.finalproject.loginClasses.LoginResponse
import com.example.finalproject.loginClasses.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity(){
    lateinit var Email : EditText
    lateinit var Password : EditText
    lateinit var Login : Button
    lateinit var Register : TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Login = findViewById(R.id.btn_login)
        Register = findViewById(R.id.register)

        Login.setOnClickListener{
            Email = findViewById(R.id.et_email)
            Password = findViewById(R.id.et_password)
            checkLogin()
        }

        Register.setOnClickListener{
            //TODO register activity
        }
    }

    private fun checkLogin() {
        val response = service.Login(User(Email.toString(),Password.toString()))
        response?.enqueue(object : Callback<LoginResponse?>{
            override fun onResponse(call: Call<LoginResponse?>, response: Response<LoginResponse?>) {
                if(response.isSuccessful)
                {
                    Log.d("###", response.body()?.token.toString())
                }
            }

            override fun onFailure(call: Call<LoginResponse?>, t: Throwable) {
                Log.d("###", "Nothing")
            }
        })
    }


}