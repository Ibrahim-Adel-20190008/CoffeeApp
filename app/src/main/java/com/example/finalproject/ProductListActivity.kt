package com.example.finalproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.finalproject.dataclasses.CoffeeItem

class ProductListActivity : AppCompatActivity() {
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

    }

    fun generateCoffeeArray(): Array<CoffeeItem> {
        // Note: function will be removed and replaced with array from API Request
        // we will call the api function here using the service instead of creating array
        return Array(10) {
           CoffeeItem("https://picsum.photos/100/100","Coffee",null)
        }
    }
}