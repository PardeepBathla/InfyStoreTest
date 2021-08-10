package com.infy.infystore.ui.Cart

import com.infy.infystore.api.ApiHelper
import com.infy.infystore.database.RoomAppDb
import com.infy.infystore.database.entity.CartEntities
import com.infy.infystore.database.entity.ProductEntities

class CartRepository(private val db: RoomAppDb) {

    suspend fun insert(item: CartEntities) = db.getCartDao()?.insert(item)
    suspend fun deleteTable() = db.getCartDao()?.deleteTable()
     fun fetchProducts() = db.getCartDao()?.getAllProductItems()
}