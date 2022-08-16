package com.example.finalproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.finalproject.sharedpref.SharedList
import com.google.android.material.bottomnavigation.BottomNavigationView

class CartActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {
    var cartAdapter:CartAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_nav)
        bottomNavigationView.selectedItemId = R.id.ic_cart
        bottomNavigationView.setOnNavigationItemSelectedListener(this)
        val layoutManager = StaggeredGridLayoutManager(1, RecyclerView.VERTICAL)
        var cartTotalPrice = findViewById<TextView>(R.id.tv_totalPrice)
        cartTotalPrice.text = calculateCartTotal().toString()
        cartAdapter = CartAdapter(SharedList.getAllItems())


        val rvCart: RecyclerView = findViewById(R.id.rv_cart)

        rvCart.layoutManager = layoutManager
        rvCart.adapter = cartAdapter
    }

    fun calculateCartTotal(): Double{
        var totalCount = 0.0
        for (item in SharedList.getAllItems()!!){
            totalCount+= item.totalPrice!!
        }
        return totalCount
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