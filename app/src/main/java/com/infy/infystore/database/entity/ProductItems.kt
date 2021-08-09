package com.infy.infystore.database.entity

import androidx.room.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.infy.infystore.ui.home.homeModal.ProductModal


@Entity(tableName = "products_list")
data class ProductItems(

    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "os") val os: String,
    @TypeConverters(RoomTypeConverters::class)
    @ColumnInfo(name = "products") val products: List<ProductModal>

)

class RoomTypeConverters {
    @TypeConverter
    fun fromString(listOfString: String?): List<ProductModal> {
        return Gson().fromJson(
            listOfString, object : TypeToken<List<ProductModal>>() {}.type
        )
    }

    @TypeConverter
    fun frmArrayList(listOfString: List<ProductModal?>?): String {
        return Gson().toJson(listOfString)
    }
}