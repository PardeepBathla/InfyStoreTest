package com.infy.infystore

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowManager
import com.infy.infystore.storage.Preference
import com.infy.infystore.utils.GlobalConstants
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)


        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        Handler(Looper.myLooper()!!).postDelayed({
            if (Preference.instance.getPreferenceBoolean(GlobalConstants.IS_LOGIN)) {
                if (Preference.instance.getPreferenceBoolean(GlobalConstants.IS_PRIVACY_ACCEPTED)) {
                    startActivity(Intent(this, DashboardActivity::class.java))
                } else {
                    startActivity(Intent(this, PrivacyPolicyActivity::class.java))
                }

            } else {
                startActivity(Intent(this, LoginActivity::class.java))
            }
            finish()
        }, 2000)

    }
}