package com.example.finalproject.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.finalproject.R
import com.example.finalproject.adapters.ProductListAdapter
import com.example.finalproject.api.service
import com.example.finalproject.dataClasses.CoffeeItem
import com.example.finalproject.localDataBase.SharedPre
import com.google.android.material.bottomnavigation.BottomNavigationView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductListActivity : AppCompatActivity(),
    BottomNavigationView.OnNavigationItemSelectedListener,
    ProductListAdapter.onListener, Callback<ArrayList<CoffeeItem>> {
    var productListAdapter: ProductListAdapter? = null
    private lateinit var arrowBack: ImageView
    lateinit var toolBarText: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_list)
        val layoutManager = StaggeredGridLayoutManager(1, RecyclerView.VERTICAL)
        getCoffeeList()
        productListAdapter = ProductListAdapter(this)
        val rvCoffees: RecyclerView = findViewById(R.id.rv_coffees)

        rvCoffees.layoutManager = layoutManager
        rvCoffees.adapter = productListAdapter
        arrowBack = findViewById(R.id.arrow_back)
        toolBarText = findViewById(R.id.toolbar_text)
        val profileText = "Menu"
        toolBarText.text = profileText
        // getNews() fun that will use Api fun to get coffees list
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_nav)
        bottomNavigationView.selectedItemId = R.id.ic_home
        bottomNavigationView.setOnNavigationItemSelectedListener(this)
        //testApi()
        supportActionBar?.hide()
        arrowBack.setVisibility(View.INVISIBLE)

    }

    fun getCoffeeList() {
        //service.getAllProducts("Bearer ${SharedPre.getText()}").enqueue(this)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.ic_cart -> {
                startActivity(Intent(this, CartActivity::class.java))
                overridePendingTransition(0, 0)
                return true
            }
            R.id.ic_profile -> {
                startActivity(Intent(this, UserProfileActivity::class.java))
                overridePendingTransition(0, 0)
                return true
            }
            R.id.ic_home -> {
                return true
            }
            else -> {
                return false
            }
        }
    }

    override fun onClick(position: Int) {
        val coffeeItem = productListAdapter?.Coffees?.get(position)
        val intent = Intent(this, PreferencesActivity::class.java)
        intent.putExtra("Selected_Item", coffeeItem)
        startActivity(intent)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onResponse(
        call: Call<ArrayList<CoffeeItem>>,
        response: Response<ArrayList<CoffeeItem>>
    ) {
        productListAdapter?.Coffees = response.body()!!
        productListAdapter?.notifyDataSetChanged()
    }

    override fun onFailure(call: Call<ArrayList<CoffeeItem>>, t: Throwable) {
        Log.v("5", "onFailure ${t.localizedMessage} ")
    }
}