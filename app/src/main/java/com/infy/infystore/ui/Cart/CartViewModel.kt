package com.infy.infystore.ui.Cart

import android.util.Log
import androidx.lifecycle.ViewModel
import com.infy.infystore.database.RoomAppDb
import com.infy.infystore.database.entity.CartEntities
import com.infy.infystore.storage.Preference
import com.infy.infystore.utils.GlobalConstants
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class CartViewModel(private val cartRepository: CartRepository, private val roomAppDb: RoomAppDb) :
    ViewModel() {

    private var dbCartProducts: List<CartEntities>? = null

    fun insert(item: CartEntities) = GlobalScope.launch {
        cartRepository.insert(item)
    }

    fun deleteCart() = GlobalScope.launch {
        cartRepository.deleteTable()
    }

    fun deleteCartItem(itemName: String) = GlobalScope.launch {
        cartRepository.deleteCartItem(itemName)
    }


    fun fetchProducts(): List<CartEntities>? {
        try {
                dbCartProducts = cartRepository.fetchProducts(Preference.instance.getPreferenceString(GlobalConstants.EMAIL))
                Log.d("ff", "fetchProducts: ")

        } catch (e: Exception) {
        }

        return dbCartProducts
    }

}