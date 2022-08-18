package com.example.finalproject.activities

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.finalproject.R
import com.example.finalproject.dataClasses.CoffeeItem
import com.example.finalproject.localDataBase.Item
import com.example.finalproject.localDataBase.SharedList


class PreferencesActivity : AppCompatActivity() {
    private lateinit var img: ImageView
    private lateinit var arrowBack: ImageView
    private lateinit var sImg: ImageView
    private lateinit var mImg: ImageView
    private lateinit var lImg: ImageView
    private lateinit var noSugar: ImageView
    private lateinit var sugar: ImageView
    private lateinit var milk: ImageView
    private lateinit var chocolate: ImageView
    private lateinit var tvTitle: TextView
    private lateinit var tvPrice: TextView
    private lateinit var tvInc: TextView
    private lateinit var tvDec: TextView
    private lateinit var tvNum: TextView
    private lateinit var totalPrice: TextView
    private lateinit var addToCart: Button
    private var priceSmall = 0.0
    private var priceMedium: Double = 0.0
    private var priceLarge: Double = 0.0

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
        arrowBack = findViewById(R.id.arrow_back)


        val coffeeObject = intent.getParcelableExtra<CoffeeItem>("Selected_Item")
        priceSmall = coffeeObject?.price.toString().toDouble()
        priceMedium = priceSmall * 1.5
        priceLarge = priceSmall * 2

        tvTitle.text = coffeeObject?.name
        tvPrice.text = priceSmall.toString()
        totalPrice.text = priceSmall.toString()
        Glide.with(this).load(coffeeObject?.urlToImg).into(img)

        // to hide app toolabar
        supportActionBar?.hide()

        // increment and decrement button
        tvInc.setOnClickListener {
            tvNum.text = (tvNum.text.toString().toInt() + 1).toString()
            tvPrice.text = (tvPrice.text.toString().toDouble() + priceSmall).toString()
            calculateTotal()
        }
        tvDec.setOnClickListener {
            if (tvNum.text.toString().toInt() == 1) {
                Toast.makeText(this, "You can't order less than 1 item", Toast.LENGTH_SHORT).show()
            } else {
                tvNum.text = (tvNum.text.toString().toInt() - 1).toString()
                tvPrice.text = (tvPrice.text.toString().toDouble() - priceSmall).toString()
                calculateTotal()
            }
        }

        // click back
        arrowBack.setOnClickListener {
            finish()
        }

        // size small, medium, large
        lImg.setOnClickListener {
            lImg.setBackgroundResource(R.drawable.select_item)
            lImg.tag = R.drawable.select_item
            sImg.setBackgroundResource(R.drawable.non_select_item)
            sImg.tag = R.drawable.non_select_item
            mImg.tag = R.drawable.non_select_item
            mImg.setBackgroundResource(R.drawable.non_select_item)
            calculateTotal()
        }

        sImg.setOnClickListener {
            sImg.setBackgroundResource(R.drawable.select_item)
            sImg.tag = R.drawable.select_item
            lImg.setBackgroundResource(R.drawable.non_select_item)
            lImg.tag = R.drawable.non_select_item
            mImg.tag = R.drawable.non_select_item
            mImg.setBackgroundResource(R.drawable.non_select_item)
            calculateTotal()
        }
        mImg.setOnClickListener {
            mImg.setBackgroundResource(R.drawable.select_item)
            mImg.tag = R.drawable.select_item
            lImg.setBackgroundResource(R.drawable.non_select_item)
            sImg.tag = R.drawable.non_select_item
            lImg.tag = R.drawable.non_select_item
            sImg.setBackgroundResource(R.drawable.non_select_item)
            calculateTotal()
        }

        // with sugar or without sugar
        sugar.setOnClickListener {
            noSugar.setBackgroundResource(R.drawable.non_select_item)
            sugar.setBackgroundResource(R.drawable.select_item)
        }
        noSugar.setOnClickListener {
            noSugar.setBackgroundResource(R.drawable.select_item)
            sugar.setBackgroundResource(R.drawable.non_select_item)
        }

        // additions (milk, chocolate)
        milk.setOnClickListener {
            if (milk.tag != null && milk.tag.equals(R.drawable.select_item)) {
                milk.setBackgroundResource(R.drawable.non_select_item)
                milk.tag = R.drawable.non_select_item
            } else {
                milk.setBackgroundResource(R.drawable.select_item)
                milk.tag = R.drawable.select_item
            }
            calculateTotal()
        }
        chocolate.setOnClickListener {
            if (chocolate.tag != null && chocolate.tag.equals(R.drawable.select_item)) {
                chocolate.setBackgroundResource(R.drawable.non_select_item)
                chocolate.tag = R.drawable.non_select_item
            } else {
                chocolate.setBackgroundResource(R.drawable.select_item)
                chocolate.tag = R.drawable.select_item
            }
            calculateTotal()
        }

        addToCart.setOnClickListener {
            val item = Item(
                coffeeObject?.urlToImg,
                coffeeObject?.name,
                totalPrice.text.toString().toDouble(),
                tvNum.text.toString().toInt()
            )
            SharedList.add(item)
            Log.d("@@@", SharedList.getAllItems().toString())
            Toast.makeText(this, "Successfully Order Check Cart To See It", Toast.LENGTH_SHORT)
                .show()
            finish()
        }
        // to select default data and calculate total at start
        defaultSelect()
        calculateTotal()


    }

    fun defaultSelect() {
        sImg.setBackgroundResource(R.drawable.select_item)
        noSugar.setBackgroundResource(R.drawable.select_item)
    }

    fun calculateTotal() {
        val number = tvNum.text.toString().toInt()
        var additions = 50
        var totalPriceValue = 0.0
        if (milk.tag != null && milk.tag.equals(R.drawable.select_item)) {
            totalPriceValue += (number * additions)
        }
        if (chocolate.tag != null && chocolate.tag.equals(R.drawable.select_item)) {
            totalPriceValue += (number * additions)
        }

        if (mImg.tag != null && mImg.tag.equals(R.drawable.select_item)) {
            totalPriceValue += (number * priceMedium)
        } else if (lImg.tag != null && lImg.tag.equals(R.drawable.select_item)) {
            totalPriceValue += (number * priceLarge)
        } else {
            totalPriceValue += (number * priceSmall)
        }
        totalPrice.text = totalPriceValue.toString()
    }
}