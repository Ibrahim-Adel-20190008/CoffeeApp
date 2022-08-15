package com.example.finalproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.finalproject.dataclasses.RegisterResponse
import com.example.finalproject.loginClasses.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {
    var etEmail: EditText? = null
    var etUsername: EditText? = null
    var etPassword: EditText? = null
    var etRepeatedPass: EditText? = null
    var btnRegister: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

         etEmail = findViewById<EditText>(R.id.et_regEmail)
         etUsername = findViewById<EditText>(R.id.et_regUsername)
         etPassword = findViewById<EditText>(R.id.et_regPassword)
         etRepeatedPass = findViewById<EditText>(R.id.et_repeatPass)
        btnRegister = findViewById(R.id.btn_signup)

        Log.v("1", "before button register")
        btnRegister?.setOnClickListener {
            Log.v("2", "inside button register")
            val email = etEmail?.text.toString()
            val password = etPassword?.text.toString()
            val username = etUsername?.text.toString()
            val repeatedPass = etRepeatedPass?.text.toString()
            if(email.isEmpty() || password.isEmpty() || username.isEmpty() || repeatedPass.isEmpty()){
                Toast.makeText(this,"please fill all required fields", Toast.LENGTH_SHORT).show()
            }else if(password != repeatedPass){
                Toast.makeText(this,"password field is not equal to repeated password field", Toast.LENGTH_SHORT).show()
            }else{
                val user = User(email, password)
                service.register(user)
                    .enqueue(object : Callback<RegisterResponse> {
                        override fun onResponse(call: Call<RegisterResponse>, response: Response<RegisterResponse>) {
                            if (response.isSuccessful) {
                                //Toast.makeText(this@RegisterActivity,"please fill all required fields", Toast.LENGTH_SHORT).show()

                                Log.v("3", "onResponse ${response.body().toString()}")

                            } else {
                                Log.v("4", "onResponse ${response.code()}")
                            }
                        }

                        override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                            Log.v("5", "onFailure ${t.localizedMessage} ")
                        }
                    })

            }
        }
    }


}