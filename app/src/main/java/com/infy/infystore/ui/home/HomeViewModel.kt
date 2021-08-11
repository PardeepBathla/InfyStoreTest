package com.infy.infystore.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.infy.infystore.database.RoomAppDb
import com.infy.infystore.database.entity.ProductEntities
import com.infy.infystore.utils.Resource
import kotlinx.coroutines.Dispatchers

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class HomeViewModel(private val homeRepository: HomeRepository,private val roomAppDb: RoomAppDb) : ViewModel() {

     var dbProducts:List<ProductEntities> ? = null

    fun insert(item: ProductEntities) = GlobalScope.launch {
        homeRepository.insert(item)
    }

    fun fetchProducts(): List<ProductEntities>? {
            try {
                dbProducts =  homeRepository.fetchProducts()
                Log.d("ff", "fetchProducts: ")
            } catch (e: Exception) {
            }

        return dbProducts
    }

    fun deleteTable() = GlobalScope.launch {
        homeRepository.deleteTable()
    }


    fun getProducts() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = homeRepository.getProductsApi()))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }
}