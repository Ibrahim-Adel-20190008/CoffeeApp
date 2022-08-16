package com.example.finalproject

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.finalproject.dataclasses.CoffeeItem

class ProductListAdapter(var Coffees: Array<CoffeeItem>):RecyclerView.Adapter<ProductListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.card_layout,parent,false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val coffeeItem = Coffees.get(position)
        holder.itemTitle.text = coffeeItem.name
        holder.itemNext.text=coffeeItem.next

        Glide.with(holder.itemView)
            .load(coffeeItem.urlToImg)
            .into(holder.itemImage)
    }

    override fun getItemCount(): Int {
        return Coffees.size
    }

    inner class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        var itemImage:ImageView
        var itemTitle:TextView
        var itemNext:TextView
        init {
            itemImage = itemView.findViewById(R.id.item_image)
            itemTitle = itemView.findViewById(R.id.item_title)
            itemNext = itemView.findViewById(R.id.item_next)
        }
    }



}