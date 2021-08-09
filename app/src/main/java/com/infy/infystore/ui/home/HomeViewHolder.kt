package com.infy.infystore.ui.home

import android.view.View
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.infy.infystore.databinding.ItemListHomeBinding

public class HomeViewHolder(itemView: View, binding: ItemListHomeBinding) : RecyclerView.ViewHolder(itemView) {
    private var itemListBinding = binding
    fun bindData(arr: Array<String>?, position: Int) {
        itemListBinding.txt.text = arr?.get(position)
    }

}
