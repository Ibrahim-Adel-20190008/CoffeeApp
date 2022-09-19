package com.example.finalproject.ui.cart

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.finalproject.R
import com.example.finalproject.databinding.FragmentCartBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CartFragment : Fragment(){
    var cartAdapter: CartAdapter = CartAdapter()
    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!
    private val viewModel : CartViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCartBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.displayCart(cartAdapter)
        val layoutManager = StaggeredGridLayoutManager(1, RecyclerView.VERTICAL)
        val cartTotalPrice = binding.tvTotalPrice
        cartTotalPrice.text =viewModel.cartTotalPrice

        cartAdapter.setOnItemClickListener(object : CartAdapter.OnItemClickListener {
            override fun onDeleteCLick(position: Int) {
                viewModel.deleteItem(cartAdapter,position)
                cartTotalPrice.text =viewModel.cartTotalPrice
            }
        })

       binding.clearCart.setOnClickListener {
           viewModel.clearAll(cartAdapter)
           cartTotalPrice.text =viewModel.cartTotalPrice
        }


        val rvCart: RecyclerView = binding.rvCart
        rvCart.layoutManager = layoutManager
        rvCart.adapter = cartAdapter
        binding.include.cartImage.visibility = View.VISIBLE
        val cartText = "My Cart"
        binding.root.findViewById<TextView>(R.id.toolbar_text).text = cartText

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}