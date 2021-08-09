package com.infy.infystore.ui.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.infy.infystore.database.RoomAppDb
import com.infy.infystore.database.entity.ProductItems
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    private var homeRepository: HomeRepository? = null
    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text

    init {
        homeRepository = RoomAppDb.getAppDatabase(application)?.let { HomeRepository(it) };


    }


    fun insert(item: ProductItems) = GlobalScope.launch {
        homeRepository?.insert(item)
    }
}