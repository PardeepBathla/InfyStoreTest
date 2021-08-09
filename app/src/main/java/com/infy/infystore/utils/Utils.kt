package com.infy.infystore.utils

import android.content.Context
import android.graphics.drawable.LayerDrawable
import android.view.Menu
import android.view.MenuItem
import com.infy.infystore.R

class Utils {
    companion object{
        val BASE_URL:String = "https://recipesapi.herokuapp.com/api/v2/"

        fun setCount(context: Context?, menu: Menu?, count: String?) {
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