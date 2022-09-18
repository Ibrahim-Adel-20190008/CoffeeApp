package com.example.finalproject.main.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.example.finalproject.adapters.CartAdapter
import com.example.finalproject.localDataBase.SharedList
import com.example.finalproject.localDataBase.SharedPre
import com.example.finalproject.main.MainRepository

class CartViewModel @ViewModelInject constructor(
    private val repo: MainRepository
): ViewModel(){
    lateinit var cartTotalPrice: String
    fun displayCart( cartAdapter:CartAdapter){
        cartTotalPrice = calculateCartTotal().toString()
        cartAdapter.cartItems = SharedPre.getUserCart().getAllItems()
    }

    fun deleteItem(cartAdapter: CartAdapter, position: Int) {
        val sharedList: SharedList = SharedPre.getUserCart()
        sharedList.removeAt(position)
        SharedPre.setUserCart(sharedList)
        cartAdapter.cartItems?.removeAt(position)
        cartAdapter.notifyItemRemoved(position)
        cartTotalPrice = calculateCartTotal().toString()
    }

    fun clearAll(cartAdapter: CartAdapter){
        val sharedList = SharedPre.getUserCart()
        sharedList.clear()
        SharedPre.setUserCart(sharedList)
        cartAdapter.cartItems?.clear()
        cartAdapter.notifyDataSetChanged()
        cartTotalPrice = "0.0"
    }

    fun calculateCartTotal(): Double {
        var totalCount = 0.0
        for (item in SharedPre.getUserCart()?.getAllItems()!!) {
            totalCount += item.totalPrice!!
        }
        return totalCount
    }
}