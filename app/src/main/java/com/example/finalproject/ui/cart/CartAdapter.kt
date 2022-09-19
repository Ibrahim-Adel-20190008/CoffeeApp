package com.example.finalproject.ui.cart

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.finalproject.R
import com.example.finalproject.dataBase.localDB.Item

class CartAdapter() :
    RecyclerView.Adapter<CartAdapter.ViewHolder>() {
    var cartItems: ArrayList<Item>? = ArrayList()
    private lateinit var mListener: OnItemClickListener

    interface OnItemClickListener{
        fun onDeleteCLick(position: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener){
        mListener = listener
    }

    inner class ViewHolder(itemView: View,listener: OnItemClickListener) : RecyclerView.ViewHolder(itemView){
        var cartImage: ImageView
        var cartTitle: TextView
        var tvQuantity: TextView
        var tvQuantityNum: TextView
        var tvPrice: TextView
        var tvEgp: TextView
        var deleteItem:ImageView

        init {
            cartImage = itemView.findViewById(R.id.cart_Image)
            cartTitle = itemView.findViewById(R.id.cart_title)
            tvQuantity = itemView.findViewById(R.id.tv_quantity)
            tvQuantityNum = itemView.findViewById(R.id.tv_quantityNum)
            tvPrice = itemView.findViewById(R.id.item_cart_price)
            tvEgp = itemView.findViewById(R.id.tv_egp)
            deleteItem = itemView.findViewById((R.id.image_delete))

            deleteItem.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener.onDeleteCLick(position)
                }
            }

        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_cart, parent, false)
        return ViewHolder(v,mListener)
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

    override fun getItemCount(): Int = cartItems?.size ?: 0

}