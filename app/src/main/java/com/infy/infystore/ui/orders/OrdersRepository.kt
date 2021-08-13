package com.infy.infystore.ui.orders

import com.infy.infystore.database.RoomAppDb
import com.infy.infystore.database.entity.CartEntities
import com.infy.infystore.database.entity.OrdersEntities

class OrdersRepository(private val db: RoomAppDb) {
    suspend fun insert(item: OrdersEntities) = db.getOrdersDao()?.insert(item)
    fun fetchProducts(email: String?) = db.getOrdersDao()?.getAllProductItems(email)
}