package com.infy.infystore.api

class ApiHelper(private val apiService: ApiService) {

    suspend fun getProducts() = apiService.getProducts()
}