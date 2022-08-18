package com.example.finalproject.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.finalproject.R
import com.example.finalproject.dataClasses.User
import com.example.finalproject.localDataBase.SharedPre
import com.example.finalproject.api.service
import com.google.android.material.bottomnavigation.BottomNavigationView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserProfileActivity : AppCompatActivity(),
    BottomNavigationView.OnNavigationItemSelectedListener {
    lateinit var image: ImageView
    lateinit var userEmail: TextView
    private lateinit var arrowBack: ImageView
    lateinit var toolBarText: TextView
    lateinit var userName: TextView
    lateinit var hiMsg: TextView
    lateinit var logOut: Button
    lateinit var editProfile: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)

        image = findViewById(R.id.image_view)
        userEmail = findViewById(R.id.user_email)
        userName = findViewById(R.id.username)
        logOut = findViewById(R.id.logout)
        hiMsg = findViewById(R.id.hi)
        arrowBack = findViewById(R.id.arrow_back)
        toolBarText = findViewById(R.id.toolbar_text)
        editProfile = findViewById(R.id.btn_editProfile)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_nav)
        bottomNavigationView.selectedItemId = R.id.ic_profile
        bottomNavigationView.setOnNavigationItemSelectedListener(this)
        val profileText = "My Profile"
        toolBarText.text = profileText

        val urlToImg =
            "https://th.bing.com/th/id/R.fe20a57e77099fe1baea254d50f6802c?rik=FsyFE8G2RLe1EA&riu=http%3a%2f%2fehonami.blob.core.windows.net%2fmedia%2f2014%2f11%2fcoffee-even-decaf-found-benefit-liver-health.jpg&ehk=XtVvJ0uvsrpSLM02RVuUPBj0EQvfbEYoC7lew1%2bWrmk%3d&risl=&pid=ImgRaw&r=0"
        Glide.with(this).load(urlToImg).into(image)

        editProfile.setOnClickListener {
            startActivity(Intent(this, EditProfileActivity::class.java))
        }



        logOut.setOnClickListener {
            SharedPre.setText(null)
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = (Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intent)
        }

        // click back
        supportActionBar?.hide()
        arrowBack.setOnClickListener {
            finish()
        }
        getUserData()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.ic_cart -> {
                startActivity(Intent(this, CartActivity::class.java))
                overridePendingTransition(0, 0)
                return true
            }
            R.id.ic_profile -> {
                return true
            }
            R.id.ic_home -> {
                startActivity(Intent(this, ProductListActivity::class.java))
                overridePendingTransition(0, 0)
                return true
            }
            else -> {
                return false
            }
        }
    }

    fun getUserData() {
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
            })
    }

    fun displayData(currentUser: User) {
        userEmail.text = currentUser.email
        userName.text = currentUser.username
        hiMsg.text = "Hi ${currentUser.username}"
    }
}