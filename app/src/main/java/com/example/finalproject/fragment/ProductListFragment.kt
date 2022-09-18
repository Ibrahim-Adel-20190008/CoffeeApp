package com.example.finalproject.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.finalproject.Communicator
import com.example.finalproject.R
import com.example.finalproject.adapters.ProductListAdapter
import com.example.finalproject.databinding.FragmentProductListBinding
import com.example.finalproject.main.viewmodels.ProductListViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView

class ProductListFragment : Fragment(),ProductListAdapter.onListener{
    private var productListAdapter: ProductListAdapter? = null
    private lateinit var communicator : Communicator
    private val viewModel: ProductListViewModel by viewModels()

    private var _binding: FragmentProductListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductListBinding.inflate(inflater,container,false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.findViewById<BottomNavigationView>(R.id.bottom_nav)?.visibility = View.VISIBLE
        productListAdapter = ProductListAdapter(this)
        activity?.let { viewModel.getMenu(it, productListAdapter) }
        communicator = activity as Communicator

        val layoutManager = StaggeredGridLayoutManager(1, RecyclerView.VERTICAL)
        val rvCoffees: RecyclerView = binding.rvCoffees
        rvCoffees.layoutManager = layoutManager
        rvCoffees.adapter = productListAdapter

        val profileText = "Menu"
        binding.include.toolbarText.text = profileText
    }

    override fun onClick(position: Int) {
        val coffeeItem = productListAdapter?.Coffees?.get(position)
        communicator.passData(coffeeItem)
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}