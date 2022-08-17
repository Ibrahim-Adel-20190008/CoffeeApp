package com.example.finalproject

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.finalproject.dataclasses.CoffeeItem

class ProductListAdapter( var listener:onListener):RecyclerView.Adapter<ProductListAdapter.ViewHolder>() {
    var Coffees: ArrayList<CoffeeItem> = arrayListOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.card_layout,parent,false)
        return ViewHolder(v,listener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val coffeeItem = Coffees.get(position)
        holder.itemTitle.text = coffeeItem.name
        holder.itemDescription.text = coffeeItem.description

        Glide.with(holder.itemView)
            .load(coffeeItem.urlToImg)
            .into(holder.itemImage)
    }

    override fun getItemCount(): Int {
        return Coffees.size
    }

    inner class ViewHolder(itemView: View, listener: onListener):RecyclerView.ViewHolder(itemView),View.OnClickListener{
        var itemImage:ImageView
        var itemTitle:TextView
        var itemDescription:TextView
        var onlistener: onListener
        init {
            itemImage = itemView.findViewById(R.id.item_image)
            itemTitle = itemView.findViewById(R.id.item_title)
            itemDescription = itemView.findViewById(R.id.item_description)
            itemView.setOnClickListener(this)
            onlistener = listener
        }
        override fun onClick(p0: View?) {
            onlistener.onClick(adapterPosition)
        }
    }
    interface onListener
    {
        fun onClick(position: Int)
    }


}