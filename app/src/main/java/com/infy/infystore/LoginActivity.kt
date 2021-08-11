package com.infy.infystore

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.view.Window
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.infy.infystore.databinding.ActivityLoginBinding
import com.infy.infystore.storage.Preference
import com.infy.infystore.utils.GlobalConstants
import com.infy.infystore.utils.Utils

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    lateinit var mGoogleSignInClient: GoogleSignInClient
    val Req_Code: Int = 123
    private lateinit var firebaseAuth: FirebaseAuth
    private val username: String = "xyz@infosys.com"
    private val password: String = "12345678"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);//will hide the title
        this.supportActionBar!!.hide(); //hide the title bar
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (Preference.instance.getPreferenceBoolean(GlobalConstants.IS_REMEMBER_PASSWORD)) {
            binding.etUsername.setText(Preference.instance.getPreferenceString(GlobalConstants.EMAIL))
            binding.etpass.setText(Preference.instance.getPreferenceString(GlobalConstants.PASSWORD))
        }
        initGoogleSignIn()

        clickListner()

        if (!Utils.checkInternetConnectivity(this)){
            Utils.showAlertDialog(this)
        }


    }

    private fun clickListner() {
        binding.ivGoogle?.setOnClickListener { view: View? ->
            signInGoogle()
        }

        binding.loginBtn.setOnClickListener {
            if (TextUtils.isEmpty(binding.etUsername.text)) {
                Toast.makeText(this, "Please enter Email id", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            if (TextUtils.isEmpty(binding.etpass.text)) {
                Toast.makeText(this, "Please enter Password", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            if (binding.etUsername.text.toString() != username) {
                Toast.makeText(this, "Incorrect Email", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            if (binding.etpass.text.toString() != password) {
                Toast.makeText(this, "Incorrect Password", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }


            val isChecked: Boolean = binding.cbRemember.isChecked
            loginSuccess(username, password, isChecked)
        }
    }

    private fun initGoogleSignIn() {
        FirebaseApp.initializeApp(this)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
        firebaseAuth = FirebaseAuth.getInstance()
    }

    private fun signInGoogle() {
        val signInIntent: Intent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, Req_Code)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == Req_Code) {
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleResult(task)
        }
    }

    private fun handleResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account: GoogleSignInAccount? = completedTask.getResult(ApiException::class.java)
            if (account != null) {
                UpdateUI(account)
            }
        } catch (e: ApiException) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show()
        }
    }

    // this is where we update the UI after Google signin takes place
    private fun UpdateUI(account: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Preference.instance.setPreferenceString(
                    GlobalConstants.EMAIL,
                    account.email.toString()
                )
                Preference.instance.setPreferenceString(
                    GlobalConstants.NAME,
                    account.displayName.toString()
                )
                googleLoginSuccess()
            }
        }
    }

    private fun googleLoginSuccess() {
        Preference.instance.setPreferenceBoolean(GlobalConstants.IS_LOGIN, true)
        startActivity(Intent(this, PrivacyPolicyActivity::class.java))
        finish()
    }

    private fun loginSuccess(username: String, password: String, isChecked: Boolean) {

        Preference.instance.setPreferenceBoolean(GlobalConstants.IS_REMEMBER_PASSWORD, isChecked)

        if (isChecked) {
            Preference.instance.setPreferenceString(GlobalConstants.PASSWORD, password)
        }
        Preference.instance.setPreferenceString(GlobalConstants.EMAIL, username)
        Preference.instance.setPreferenceBoolean(GlobalConstants.IS_LOGIN, true)
        if (Preference.instance.getPreferenceBoolean(GlobalConstants.IS_PRIVACY_ACCEPTED)) {
            startActivity(Intent(this, DashboardActivity::class.java))
        } else {
            startActivity(Intent(this, PrivacyPolicyActivity::class.java))
        }
        finish()
    }

    override fun onStart() {
        super.onStart()
        if (GoogleSignIn.getLastSignedInAccount(this) != null) {
            startActivity(
                Intent(
                    this, DashboardActivity
                    ::class.java
                )
            )
            finish()
        }
    }


}