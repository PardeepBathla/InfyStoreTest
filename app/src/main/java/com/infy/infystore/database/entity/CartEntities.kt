package com.infy.infystore.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "table_cart")
data class CartEntities(
    @ColumnInfo(name = "productName") var itemName: String,
    @ColumnInfo(name = "productPrice") var itemPrice: String,
    @ColumnInfo(name = "productDesc") var itemDescp: String,
    @ColumnInfo(name = "productImage") var itemImage: String,
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null

}