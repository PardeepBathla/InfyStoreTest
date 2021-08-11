package com.infy.infystore.database.dao

import androidx.room.*
import com.infy.infystore.database.entity.ProductEntities

@Dao
interface ProductsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item:ProductEntities)

    @Query("DELETE FROM table_products")
    suspend fun deleteTable()

    @Query("SELECT * FROM table_products")
     fun getAllProductItems(): List<ProductEntities>
}