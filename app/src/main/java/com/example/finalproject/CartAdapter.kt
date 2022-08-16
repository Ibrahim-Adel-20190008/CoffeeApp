package com.example.finalproject

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.finalproject.sharedpref.Item

class CartAdapter(var cartItems: ArrayList<Item>?): RecyclerView.Adapter<CartAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        var cartImage: ImageView
        var cartTitle: TextView
        var tvQuantity : TextView
        var tvQuantityNum: TextView
        var tvPrice : TextView
        var tvEgp : TextView
        init {
            cartImage = itemView.findViewById(R.id.cart_Image)
            cartTitle = itemView.findViewById(R.id.cart_title)
            tvQuantity = itemView.findViewById(R.id.tv_quantity)
            tvQuantityNum = itemView.findViewById(R.id.tv_quantityNum)
            tvPrice = itemView.findViewById(R.id.item_cart_price)
            tvEgp = itemView.findViewById(R.id.tv_egp)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_cart,parent,false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val cartItem = cartItems?.get(position)
        holder.cartTitle.text = cartItem?.name
        holder.tvQuantityNum.text = cartItem?.quantity.toString()
        holder.tvPrice.text = cartItem?.totalPrice.toString()
        Glide.with(holder.itemView)
            .load(cartItem?.urlToImg)
            .into(holder.cartImage)
    }

    override fun getItemCount(): Int = cartItems?.size?: 0
}