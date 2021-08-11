package com.infy.infystore.utils

import android.app.AlertDialog
import android.app.Service
import android.content.Context
import android.content.DialogInterface
import android.graphics.drawable.LayerDrawable
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.infy.infystore.R


class Utils {
    companion object {

        var connectivity: ConnectivityManager? = null
        var info: NetworkInfo? = null

        fun checkInternetConnectivity(context: Context?): Boolean {
            var isInternetAvailable = false
            connectivity = context?.getSystemService(Service.CONNECTIVITY_SERVICE)
                    as ConnectivityManager

            if (connectivity != null) {
                info = connectivity!!.activeNetworkInfo

                if (info != null) {
                    if (info!!.state == NetworkInfo.State.CONNECTED) {
                        isInternetAvailable = true;
                        Log.d("connected", "checkInternetConnectivity: ")
                    }
                } else {
                    isInternetAvailable = false
                    Log.d("no connected", "checkInternetConnectivity: ")
                }
            }

            return isInternetAvailable
        }


        fun showAlertDialog(context: Context?) {
            AlertDialog.Builder(context)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Internet Connection Alert")
                .setMessage("Please Check Your Internet Connection")
                .setPositiveButton("Close",
                    DialogInterface.OnClickListener { dialogInterface, i -> dialogInterface.dismiss() })
                .show()
        }

        fun setCount(context: Context?, menu: Menu?, count: String) {
            val menuItem: MenuItem = menu?.findItem(R.id.action_cart)!!
            val icon = menuItem.icon as LayerDrawable
            val badge: CountDrawable

            // Reuse drawable if possible
            val reuse = icon.findDrawableByLayerId(R.id.ic_group_count)
            badge = if (reuse != null && reuse is CountDrawable) {
                reuse
            } else {
                CountDrawable(context)
            }
            badge.setCount(count)
            icon.mutate()
            icon.setDrawableByLayerId(R.id.ic_group_count, badge)
        }
    }
}