package com.infy.infystore.ui.Cart

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.infy.infystore.R
import com.infy.infystore.database.entity.CartEntities
import com.infy.infystore.databinding.ItemListHomeBinding

class CartAdapter(private val context: Context, private var arr: ArrayList<CartEntities>?) :
    RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val binding = ItemListHomeBinding.inflate(LayoutInflater.from(context), parent, false)
        return CartViewHolder(binding.root,binding)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {

        holder.bindData(arr,position,context);
    }

    override fun getItemCount(): Int {
        return arr?.size?:0
    }


    public class CartViewHolder(itemView: View, binding: ItemListHomeBinding) : RecyclerView.ViewHolder(itemView) {
        private var itemListBinding = binding
        fun bindData(arr: ArrayList<CartEntities>?, position: Int, context: Context) {

            val pm: CartEntities? = arr?.get(position)
            itemListBinding.tvTitle.text = arr?.get(position)?.itemName
            itemListBinding.tvPrice.text = "$"+pm?.itemPrice


            Glide.with(context)
                .load(arr?.get(position)?.itemImage)
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        p0: GlideException?,
                        p1: Any?,
                        p2: Target<Drawable>?,
                        p3: Boolean
                    ): Boolean {
                        itemListBinding.pbImage.visibility = View.GONE
                        return true
                    }

                    override fun onResourceReady(
                        p0: Drawable?,
                        p1: Any?,
                        p2: Target<Drawable>?,
                        p3: DataSource?,
                        p4: Boolean
                    ): Boolean {
                        Log.d("jj", "OnResourceReady")
                        //do something when picture already loaded

                        itemListBinding.pbImage.visibility = View.GONE
                        return false
                    }
                })
//                .placeholder(R.drawable.infy_logo)
                .fallback(R.drawable.infy_logo)
                .into(itemListBinding.ivProduct)
        }

    }
}