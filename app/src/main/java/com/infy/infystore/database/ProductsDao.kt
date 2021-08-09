package com.infy.infystore.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.infy.infystore.database.entity.ProductItems
import com.infy.infystore.ui.home.homeModal.ProductModal

@Dao
interface ProductsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item:ProductItems)

    @Delete
    suspend fun delete(item:ProductItems)

    @Query("SELECT * FROM products_list")
    fun getAllProductItems(): LiveData<List<ProductItems>>
}