package com.infy.infystore.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.infy.infystore.database.entity.CartEntities

@Dao
interface CartDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: CartEntities)

    @Query("DELETE FROM table_cart")
    suspend fun deleteTable()

    @Query("DELETE FROM table_cart WHERE productName = :name")
    suspend fun deleteCartItem(name: String?)

//    @Query("DELETE FROM current_order_items WHERE dish_id = :dishId")
//    fun deleteByDishId()

    @Query("SELECT * FROM table_cart WHERE email = :email")
      fun getAllProductItems(email: String?): List<CartEntities>
}