package com.example.finalproject

import android.content.Intent
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
import com.example.finalproject.sharedpref.SharedPre
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
        // still login
//        if(SharedPre.getText()!=null)
//        {
//            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//            startActivity(Intent(this, UserProfileActivity::class.java))
//        }

        Login = findViewById(R.id.btn_login)
        Register = findViewById(R.id.register)

        Login.setOnClickListener{
            Email = findViewById(R.id.et_email)
            Password = findViewById(R.id.et_password)
            checkLogin()

//            SharedPre.setText("newToken")
//            intent.flags = (Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)

            // start user profile activity
            val intent = Intent(this, UserProfileActivity::class.java)
            startActivity(intent)
        }

        Register.setOnClickListener{
            //TODO register activity
        }


    }

    // test api
    private fun checkLogin() {
        val response = service.Login(Email.toString(),Password.toString())
        response?.enqueue(object : Callback<Login?>{
            override fun onResponse(call: Call<Login?>, response: Response<Login?>) {
                if(response.isSuccessful)
                {
                    Log.d("###", response.body()?.data.toString())
                }
            }

            override fun onFailure(call: Call<Login?>, t: Throwable) {
                Log.d("###", "Nothing")
            }
        })
    }


}