package com.example.finalproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.google.android.material.bottomnavigation.BottomNavigationView

class CartActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_nav)
        bottomNavigationView.selectedItemId = R.id.ic_cart
        bottomNavigationView.setOnNavigationItemSelectedListener(this)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.ic_cart ->{
                return true
            }
            R.id.ic_profile ->{
                startActivity(Intent(this,UserProfileActivity::class.java))
                overridePendingTransition(0,0)
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