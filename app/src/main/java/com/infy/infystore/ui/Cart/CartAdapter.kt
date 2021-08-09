package com.infy.infystore.ui.Cart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.infy.infystore.DashboardActivity
import com.infy.infystore.databinding.ItemListHomeBinding

class CartAdapter(private val mainActivity: CartFragment, private var arr: Array<String>?) :
    RecyclerView.Adapter<CartViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val binding = ItemListHomeBinding.inflate(LayoutInflater.from(mainActivity.context), parent, false)
        return CartViewHolder(binding.root,binding)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {

        holder.bindData(arr,position);
    }

    override fun getItemCount(): Int {
        return arr?.size?:0
    }
}