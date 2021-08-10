package com.infy.infystore.api

import com.infy.infystore.DummyProduct
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("categories")
    suspend fun getProducts(): DummyProduct
}