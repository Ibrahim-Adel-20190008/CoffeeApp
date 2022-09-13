package com.example.finalproject

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.finalproject.adapters.CartAdapter
import com.example.finalproject.databinding.FragmentCartBinding
import com.example.finalproject.databinding.FragmentRegisterBinding
import com.example.finalproject.localDataBase.SharedPre
import com.google.android.material.bottomnavigation.BottomNavigationView



class CartFragment : Fragment() {
    var cartAdapter: CartAdapter? = null
    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCartBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager = StaggeredGridLayoutManager(1, RecyclerView.VERTICAL)
        val cartTotalPrice = binding.tvTotalPrice
        cartTotalPrice.text = calculateCartTotal().toString()
        cartAdapter = CartAdapter(SharedPre.getUserCart()?.getAllItems())


        val rvCart: RecyclerView = binding.rvCart
        rvCart.layoutManager = layoutManager
        rvCart.adapter = cartAdapter
        //arrowBack = binding.root.findViewById<ImageView>(R.id.arrow_back)
        //toolBarText = binding.root.findViewById<TextView>(R.id.toolbar_text)
        //image = binding.root.findViewById<ImageView>(R.id.cart_image)
        binding.root.findViewById<ImageView>(R.id.cart_image).visibility = View.VISIBLE
        val cartText = "My Cart"
        binding.root.findViewById<TextView>(R.id.toolbar_text).text = cartText

        // click back
//        binding.root.findViewById<ImageView>(R.id.arrow_back).setOnClickListener {
//            activity?.onBackPressed()        }

    }

    fun calculateCartTotal(): Double {
        var totalCount = 0.0
        for (item in SharedPre.getUserCart()?.getAllItems()!!) {
            totalCount += item.totalPrice!!
        }
        return totalCount
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}