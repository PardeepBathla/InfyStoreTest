package com.infy.infystore.ui.Cart

import com.infy.infystore.database.RoomAppDb
import com.infy.infystore.database.entity.CartEntities

class CartRepository(private val db: RoomAppDb) {

    suspend fun insert(item: CartEntities) = db.getCartDao()?.insert(item)
    suspend fun deleteTable() = db.getCartDao()?.deleteTable()
    suspend fun deleteCartItem(itemName: String) = db.getCartDao()?.deleteCartItem(itemName)
      fun fetchProducts(email: String?) = db.getCartDao()?.getAllProductItems(email)
}