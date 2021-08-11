package com.infy.infystore

import android.app.Application
import android.util.Log
import com.infy.infystore.utils.LogoutListener
import java.util.*

class InfyStoreApplication : Application() {

    companion object {
        private var logoutListener: LogoutListener? = null
        private var timer: Timer? = null
        var mInstance: InfyStoreApplication? = null

        @Synchronized
        public fun getInstance(): InfyStoreApplication? {
            return mInstance
        }


        public fun resetSession() {
            userSessionStart()
        }

        public fun registerSessionListener(listener: LogoutListener) {
            logoutListener = listener
        }

        fun userSessionStart() {
            if (timer != null) {
                timer!!.cancel()
            }
            timer = Timer()
            timer!!.schedule(object : TimerTask() {
                override fun run() {
                    if (logoutListener != null) {
                        logoutListener!!.onSessionLogout()
                        Log.d("App", "Session Destroyed")
                    }
                }
            }, 1000*60*10)
        }


    }

    override fun onCreate() {
        super.onCreate()

        mInstance = this
    }


}