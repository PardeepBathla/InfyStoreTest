package com.infy.infystore.ui.home


import com.infy.infystore.database.RoomAppDb
import com.infy.infystore.database.entity.ProductItems

class HomeRepository (private val db: RoomAppDb){
    suspend fun insert(item: ProductItems) = db.getProductDao()?.insert(item)
//    suspend fun delete(item: ProductItems) = db.getProductDao()?.delete(item)
//
//    fun allGroceryItems() = db.getProductDao().getAllProductItems()
}