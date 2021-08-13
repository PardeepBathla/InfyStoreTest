package com.infy.infystore.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.infy.infystore.database.entity.CartEntities
import com.infy.infystore.database.entity.OrdersEntities

@Dao
interface OrdersDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: OrdersEntities)

    @Query("SELECT * FROM table_orders WHERE email = :email")
    fun getAllProductItems(email: String?): List<OrdersEntities>
}