package com.example.finalproject.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.finalproject.*
import com.example.finalproject.api.service
import com.example.finalproject.dataClasses.CoffeeItem
import com.example.finalproject.dataClasses.User
import com.example.finalproject.databinding.ActivityCoffeeBinding
import com.example.finalproject.fragment.CartFragment
import com.example.finalproject.fragment.PreferencesFragment
import com.example.finalproject.fragment.ProductListFragment
import com.example.finalproject.fragment.UserProfileFragment
import com.example.finalproject.localDataBase.SharedPre
import kotlinx.coroutines.*

class CoffeeActivity : AppCompatActivity(), Communicator {
    private lateinit var binding: ActivityCoffeeBinding
    private lateinit var currentUser :User
    private var coffees: ArrayList<CoffeeItem> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getUserInfoApi()

        binding = ActivityCoffeeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        val cartFragment = CartFragment()
        val userProfileFragment = UserProfileFragment()
        val productListFragment = ProductListFragment()
        setFragment(productListFragment)
        binding.bottomNav.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.ic_cart -> setFragment(cartFragment)
                R.id.ic_profile -> setFragment(userProfileFragment)
                R.id.ic_home -> setFragment(productListFragment)
            }
            true
        }
    }

    private fun setFragment(fragment:Fragment){
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragment_container2, fragment)
            commit()
        }
    }

    private fun getUserInfoApi(){
        lifecycleScope.launch(Dispatchers.IO){
            val response = service.getUser("Bearer ${SharedPre.getText()}", SharedPre.getEmail())
            if(response.isSuccessful){
                val email = response.body()?.email
                val fullName = response.body()?.username
                val password = response.body()?.password
                withContext(Dispatchers.Main){
                    currentUser =  User(fullName, password, email)
                }
            }else{
                Log.v("401 ", "onResponse ${response.body()}")
            }
        }
    }

    public fun getCurrentUser(): User{
        return currentUser;
    }
    public fun setCurrentUser(newCurrentUser:User){
        currentUser = newCurrentUser;
    }
    public fun getMenuList(): ArrayList<CoffeeItem>{
        return coffees
    }
    public fun setMenuList(newCoffees : ArrayList<CoffeeItem>){
        coffees = newCoffees
    }

    override fun passData(item: CoffeeItem?) {
        val bundle = Bundle()
        bundle.putParcelable("Selected Item",item)
        val fragment = PreferencesFragment()
        fragment.arguments=bundle
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragment_container2, fragment)
            addToBackStack(null)
            commit()
        }
    }
}