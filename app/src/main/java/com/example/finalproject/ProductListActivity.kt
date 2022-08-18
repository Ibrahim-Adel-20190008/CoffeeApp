package com.example.finalproject

import android.annotation.SuppressLint
import android.app.Application
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.finalproject.dataclasses.CoffeeItem
import com.example.finalproject.sharedpref.SharedPre
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductListActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener,ProductListAdapter.onListener,Callback<ArrayList<CoffeeItem>>{
    var productListAdapter:ProductListAdapter? = null
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
        val profileText ="Menu"
        toolBarText.text = profileText
        // getNews() fun that will use Api fun to get coffees list
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_nav)
        bottomNavigationView.selectedItemId = R.id.ic_home
        bottomNavigationView.setOnNavigationItemSelectedListener(this)
        //testApi()
        supportActionBar?.hide()

        // click back
        arrowBack.setOnClickListener {
            finish()
        }

    }

    fun getCoffeeList(){
        service.getAllProducts("Bearer ${SharedPre.getText()}").enqueue(this)
    }

    /*fun testApi(){
        service.getAllProducts("Bearer ${SharedPre.getText()}")
            .enqueue(object : Callback<ArrayList<CoffeeItem>> {
            override fun onResponse(call: Call<ArrayList<CoffeeItem>>, response: Response<ArrayList<CoffeeItem>>) {
                if (response.isSuccessful) {
                    if(response.code()==200){
                        Log.v("3", "onResponse Success ${response.body().toString()}")

                    }


                }else {
                    Log.v("4", "onResponse not success ${response.code()}")
                    Log.v("4", "onResponse not success ${response.body().toString()}")
                }
                Log.v("6", " Token value ${SharedPre.getText()}")
            }

            override fun onFailure(call: Call<ArrayList<CoffeeItem>>, t: Throwable) {
                Log.v("5", "onFailure ${t.localizedMessage} ")
            }
        })
    }

    fun generateCoffeeArray(): Array<CoffeeItem> {
        // Note: function will be removed and replaced with array from API Request
        // we will call the api function here using the service instead of creating array
        return Array(10) {
           CoffeeItem("https://picsum.photos/100/100","Coffee",null,"product Description")
        }
    }*/

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

    override fun onClick(position: Int) {
        val coffeeItem = productListAdapter?.Coffees?.get(position)
        val intent = Intent(this,Preferences::class.java)
        intent.putExtra("Selected_Item",coffeeItem)
        startActivity(intent)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onResponse(
        call: Call<ArrayList<CoffeeItem>>,
        response: Response<ArrayList<CoffeeItem>>
    ) {
        productListAdapter?.Coffees = response.body()!!
        productListAdapter?.notifyDataSetChanged()    }

    override fun onFailure(call: Call<ArrayList<CoffeeItem>>, t: Throwable) {
        Log.v("5", "onFailure ${t.localizedMessage} ")
    }
}