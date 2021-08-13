package com.infy.infystore.database.entity


import androidx.room.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.infy.infystore.ui.home.homeModal.ProductModal


@Entity(tableName = "table_orders")
data class OrdersEntities(
    @ColumnInfo(name = "productPrice") var productPrice: String,
    @TypeConverters(OrdersTypeConverters::class)
    @ColumnInfo(name = "productsName") var productsName: List<String>,
    @ColumnInfo(name = "productImage") var productImage: String,
    @ColumnInfo(name = "email") var email: String,
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null


}

class OrdersTypeConverters {
    @TypeConverter
    fun fromString(listOfString: String?): List<String> {
        return Gson().fromJson(
            listOfString, object : TypeToken<List<String>>() {}.type
        )
    }

    @TypeConverter
    fun frmArrayList(listOfString: List<String?>?): String {
        return Gson().toJson(listOfString)
    }
}