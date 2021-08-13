package com.infy.infystore.ui.home


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.infy.infystore.DummyProduct
import com.infy.infystore.api.ApiHelper
import com.infy.infystore.api.ApiService
import com.infy.infystore.api.RetrofitBuilder
import com.infy.infystore.database.RoomAppDb
import com.infy.infystore.database.entity.ProductEntities
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeRepository (private val db: RoomAppDb,private val apiHelper: ApiHelper){
    suspend fun insert(item: ProductEntities) = db.getProductDao()?.insert(item)
    suspend fun deleteTable() = db.getProductDao()?.deleteTable()
      fun fetchProducts() = db.getProductDao()?.getAllProductItems()

    suspend fun getProductsApi() = apiHelper.getProducts()


    private var apiInterface:ApiService?=null


    init {
        apiInterface = RetrofitBuilder.apiService
    }

    fun getAllPosts(): LiveData<DummyProduct> {

        val data = MutableLiveData<DummyProduct>()


        apiInterface?.getProducts()?.enqueue(object : Callback<DummyProduct> {
            override fun onResponse(call: Call<DummyProduct>, response: Response<DummyProduct>) {
                val res = response.body()
                if (response.code() == 200 && res!=null){
                    data.value = res

                }else{
                    data.value = null
                }
            }

            override fun onFailure(call: Call<DummyProduct>, t: Throwable) {
                data.value = null
            }

        })

        return data
    }

}



