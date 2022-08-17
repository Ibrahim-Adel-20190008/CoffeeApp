package com.example.finalproject

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import com.bumptech.glide.Glide
import com.example.finalproject.sharedpref.SharedPre
import com.google.android.material.bottomnavigation.BottomNavigationView

class UserProfileActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {
    lateinit var image : ImageView
    lateinit var userEmail:TextView
    private lateinit var arrowBack: ImageView
    lateinit var userName:TextView
    lateinit var hiMsg:TextView
    lateinit var toolBarText:TextView
    lateinit var logOut: Button
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

        val currentUser =SharedPre.getUser()
        userEmail.text = currentUser?.email
        userName.text = currentUser?.username
        hiMsg.text = "Hi ${currentUser?.username}"

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_nav)
        bottomNavigationView.selectedItemId = R.id.ic_profile
        bottomNavigationView.setOnNavigationItemSelectedListener(this)
        val profileText ="My Profile"
        toolBarText.text = profileText

        val urlToImg = "https://th.bing.com/th/id/R.fe20a57e77099fe1baea254d50f6802c?rik=FsyFE8G2RLe1EA&riu=http%3a%2f%2fehonami.blob.core.windows.net%2fmedia%2f2014%2f11%2fcoffee-even-decaf-found-benefit-liver-health.jpg&ehk=XtVvJ0uvsrpSLM02RVuUPBj0EQvfbEYoC7lew1%2bWrmk%3d&risl=&pid=ImgRaw&r=0"
        Glide.with(this).load(urlToImg).into(image)



        logOut.setOnClickListener {
            SharedPre.setText(null)
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = (Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intent)
        }

        // click back
        supportActionBar?.hide()
        arrowBack.setOnClickListener {
            finish()
        }

    }
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.ic_cart ->{
                startActivity(Intent(this,CartActivity::class.java))
                overridePendingTransition(0,0)
                return true
            }
            R.id.ic_profile ->{
                return true
            }
            R.id.ic_home ->{
                startActivity(Intent(this,ProductListActivity::class.java))
                overridePendingTransition(0,0)
                return true
            }
            else ->{
                return false
            }
        }
    }

}