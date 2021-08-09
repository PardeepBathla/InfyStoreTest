package com.infy.infystore.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.infy.infystore.DashboardActivity
import com.infy.infystore.databinding.ItemListHomeBinding

class HomeAdapter(private val mainActivity: HomeFragment, private var arr: Array<String>?) :
    RecyclerView.Adapter<HomeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val binding = ItemListHomeBinding.inflate(LayoutInflater.from(mainActivity.context), parent, false)
        return HomeViewHolder(binding.root,binding)
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {

        holder.bindData(arr,position);
    }

    override fun getItemCount(): Int {
        return arr?.size?:0
    }
}