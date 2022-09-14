package com.example.finalproject.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.finalproject.*
import com.example.finalproject.dataClasses.CoffeeItem
import com.example.finalproject.databinding.ActivityCoffeeBinding
import com.example.finalproject.databinding.ActivityUserBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class CoffeeActivity : AppCompatActivity(), Communicator {
    private lateinit var binding: ActivityCoffeeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
           // addToBackStack(null)
            commit()
        }
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