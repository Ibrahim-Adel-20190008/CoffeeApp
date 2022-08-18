package com.example.finalproject

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity


class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        val PREFS_NAME = "MyPrefsFile"

        val settings = getSharedPreferences(PREFS_NAME, 0)

        if (settings.getBoolean("my_first_time", true)) {
            supportActionBar?.hide()
            val handler = Handler(Looper.getMainLooper())
            handler.postDelayed({
                val intent = Intent(this,MainActivity::class.java)
                startActivity(intent)
                finish()
            },3000)
            settings.edit().putBoolean("my_first_time", false).apply()
        }

    }

}