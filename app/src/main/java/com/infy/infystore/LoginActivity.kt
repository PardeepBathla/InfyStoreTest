package com.infy.infystore

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.infy.infystore.databinding.ActivityLoginBinding
import com.infy.infystore.utils.Utils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       binding =  ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loginBtn.setOnClickListener {
            startActivity(Intent(this,MainActivity::class.java))
        }


        retrofit();
    }

    private fun retrofit() {

         val api= Retrofit.Builder().baseUrl(Utils.BASE_URL)
             .addConverterFactory(GsonConverterFactory.create())
             .build()
             .create(MyApi::class.java)

        GlobalScope.launch(Dispatchers.IO) {

//            networkCall(api)

            val result = async { networkCall(api)}
            result.await()

            for (prod in result.await()!!) {
                Log.d("retrofit", prod.title)
            }


            val result1 = async { networkCall(api)}
            for (prod in result.await()!!) {
                Log.d("retrofit", prod.title)
            }
        }

    }

    private suspend fun networkCall(api: MyApi): List<Category>? {
        var list:List<Category>? = null
        val products = api.getProducts()
        if (products.isSuccessful) {
            list =  products.body()?.categories
        }
        return list
    }

//    private suspend fun networkCall(api: MyApi){
//        val products = api.getProducts()
//        if (products.isSuccessful) {
//            for (prod in products.body()?.categories!!) {
//                Log.d("retrofit", prod.title)
//            }
//        }
//
//    }
}