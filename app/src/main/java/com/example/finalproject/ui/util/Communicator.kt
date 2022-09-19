package com.example.finalproject.ui.util

import com.example.finalproject.dataBase.remoteDB.models.CoffeeItem

interface Communicator {
fun passData(item : CoffeeItem?)
}
