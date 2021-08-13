package com.infy.infystore.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.infy.infystore.DummyProduct
import com.infy.infystore.database.RoomAppDb
import com.infy.infystore.database.entity.ProductEntities

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class HomeViewModel(private val homeRepository: HomeRepository,private val roomAppDb: RoomAppDb) : ViewModel() {

     var dbProducts:List<ProductEntities> ? = null
    var postModalLiveListView: LiveData<DummyProduct>?=null
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


    fun getProd(){
        postModalLiveListView = homeRepository.getAllProducts()
    }

    /*fun getProducts() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = homeRepository.getProductsApi()))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }*/

    fun updateTable() {

    }
}