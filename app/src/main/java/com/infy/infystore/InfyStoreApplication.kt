package com.infy.infystore

import android.app.Application

class InfyStoreApplication : Application() {

    companion object {
        var mInstance: InfyStoreApplication? = null
    @Synchronized
    public fun getInstance(): InfyStoreApplication? {
        return mInstance
    }
}
    override fun onCreate() {
        super.onCreate()

        mInstance = this
    }

}