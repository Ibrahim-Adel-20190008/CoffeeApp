package com.example.finalproject

import android.app.Application
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.finalproject.dataclasses.CoffeeItem
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView

class ProductListActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener{
    var productListAdapter:ProductListAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_list)
        //val layoutManager = LinearLayoutManager(this)
        //val layoutManager = GridLayoutManager(this, 2)
        val layoutManager = StaggeredGridLayoutManager(1, RecyclerView.VERTICAL)
        val Coffees = generateCoffeeArray()
        productListAdapter = ProductListAdapter(Coffees)
        val rvCoffees: RecyclerView = findViewById(R.id.rv_coffees)

        rvCoffees.layoutManager = layoutManager
        rvCoffees.adapter = productListAdapter
        // getNews() fun that will use Api fun to get coffees list
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_nav)
        bottomNavigationView.selectedItemId = R.id.ic_home
        bottomNavigationView.setOnNavigationItemSelectedListener(this)

    }

    fun generateCoffeeArray(): Array<CoffeeItem> {
        // Note: function will be removed and replaced with array from API Request
        // we will call the api function here using the service instead of creating array
        return Array(10) {
           CoffeeItem("https://picsum.photos/100/100","Coffee",null)
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
                startActivity(Intent(this,UserProfileActivity::class.java))
                overridePendingTransition(0,0)
                return true
            }
            R.id.ic_home ->{
                return true
            }
            else ->{
                return false
            }
        }
    }
}