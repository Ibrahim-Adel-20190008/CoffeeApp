package com.example.finalproject.mainActivitys

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.finalproject.*
import com.example.finalproject.dataBase.remoteDB.models.CoffeeItem
import com.example.finalproject.dataBase.remoteDB.models.User
import com.example.finalproject.ui.cart.CartFragment
import com.example.finalproject.ui.prefernces.PreferencesFragment
import com.example.finalproject.ui.productList.ProductListFragment
import com.example.finalproject.ui.userProfile.UserProfileFragment
import com.example.finalproject.databinding.ActivityCoffeeBinding
import com.example.finalproject.ui.util.Communicator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CoffeeActivity : AppCompatActivity(), Communicator {
    private lateinit var binding: ActivityCoffeeBinding
    private var currentUser : User = User()
    private var coffees: ArrayList<CoffeeItem> = arrayListOf()

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
            commit()
        }
    }

    public fun getCurrentUser(): User? {
        return currentUser;
    }
    public fun setCurrentUser(newCurrentUser: User){
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