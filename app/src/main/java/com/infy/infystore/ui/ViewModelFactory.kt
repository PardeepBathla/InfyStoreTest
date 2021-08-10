package com.infy.infystore.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.infy.infystore.api.ApiHelper
import com.infy.infystore.database.RoomAppDb
import com.infy.infystore.ui.Cart.CartRepository
import com.infy.infystore.ui.Cart.CartViewModel
import com.infy.infystore.ui.home.HomeRepository
import com.infy.infystore.ui.home.HomeViewModel

class ViewModelFactory(private val apiHelper: ApiHelper,private val roomAppDb: RoomAppDb) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(HomeRepository(roomAppDb,apiHelper),roomAppDb) as T
        }
        if (modelClass.isAssignableFrom(CartViewModel::class.java)) {
            return CartViewModel(CartRepository(roomAppDb),roomAppDb) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }

}