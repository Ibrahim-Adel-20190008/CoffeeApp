package com.example.finalproject.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.finalproject.R
import com.example.finalproject.dataClasses.CoffeeItem
import com.example.finalproject.databinding.FragmentPreferencesBinding
import com.example.finalproject.localDataBase.Item
import com.example.finalproject.localDataBase.SharedList
import com.example.finalproject.localDataBase.SharedPre
import com.google.android.material.bottomnavigation.BottomNavigationView

class PreferencesFragment : Fragment() {
    private var priceSmall = 0.0
    private var priceMedium: Double = 0.0
    private var priceLarge: Double = 0.0
    private var sharedList: SharedList = SharedList()
    private var _binding: FragmentPreferencesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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

        activity?.findViewById<BottomNavigationView>(R.id.bottom_nav)?.setVisibility(View.GONE)
        binding.include.arrowBack.setVisibility(View.VISIBLE)
        binding.root.findViewById<ImageView>(R.id.arrow_back).setOnClickListener {
            activity?.onBackPressed()
        }

        // increment and decrement button
        binding.incrementView.setOnClickListener {
            binding.number.text = (binding.number.text.toString().toInt() + 1).toString()
            binding.itemPrice.text = (binding.itemPrice.text.toString().toDouble() + priceSmall).toString()
            calculateTotal()
        }
        binding.decrementView.setOnClickListener {
            if (binding.number.text.toString().toInt() == 1) {
                Toast.makeText(activity, "You can't order less than 1 item", Toast.LENGTH_SHORT).show()
            } else {
                binding.number.text = (binding.number.text.toString().toInt() - 1).toString()
                binding.itemPrice.text = (binding.itemPrice.text.toString().toDouble() - priceSmall).toString()
                calculateTotal()
            }
        }
        // size small, medium, large
        binding.large.setOnClickListener {
            binding.large.setBackgroundResource(R.drawable.select_item)
            binding.large.tag = R.drawable.select_item
            binding.small.setBackgroundResource(R.drawable.non_select_item)
            binding.small.tag = R.drawable.non_select_item
            binding.medium.tag = R.drawable.non_select_item
            binding.medium.setBackgroundResource(R.drawable.non_select_item)
            calculateTotal()
        }

        binding.small.setOnClickListener {
            binding.small.setBackgroundResource(R.drawable.select_item)
            binding.small.tag = R.drawable.select_item
            binding.large.setBackgroundResource(R.drawable.non_select_item)
            binding.large.tag = R.drawable.non_select_item
            binding.medium.tag = R.drawable.non_select_item
            binding.medium.setBackgroundResource(R.drawable.non_select_item)
            calculateTotal()
        }
        binding.medium.setOnClickListener {
            binding.medium.setBackgroundResource(R.drawable.select_item)
            binding.medium.tag = R.drawable.select_item
            binding.large.setBackgroundResource(R.drawable.non_select_item)
            binding.small.tag = R.drawable.non_select_item
            binding.large.tag = R.drawable.non_select_item
            binding.small.setBackgroundResource(R.drawable.non_select_item)
            calculateTotal()
        }

        // with sugar or without sugar
        binding.sugar.setOnClickListener {
            binding.noSugar.setBackgroundResource(R.drawable.non_select_item)
            binding.sugar.setBackgroundResource(R.drawable.select_item)
        }
        binding.noSugar.setOnClickListener {
            binding.noSugar.setBackgroundResource(R.drawable.select_item)
            binding.sugar.setBackgroundResource(R.drawable.non_select_item)
        }

        // additions (binding.milk, binding.chocolate)
        binding.milk.setOnClickListener {
            if (binding.milk.tag != null && binding.milk.tag.equals(R.drawable.select_item)) {
                binding.milk.setBackgroundResource(R.drawable.non_select_item)
                binding.milk.tag = R.drawable.non_select_item
            } else {
                binding.milk.setBackgroundResource(R.drawable.select_item)
                binding.milk.tag = R.drawable.select_item
            }
            calculateTotal()
        }
        binding.chocolate.setOnClickListener {
            if (binding.chocolate.tag != null && binding.chocolate.tag.equals(R.drawable.select_item)) {
                binding.chocolate.setBackgroundResource(R.drawable.non_select_item)
                binding.chocolate.tag = R.drawable.non_select_item
            } else {
                binding.chocolate.setBackgroundResource(R.drawable.select_item)
                binding.chocolate.tag = R.drawable.select_item
            }
            calculateTotal()
        }

        binding.addToCart.setOnClickListener {
            val item = Item(
                coffeeObject?.urlToImg,
                coffeeObject?.name,
                binding.total.text.toString().toDouble(),
                binding.number.text.toString().toInt()
            )
            sharedList = SharedPre.getUserCart()
            sharedList.add(item)
            SharedPre.setUserCart(sharedList)
            Log.d("@@@", sharedList.getAllItems().toString())
            Toast.makeText(activity, "Successfully Order Check Cart To See It", Toast.LENGTH_SHORT)
                .show()
            activity?.onBackPressed()
        }
        // to select default data and calculate total at start
        defaultSelect()
        calculateTotal()

    }
    fun defaultSelect() {
        binding.small.setBackgroundResource(R.drawable.select_item)
        binding.noSugar.setBackgroundResource(R.drawable.select_item)
    }

    fun calculateTotal() {
        val number = binding.number.text.toString().toInt()
        val additions = 50
        var totalPriceValue = 0.0
        if (binding.milk.tag != null && binding.milk.tag.equals(R.drawable.select_item)) {
            totalPriceValue += (number * additions)
        }
        if (binding.chocolate.tag != null && binding.chocolate.tag.equals(R.drawable.select_item)) {
            totalPriceValue += (number * additions)
        }

        if (binding.medium.tag != null && binding.medium.tag.equals(R.drawable.select_item)) {
            totalPriceValue += (number * priceMedium)
        } else if (binding.large.tag != null && binding.large.tag.equals(R.drawable.select_item)) {
            totalPriceValue += (number * priceLarge)
        } else {
            totalPriceValue += (number * priceSmall)
        }
        binding.total.text = totalPriceValue.toString()
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}