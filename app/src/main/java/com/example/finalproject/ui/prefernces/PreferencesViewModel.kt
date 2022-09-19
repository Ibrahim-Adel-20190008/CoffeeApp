package com.example.finalproject.ui.prefernces

import androidx.lifecycle.ViewModel
import com.example.finalproject.R
import com.example.finalproject.databinding.FragmentPreferencesBinding
import com.example.finalproject.dataBase.localDB.Item
import com.example.finalproject.dataBase.localDB.SharedList
import com.example.finalproject.dataBase.localDB.SharedPre
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlin.properties.Delegates

@HiltViewModel
class PreferencesViewModel @Inject constructor() : ViewModel(){
    lateinit var binding: FragmentPreferencesBinding
    var priceSmall by Delegates.notNull<Double>()
    fun assignBinding(newBinding: FragmentPreferencesBinding, newPriceSmall:Double){
        binding = newBinding
        priceSmall = newPriceSmall
    }
    fun inc(){
        binding.number.text = (binding.number.text.toString().toInt() + 1).toString()
        binding.itemPrice.text = (binding.itemPrice.text.toString().toDouble() + priceSmall).toString()
        calculateTotal()
    }
    fun dec() {
            binding.number.text = (binding.number.text.toString().toInt() - 1).toString()
            binding.itemPrice.text = (binding.itemPrice.text.toString().toDouble() - priceSmall).toString()
            calculateTotal()
    }

    fun selectLarge() {
        binding.large.setBackgroundResource(R.drawable.select_item)
        binding.large.tag = R.drawable.select_item
        binding.small.setBackgroundResource(R.drawable.non_select_item)
        binding.small.tag = R.drawable.non_select_item
        binding.medium.tag = R.drawable.non_select_item
        binding.medium.setBackgroundResource(R.drawable.non_select_item)
        calculateTotal()
    }

    fun selectSmall() {
        binding.small.setBackgroundResource(R.drawable.select_item)
        binding.small.tag = R.drawable.select_item
        binding.large.setBackgroundResource(R.drawable.non_select_item)
        binding.large.tag = R.drawable.non_select_item
        binding.medium.tag = R.drawable.non_select_item
        binding.medium.setBackgroundResource(R.drawable.non_select_item)
        calculateTotal()
    }
    fun selectMedium() {
        binding.medium.setBackgroundResource(R.drawable.select_item)
        binding.medium.tag = R.drawable.select_item
        binding.large.setBackgroundResource(R.drawable.non_select_item)
        binding.small.tag = R.drawable.non_select_item
        binding.large.tag = R.drawable.non_select_item
        binding.small.setBackgroundResource(R.drawable.non_select_item)
        calculateTotal()
    }

    fun selectSugar() {
        binding.noSugar.setBackgroundResource(R.drawable.non_select_item)
        binding.sugar.setBackgroundResource(R.drawable.select_item)
    }
    fun selectNoSugar() {
        binding.noSugar.setBackgroundResource(R.drawable.select_item)
        binding.sugar.setBackgroundResource(R.drawable.non_select_item)

    }

    fun selectMilk() {
        if (binding.milk.tag != null && binding.milk.tag.equals(R.drawable.select_item)) {
            binding.milk.setBackgroundResource(R.drawable.non_select_item)
            binding.milk.tag = R.drawable.non_select_item
        } else {
            binding.milk.setBackgroundResource(R.drawable.select_item)
            binding.milk.tag = R.drawable.select_item
        }
        calculateTotal()
    }
    fun selectChocolate() {
        if (binding.chocolate.tag != null && binding.chocolate.tag.equals(R.drawable.select_item)) {
            binding.chocolate.setBackgroundResource(R.drawable.non_select_item)
            binding.chocolate.tag = R.drawable.non_select_item
        } else {
            binding.chocolate.setBackgroundResource(R.drawable.select_item)
            binding.chocolate.tag = R.drawable.select_item
        }
        calculateTotal()
    }

    fun addToCart(url:String?,name:String?,sharedList: SharedList) {
        val item = Item(
            url,
            name,
            binding.total.text.toString().toDouble(),
            binding.number.text.toString().toInt()
        )
        val oldList =SharedPre.getUserCart().items
        oldList?.let {
            for (i in oldList) {
                sharedList.add(i)
            }
        }
        sharedList.add(item)
        SharedPre.setUserCart(sharedList)
    }


    fun calculateTotal() {
        val number = binding.number.text.toString().toInt()
        val additions = 50
        val priceMedium = priceSmall * 1.5
        val priceLarge = priceSmall * 2
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
    fun defaultSelect() {
        binding.small.setBackgroundResource(R.drawable.select_item)
        binding.noSugar.setBackgroundResource(R.drawable.select_item)
    }
    
}