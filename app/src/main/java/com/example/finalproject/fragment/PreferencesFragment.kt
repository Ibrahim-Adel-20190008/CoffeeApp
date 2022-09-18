package com.example.finalproject.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.example.finalproject.R
import com.example.finalproject.data.models.CoffeeItem
import com.example.finalproject.databinding.FragmentPreferencesBinding
import com.example.finalproject.localDataBase.Item
import com.example.finalproject.localDataBase.SharedList
import com.example.finalproject.localDataBase.SharedPre
import com.example.finalproject.main.viewmodels.PreferencesViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView

class PreferencesFragment : Fragment() {
    private var priceSmall = 0.0
    private var priceMedium: Double = 0.0
    private var priceLarge: Double = 0.0
    private var sharedList: SharedList = SharedList()
    private var _binding: FragmentPreferencesBinding? = null
    private val binding get() = _binding!!
    private val viewModel: PreferencesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPreferencesBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val coffeeObject = arguments?.getParcelable<CoffeeItem>("Selected Item")
        priceSmall = coffeeObject?.price.toString().toDouble()
        priceMedium = priceSmall * 1.5
        priceLarge = priceSmall * 2

        binding.itemName.text = coffeeObject?.name
        binding.itemPrice.text = priceSmall.toString()
        binding.total.text = priceSmall.toString()
        Glide.with(this).load(coffeeObject?.urlToImg).into(binding.imgItem)

        activity?.findViewById<BottomNavigationView>(R.id.bottom_nav)?.visibility = View.GONE
        binding.include.arrowBack.visibility = View.VISIBLE
        binding.root.findViewById<ImageView>(R.id.arrow_back).setOnClickListener {
            activity?.onBackPressed()
        }

        viewModel.assignBinding(binding,priceSmall)
        viewModel.calculateTotal()
        viewModel.defaultSelect()

        // increment and decrement button
        binding.incrementView.setOnClickListener {
            viewModel.inc()
        }
        binding.decrementView.setOnClickListener {
            if (binding.number.text.toString().toInt() == 1) {
                Toast.makeText(activity, "You can't order less than 1 item", Toast.LENGTH_SHORT).show()
            } else {
                viewModel.dec()
            }
        }

        // size small, medium, large
        binding.large.setOnClickListener {
            viewModel.selectLarge()
        }

        binding.small.setOnClickListener {
            viewModel.selectSmall()
        }
        binding.medium.setOnClickListener {
            viewModel.selectMedium()
        }

        // with sugar or without sugar
        binding.sugar.setOnClickListener {
            viewModel.selectSugar()
        }
        binding.noSugar.setOnClickListener {
            viewModel.selectNoSugar()
        }

        // additions (binding.milk, binding.chocolate)
        binding.milk.setOnClickListener {
            viewModel.selectMilk()
        }
        binding.chocolate.setOnClickListener {
            viewModel.selectChocolate()
        }
        
        binding.addToCart.setOnClickListener {
            viewModel.addToCart(coffeeObject?.urlToImg,coffeeObject?.name,sharedList)
            Toast.makeText(activity, "Successfully Order Check Cart To See It", Toast.LENGTH_SHORT)
                .show()
            activity?.onBackPressed()
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}