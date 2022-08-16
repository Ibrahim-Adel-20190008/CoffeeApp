package com.example.finalproject

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import com.bumptech.glide.Glide
import com.example.finalproject.sharedpref.SharedPre

class UserProfileActivity : AppCompatActivity() {
    lateinit var image : ImageView
    lateinit var userEmail:TextView
    lateinit var userName:TextView
    lateinit var logOut: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)

        image = findViewById(R.id.image_view)
        userEmail = findViewById(R.id.user_email)
        userName = findViewById(R.id.username)
        logOut = findViewById(R.id.logout)

        val urlToImg = "https://th.bing.com/th/id/R.fe20a57e77099fe1baea254d50f6802c?rik=FsyFE8G2RLe1EA&riu=http%3a%2f%2fehonami.blob.core.windows.net%2fmedia%2f2014%2f11%2fcoffee-even-decaf-found-benefit-liver-health.jpg&ehk=XtVvJ0uvsrpSLM02RVuUPBj0EQvfbEYoC7lew1%2bWrmk%3d&risl=&pid=ImgRaw&r=0"
        Glide.with(this).load(urlToImg).into(image)

        logOut.setOnClickListener {
            SharedPre.setText("")
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = (Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intent)
        }

    }

}