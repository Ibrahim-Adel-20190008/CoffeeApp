package com.example.finalproject.main.viewmodels

import android.util.Log
import androidx.fragment.app.FragmentActivity
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.finalproject.activities.CoffeeActivity
import com.example.finalproject.adapters.ProductListAdapter
import com.example.finalproject.databinding.FragmentProductListBinding
import com.example.finalproject.localDataBase.SharedPre
import com.example.finalproject.main.MainRepository
import com.example.finalproject.util.Resources
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ProductListViewModel @ViewModelInject constructor(
    private val repo : MainRepository
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
                                productListAdapter?.Coffees = it.getMenuList()
                                productListAdapter?.notifyDataSetChanged()
                            }
                        }
                    }
                }
            }
    }
}