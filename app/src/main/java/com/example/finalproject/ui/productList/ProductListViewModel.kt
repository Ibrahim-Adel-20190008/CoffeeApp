package com.example.finalproject.ui.productList

import android.util.Log
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalproject.mainActivitys.CoffeeActivity
import com.example.finalproject.dataBase.localDB.SharedPre
import com.example.finalproject.domain.Repository
import com.example.finalproject.ui.util.Resources
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductListViewModel @Inject constructor(
    private val repo : Repository
) : ViewModel() {
    fun getMenu(activity : FragmentActivity, productListAdapter: ProductListAdapter?){
            ( activity as CoffeeActivity?)?.let {
                if(it.getMenuList().size==0) {
                    viewModelScope.launch(Dispatchers.IO){
                        when(val response = repo.getAllProducts("Bearer ${SharedPre.getText()}")){
                            is Resources.Success->{
                                Log.v("ProductList","testing it ")
                                launch(Dispatchers.Main){
                                    productListAdapter?.Coffees = response.data!!
                                    productListAdapter?.Coffees?.let { it1 -> it.setMenuList(it1) }
                                    productListAdapter?.notifyDataSetChanged()
                                }
                            }
                            is Resources.Error->{
                                Log.v("Product list","Error can't get it!")
                            }
                        }
                    }
                }
                else{
                productListAdapter?.Coffees = it.getMenuList()
                productListAdapter?.notifyDataSetChanged()
                }

            }
    }
}