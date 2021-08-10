package com.infy.infystore.ui.Cart

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.infy.infystore.database.RoomAppDb
import com.infy.infystore.database.entity.CartEntities
import com.infy.infystore.database.entity.ProductEntities
import com.infy.infystore.ui.home.HomeRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CartViewModel(private val cartRepository: CartRepository, private val roomAppDb: RoomAppDb) : ViewModel() {

    private var dbCartProducts: List<CartEntities>? = null

    fun insert(item: CartEntities) = GlobalScope.launch {
        cartRepository.insert(item)
    }


    fun fetchProducts(): List<CartEntities>? {

            try {
              dbCartProducts = cartRepository.fetchProducts()
                Log.d("ff", "fetchProducts: ")
            } catch (e: Exception) {
            }

        return dbCartProducts
    }

}