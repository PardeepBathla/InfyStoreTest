package com.infy.infystore

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.infy.infystore.databinding.ActivityLoginBinding
import com.infy.infystore.storage.Preference
import com.infy.infystore.utils.GlobalConstants

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       binding =  ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loginBtn.setOnClickListener {
            Preference.instance.setPreferenceBoolean(GlobalConstants.IS_LOGIN,true)
            startActivity(Intent(this,PrivacyPolicyActivity::class.java))
            finish()
        }


    }


}