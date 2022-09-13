package com.example.finalproject.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.finalproject.LoginFragment
import com.example.finalproject.R
import com.example.finalproject.RegisterFragment
import com.example.finalproject.databinding.ActivityRegisterBinding
import com.example.finalproject.databinding.ActivityUserBinding

class UserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragment_container,LoginFragment())
            commit()
        }
    }
}