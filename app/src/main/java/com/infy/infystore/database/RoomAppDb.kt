package com.infy.infystore.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.infy.infystore.database.dao.CartDao
import com.infy.infystore.database.dao.OrdersDao
import com.infy.infystore.database.dao.ProductsDao
import com.infy.infystore.database.entity.*

@Database(entities = [ProductEntities::class,CartEntities::class,OrdersEntities::class], version = 1)
@TypeConverters(RoomTypeConverters::class,OrdersTypeConverters::class)
abstract class RoomAppDb: RoomDatabase() {

    abstract fun getProductDao(): ProductsDao?
    abstract fun getCartDao(): CartDao?
    abstract fun getOrdersDao(): OrdersDao?

    companion object {
        private var INSTANCE: RoomAppDb?= null

        fun getAppDatabase(context: Context): RoomAppDb? {

            if(INSTANCE == null ) {

                INSTANCE = Room.databaseBuilder<RoomAppDb>(
                    context.applicationContext, RoomAppDb::class.java, "AppDBB"
                )
                    .allowMainThreadQueries()
                    .build()

            }
            return INSTANCE
        }
    }
}