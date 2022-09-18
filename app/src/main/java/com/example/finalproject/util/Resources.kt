package com.example.finalproject.util

sealed class Resources<T>(val data: T?, val msg:String?) {
    class Success<T> (data :T): Resources<T>(data,null)
    class Error<T> (msg: String): Resources<T>(null,msg)
}