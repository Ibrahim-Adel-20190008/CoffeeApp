package com.example.finalproject

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.finalproject.adapters.ProductListAdapter
import com.example.finalproject.api.service
import com.example.finalproject.dataClasses.CoffeeItem
import com.example.finalproject.databinding.FragmentCartBinding
import com.example.finalproject.databinding.FragmentProductListBinding
import com.example.finalproject.localDataBase.SharedPre
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductListFragment : Fragment(),ProductListAdapter.onListener{
    var productListAdapter: ProductListAdapter? = null
    private lateinit var communicator : Communicator
    private var _binding: FragmentProductListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProductListBinding.inflate(inflater,container,false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.findViewById<BottomNavigationView>(R.id.bottom_nav)?.setVisibility(View.VISIBLE)
        val layoutManager = StaggeredGridLayoutManager(1, RecyclerView.VERTICAL)
        getCoffeeList()
        productListAdapter = ProductListAdapter(this)
        communicator = activity as Communicator

        val rvCoffees: RecyclerView = binding.rvCoffees
        rvCoffees.layoutManager = layoutManager
        rvCoffees.adapter = productListAdapter

        val profileText = "Menu"
        binding.include.toolbarText.text = profileText

    }
    fun getCoffeeList() {
       // service.getAllProducts("Bearer ${SharedPre.getText()}").enqueue(this)
        GlobalScope.launch(Dispatchers.IO){
            val response = service.getAllProducts("Bearer ${SharedPre.getText()}")
            if (response.isSuccessful){
                Log.v("ProductList","testing it ")
                launch(Dispatchers.Main){
                    productListAdapter?.Coffees = response.body()!!
                    productListAdapter?.notifyDataSetChanged()
                }

            }
        }
    }

    //TODO hanle on cilck listner
    override fun onClick(position: Int) {
        val coffeeItem = productListAdapter?.Coffees?.get(position)
        communicator.passData(coffeeItem)
    }

    /*@SuppressLint("NotifyDataSetChanged")
    override fun onResponse(
        call: Call<ArrayList<CoffeeItem>>,
        response: Response<ArrayList<CoffeeItem>>
    ) {
        Log.v("ProductList","testing it ")
        productListAdapter?.Coffees = response.body()!!
        productListAdapter?.notifyDataSetChanged()
    }

    override fun onFailure(call: Call<ArrayList<CoffeeItem>>, t: Throwable) {
        Log.v("5", "onFailure ${t.localizedMessage} ")
    }*/

}