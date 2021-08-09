package com.infy.infystore

import android.telecom.Call
import com.infy.infystore.ui.home.homeModal.ProductModal
import retrofit2.Response
import retrofit2.http.GET

interface MyApi {
    @GET("categories")
    suspend fun getProducts(): Response<DummyProduct>
}