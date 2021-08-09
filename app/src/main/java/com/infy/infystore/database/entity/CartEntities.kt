package com.infy.infystore.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "cart_items")
data class CartEntities(
    @ColumnInfo(name = "productName") var itemName: String,
    @ColumnInfo(name = "productQty") var itemQuantity: Int,
    @ColumnInfo(name = "productPrice") var itemPrice: String,
    @ColumnInfo(name = "productDesc") var itemDescp: String,
    @ColumnInfo(name = "productId") var itemId: String
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null

}