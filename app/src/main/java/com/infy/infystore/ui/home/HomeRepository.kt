package com.infy.infystore.ui.home


import com.infy.infystore.api.ApiHelper
import com.infy.infystore.database.RoomAppDb
import com.infy.infystore.database.entity.ProductEntities

class HomeRepository (private val db: RoomAppDb,private val apiHelper: ApiHelper){
    suspend fun insert(item: ProductEntities) = db.getProductDao()?.insert(item)
    suspend fun deleteTable() = db.getProductDao()?.deleteTable()
     suspend fun fetchProducts() = db.getProductDao()?.getAllProductItems()

    suspend fun getProductsApi() = apiHelper.getProducts()

}


/*
private fun getProducts() {

    val api= Retrofit.Builder().baseUrl(Utils.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ApiService::class.java)

    GlobalScope.launch(Dispatchers.IO) {
            networkCall(api)

//        val result = async { networkCall(api)}
//        result.await()
//
//        for (prod in result.await()!!) {
//            Log.d("retrofit", prod.title)
//        }
//
//
//        val result1 = async { networkCall(api)}
//        for (prod in result.await()!!) {
//            Log.d("retrofit", prod.title)
//        }
    }

}

//private suspend fun networkCall(api: MyApi): List<Category>? {
//    var list:List<Category>? = null
//    val products = api.getProducts()
//    if (products.isSuccessful) {
//        list =  products.body()?.categories
//    }
//    return list
//}

    private suspend fun networkCall(apiService: ApiService){
        val products = apiService.getProducts()
        if (products.isSuccessful) {
            for (prod in products.body()?.categories!!) {
                Log.d("retrofit", prod.title)
            }
        }

    }*/
