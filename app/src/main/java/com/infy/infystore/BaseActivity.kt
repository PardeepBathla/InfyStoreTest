package com.infy.infystore

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.infy.infystore.utils.LogoutListener

open class BaseActivity : AppCompatActivity(),LogoutListener{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onResume() {
        super.onResume()
        //Set Listener to receive events
        InfyStoreApplication.registerSessionListener(this)
    }

    override fun onUserInteraction() {
        super.onUserInteraction()
        //reset session when user interact
        InfyStoreApplication.resetSession()
    }

    override fun onSessionLogout() {
        // Do You Task on session out

        Log.d("session", "onSessionLogout: ")
    }
}