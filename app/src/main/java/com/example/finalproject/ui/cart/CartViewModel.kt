package com.example.finalproject.ui.cart

import androidx.lifecycle.ViewModel
import com.example.finalproject.dataBase.localDB.SharedList
import com.example.finalproject.dataBase.localDB.SharedPre
import com.example.finalproject.domain.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val repo: Repository
): ViewModel(){
    var cartTotalPrice: String = "0.0"
    fun displayCart( cartAdapter: CartAdapter){
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