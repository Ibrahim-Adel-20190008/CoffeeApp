package com.example.finalproject

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.finalproject.dataclasses.CoffeeItem


class Preferences : AppCompatActivity() {
    lateinit var img: ImageView
    lateinit var sImg: ImageView
    lateinit var mImg: ImageView
    lateinit var lImg: ImageView
    lateinit var noSugar: ImageView
    lateinit var sugar: ImageView
    lateinit var milk: ImageView
    lateinit var chocolate: ImageView
    lateinit var tvTitle: TextView
    lateinit var tvPrice: TextView
    lateinit var tvInc: TextView
    lateinit var tvDec: TextView
    lateinit var tvNum: TextView
    lateinit var totalPrice: TextView
    lateinit var addToCart: Button
    val priceSmall:Double = 100.0
    val priceMedium:Double = priceSmall*1.5
    val priceLarge:Double = priceSmall*2

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_preferences)

        img = findViewById(R.id.img_item)
        sImg = findViewById(R.id.small)
        mImg = findViewById(R.id.medium)
        lImg = findViewById(R.id.large)
        noSugar = findViewById(R.id.no_sugar)
        sugar = findViewById(R.id.sugar)
        milk = findViewById(R.id.milk)
        chocolate = findViewById(R.id.chocolate)
        tvTitle = findViewById(R.id.item_name)
        tvPrice = findViewById(R.id.item_price)
        tvInc = findViewById(R.id.increment_view)
        tvDec = findViewById(R.id.decrement_view)
        tvNum = findViewById(R.id.number)
        totalPrice = findViewById(R.id.total)
        addToCart = findViewById(R.id.add_to_cart)


        val coffeeObject = intent.getParcelableExtra<CoffeeItem>("Selected_Item")
        tvTitle.text = coffeeObject?.name
        tvPrice.text = priceSmall.toString()
        totalPrice.text = priceSmall.toString()
        Glide.with(this).load(coffeeObject?.urlToImg).into(img)

        // increment and decrement button
        tvInc.setOnClickListener {
            tvNum.text = (tvNum.text.toString().toInt() + 1).toString()
            tvPrice.text = (tvPrice.text.toString().toDouble() +priceSmall).toString()
            calculateTotal()
        }
        tvDec.setOnClickListener {
            if (tvNum.text.toString().toInt() == 1) {
                Toast.makeText(this, "You can't order less than 1 item", Toast.LENGTH_SHORT).show()
            } else {
                tvNum.text = (tvNum.text.toString().toInt() - 1).toString()
                tvPrice.text = (tvPrice.text.toString().toDouble() -priceSmall).toString()
                calculateTotal()
            }
        }

        // size small, medium, large
        lImg.setOnClickListener {
                lImg.setBackgroundResource(R.drawable.select_item)
                lImg.setTag(R.drawable.select_item)
                sImg.setBackgroundResource(R.drawable.non_select_item)
                sImg.setTag(R.drawable.non_select_item)
                mImg.setTag(R.drawable.non_select_item)
                mImg.setBackgroundResource(R.drawable.non_select_item)
                calculateTotal()
        }

        sImg.setOnClickListener{
            sImg.setBackgroundResource(R.drawable.select_item)
            sImg.setTag(R.drawable.select_item)
            lImg.setBackgroundResource(R.drawable.non_select_item)
            lImg.setTag(R.drawable.non_select_item)
            mImg.setTag(R.drawable.non_select_item)
            mImg.setBackgroundResource(R.drawable.non_select_item)
            calculateTotal()
        }
        mImg.setOnClickListener {
                mImg.setBackgroundResource(R.drawable.select_item)
                mImg.setTag(R.drawable.select_item)
                lImg.setBackgroundResource(R.drawable.non_select_item)
                sImg.setTag(R.drawable.non_select_item)
                lImg.setTag(R.drawable.non_select_item)
                sImg.setBackgroundResource(R.drawable.non_select_item)
                calculateTotal()
        }

        // with sugar or without sugar
        sugar.setOnClickListener{
            noSugar.setBackgroundResource(R.drawable.non_select_item)
            sugar.setBackgroundResource(R.drawable.select_item)
        }
        noSugar.setOnClickListener {
            noSugar.setBackgroundResource(R.drawable.select_item)
            sugar.setBackgroundResource(R.drawable.non_select_item)
        }

        // additions (milk, chocolate)
        milk.setOnClickListener {
            if(milk.getTag() != null && milk.getTag().equals(R.drawable.select_item)) {
                milk.setBackgroundResource(R.drawable.non_select_item)
                milk.setTag(R.drawable.non_select_item)
            }
            else{
                milk.setBackgroundResource(R.drawable.select_item)
                milk.setTag(R.drawable.select_item)
            }
            calculateTotal()
        }
        chocolate.setOnClickListener {
            if(chocolate.getTag() != null && chocolate.getTag().equals(R.drawable.select_item)) {
                chocolate.setBackgroundResource(R.drawable.non_select_item)
                chocolate.setTag(R.drawable.non_select_item)
            }
            else{
                chocolate.setBackgroundResource(R.drawable.select_item)
                chocolate.setTag(R.drawable.select_item)
            }
            calculateTotal()
        }

        // to select default data and calculate total at start
        defaultSelect()
        calculateTotal()
    }

    fun defaultSelect()
    {
        sImg.setBackgroundResource(R.drawable.select_item)
        noSugar.setBackgroundResource(R.drawable.select_item)
    }

    fun calculateTotal()
    {
        val number = tvNum.text.toString().toInt()
        var additions = 50
        var totalPriceValue = 0.0
        if(milk.getTag() != null && milk.getTag().equals(R.drawable.select_item)){
            totalPriceValue += (number *additions)
        }
        if(chocolate.getTag() != null && chocolate.getTag().equals(R.drawable.select_item)){
            totalPriceValue += (number *additions)
        }

        if(mImg.getTag() != null && mImg.getTag().equals(R.drawable.select_item)) {
            totalPriceValue += (number * priceMedium)
        }

        else if(lImg.getTag() != null && lImg.getTag().equals(R.drawable.select_item)){
            totalPriceValue +=  (number * priceLarge)
        }
        else
        {
            totalPriceValue +=  (number * priceSmall)
        }
        totalPrice.text = totalPriceValue.toString()
    }
}