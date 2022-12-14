package com.example.finalproject.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.finalproject.R
import com.example.finalproject.api.service
import com.example.finalproject.dataClasses.User
import com.example.finalproject.localDataBase.SharedPre
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EditProfileActivity : AppCompatActivity() {
    lateinit var etUsername: EditText
    lateinit var etNewPassword: EditText
    lateinit var etRepeatPassword: EditText
    lateinit var btnConfirm: Button
    private lateinit var arrowBack: ImageView
    lateinit var toolBarText: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)
        etUsername = findViewById(R.id.et_editUsername)
        etNewPassword = findViewById(R.id.et_newPassword)
        etRepeatPassword = findViewById(R.id.et_repeatNewPass)
        btnConfirm = findViewById(R.id.btn_confirm)
        arrowBack = findViewById(R.id.arrow_back)
        toolBarText = findViewById(R.id.toolbar_text)
        supportActionBar?.hide()
        val editProfileText = "Edit My Profile"
        toolBarText.text = editProfileText

        // click back
        arrowBack.setOnClickListener {
            finish()
        }

        btnConfirm.setOnClickListener {
            val password = etNewPassword.text.toString()
            val username = etUsername.text.toString()
            val repeatedPass = etRepeatPassword.text.toString()
            if (password.isEmpty() || username.isEmpty() || repeatedPass.isEmpty()) {
                Toast.makeText(this, "please fill all required fields", Toast.LENGTH_SHORT).show()
            } else if (password != repeatedPass) {
                Toast.makeText(
                    this,
                    "password field is not equal to repeated password field",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
               /* service.editProfileInfo(
                    "Bearer ${SharedPre.getText()}",
                    SharedPre.getEmail(),
                    username,
                    password
                ).enqueue(object : Callback<Unit> {
                    override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                        if (response.isSuccessful) {
                            Toast.makeText(
                                this@EditProfileActivity,
                                "profile is edited successfully",
                                Toast.LENGTH_SHORT
                            ).show()
                            startActivity(
                                Intent(
                                    this@EditProfileActivity,
                                    UserProfileActivity::class.java
                                )
                            )
                        } else {
                            Log.v("401 ", "onResponse ${response.body()}")
                        }
                    }

                    override fun onFailure(call: Call<Unit>, t: Throwable) {
                        Log.d("###", "Nothing")
                    }
                })*/
            }

        }
        getUserData()
    }

    fun getUserData() { /*
        service.getUser("Bearer ${SharedPre.getText()}", SharedPre.getEmail())
            .enqueue(object : Callback<User> {
                override fun onResponse(call: Call<User>, response: Response<User>) {
                    if (response.isSuccessful) {
                        val email = response.body()?.email
                        val fullName = response.body()?.username
                        val password = response.body()?.password
                        displayData(User(fullName, password, email))
                    } else {
                        Log.v("401 ", "onResponse ${response.body()}")
                    }
                }

                override fun onFailure(call: Call<User>, t: Throwable) {
                    Log.d("###", "Nothing")
                }
            })*/
    }

    fun displayData(currentUser: User) {
        etUsername.setText(currentUser.username)
    }
}