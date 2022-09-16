package com.example.finalproject.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.finalproject.Communicator
import com.example.finalproject.R
import com.example.finalproject.activities.CoffeeActivity
import com.example.finalproject.adapters.ProductListAdapter
import com.example.finalproject.api.service
import com.example.finalproject.databinding.FragmentProductListBinding
import com.example.finalproject.localDataBase.SharedPre
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

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
        productListAdapter = ProductListAdapter(this)
        getCoffeeList()
        communicator = activity as Communicator

        val rvCoffees: RecyclerView = binding.rvCoffees
        rvCoffees.layoutManager = layoutManager
        rvCoffees.adapter = productListAdapter

        val profileText = "Menu"
        binding.include.toolbarText.text = profileText

    }
    @OptIn(DelicateCoroutinesApi::class)
    private fun getCoffeeList() {
        ( activity as CoffeeActivity?)?.let {
            if(it.getMenuList().size==0) {
                GlobalScope.launch(Dispatchers.IO){
                    val response = service.getAllProducts("Bearer ${SharedPre.getText()}")
                    if (response.isSuccessful){
                        Log.v("ProductList","testing it ")
                        launch(Dispatchers.Main){
                            productListAdapter?.Coffees = response.body()!!
                            productListAdapter?.Coffees?.let { it1 -> it.setMenuList(it1) }
                            productListAdapter?.notifyDataSetChanged()
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

    override fun onClick(position: Int) {
        val coffeeItem = productListAdapter?.Coffees?.get(position)
        communicator.passData(coffeeItem)
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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