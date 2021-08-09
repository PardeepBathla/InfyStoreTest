package com.infy.infystore.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.infy.infystore.ui.home.HomeRepository
import com.infy.infystore.ui.home.HomeViewModel

class ProductsViewModelFactory(private val repository: HomeRepository):ViewModelProvider.NewInstanceFactory() {

//    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
//        return HomeViewModel(repository) as T
//    }
}