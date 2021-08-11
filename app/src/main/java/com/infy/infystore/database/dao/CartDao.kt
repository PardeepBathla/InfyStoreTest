package com.infy.infystore.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.infy.infystore.database.entity.CartEntities
import com.infy.infystore.database.entity.ProductEntities

@Dao
interface CartDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item:CartEntities)

    @Query("DELETE FROM table_cart")
    suspend fun deleteTable()

    @Query("SELECT * FROM table_cart")
     suspend  fun getAllProductItems(): List<CartEntities>
}