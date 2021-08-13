package com.infy.infystore.ui.orders

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.infy.infystore.database.RoomAppDb
import com.infy.infystore.database.entity.CartEntities
import com.infy.infystore.database.entity.OrdersEntities
import com.infy.infystore.storage.Preference
import com.infy.infystore.utils.GlobalConstants
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class OrdersViewModel(private val ordersRepository: OrdersRepository,roomAppDb: RoomAppDb) : ViewModel() {

    private var dbCartProducts: List<OrdersEntities>? = null

    fun insert(item: OrdersEntities) = GlobalScope.launch {
        ordersRepository.insert(item)
    }

    fun fetchProducts(): List<OrdersEntities>? {
        try {
            dbCartProducts = ordersRepository.fetchProducts(
                Preference.instance.getPreferenceString(
                    GlobalConstants.EMAIL))
            Log.d("ff", "fetchProducts: ")

        } catch (e: Exception) {
        }

        return dbCartProducts
    }

}