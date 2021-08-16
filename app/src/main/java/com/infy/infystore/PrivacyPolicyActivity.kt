package com.infy.infystore

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.infy.infystore.databinding.ActivityMainBinding
import com.infy.infystore.storage.Preference
import com.infy.infystore.utils.GlobalConstants


class PrivacyPolicyActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var radioButton: RadioButton
    private var radioGroup: RadioGroup? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);//will hide the title
        this.supportActionBar!!.hide(); //hide the title bar
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        radioGroup = binding.radioGroup
        binding.submit.setOnClickListener {

            val intSelectButton: Int = radioGroup!!.checkedRadioButtonId
            radioButton = findViewById(intSelectButton)
            Log.d("radio",""+intSelectButton)


            if (radioButton.text.equals(getString(R.string.accept))) {
                Preference.instance.setPreferenceBoolean(GlobalConstants.IS_PRIVACY_ACCEPTED,true)
                startActivity(Intent(this,DashboardActivity::class.java))
                finish()
            }else{
                Preference.instance.setPreferenceBoolean(GlobalConstants.IS_PRIVACY_ACCEPTED,false)
                finish()
            }
        }
    }
}