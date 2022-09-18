package com.example.finalproject.localDataBase

data class Item(
    var urlToImg: String? = null,
    var name: String? = null,
    var totalPrice: Double? = null,
    var quantity: Int? = null
)

class SharedList {
    var items: ArrayList<Item>? = ArrayList()
    fun add(item: Item) {
        items?.add(item)
    }
    fun addAll(items: ArrayList<Item>?) {
        items?.addAll(items)
    }

    fun removeAt(index:Int){
        items?.removeAt(index)
    }
    fun clear(){
        items?.clear()
    }

    fun getAllItems(): ArrayList<Item>? {
        return items
    }
}