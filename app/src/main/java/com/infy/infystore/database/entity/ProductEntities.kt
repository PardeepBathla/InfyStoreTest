package com.infy.infystore.database.entity

import androidx.room.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.infy.infystore.ui.home.homeModal.ProductModal


@Entity(tableName = "table_products")
data class ProductEntities(

    @PrimaryKey(autoGenerate = true) val id: Int = 0,
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