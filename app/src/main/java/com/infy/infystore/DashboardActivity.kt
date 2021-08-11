package com.infy.infystore

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProviders
import com.infy.infystore.api.ApiHelper
import com.infy.infystore.api.RetrofitBuilder
import com.infy.infystore.database.RoomAppDb
import com.infy.infystore.database.entity.CartEntities
import com.infy.infystore.databinding.ActivityDashboardBinding
import com.infy.infystore.storage.Preference
import com.infy.infystore.ui.Cart.CartViewModel
import com.infy.infystore.ui.ViewModelFactory
import com.infy.infystore.utils.GlobalConstants
import com.infy.infystore.utils.Utils
import kotlinx.android.synthetic.main.nav_header.view.*

class DashboardActivity : BaseActivity() {

    private lateinit var cartViewModel: CartViewModel
    private lateinit var binding: ActivityDashboardBinding
    private lateinit var appBarConfiguration: AppBarConfiguration

    companion object {
        private lateinit var cartCount: String
    }

    private var cartList: ArrayList<CartEntities>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViewModel()
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.includedLay.toolbar)

        init()


    }

    override fun onSessionLogout() {
        Log.d("session", "onSessionLogout: ")
        super.onSessionLogout()
        logoutUser()

    }

    private fun init() {
        val drawerLayout: DrawerLayout = binding.drawerLayout
        var navView: NavigationView = binding.navView
        navView.getHeaderView(0).tvName.text =
            Preference.instance.getPreferenceString(GlobalConstants.EMAIL)
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_cart, R.id.nav_orders, R.id.nav_account
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.dashboard, menu)
        cartList = cartViewModel.fetchProducts()?.let { it as ArrayList<CartEntities> }
        if (cartList != null) {
            cartCount = cartList!!.size.toString()
            Utils.setCount(this, menu, cartCount)
        }


        return true
    }

    private fun setupViewModel() {
        cartViewModel = ViewModelProviders.of(this,
            RoomAppDb.getAppDatabase(this@DashboardActivity)?.let {
                ViewModelFactory(ApiHelper(RetrofitBuilder.apiService), it)
            }).get(CartViewModel::class.java)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_logout) {
            logoutUser()
        }

        return super.onOptionsItemSelected(item)
    }

    private fun logoutUser() {
        val isPolicyAccepted: Boolean =
            Preference.instance.getPreferenceBoolean(GlobalConstants.IS_PRIVACY_ACCEPTED)
        if (!Preference.instance.getPreferenceBoolean(GlobalConstants.IS_REMEMBER_PASSWORD)) {
            Preference.instance.clearPreferences()
        }
        Preference.instance.setPreferenceBoolean(
            GlobalConstants.IS_PRIVACY_ACCEPTED, isPolicyAccepted
        )

        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}