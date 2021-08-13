package com.infy.infystore.ui.home


import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.infy.infystore.R

import com.bumptech.glide.request.target.Target
import com.infy.infystore.databinding.FragmentCartBinding
import com.infy.infystore.databinding.ItemListHomeBinding
import com.infy.infystore.ui.Cart.CartFragment
import com.infy.infystore.ui.home.homeModal.ProductModal
import com.infy.infystore.utils.GlobalConstants


class HomeAdapter(
    private val context: Context,
    private var arr: ArrayList<ProductModal>?,
) : RecyclerView.Adapter<HomeAdapter.HomeViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val binding = ItemListHomeBinding.inflate(LayoutInflater.from(context), parent, false)
        return HomeViewHolder(binding.root, binding)
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {

        holder.bindData(arr, position, context);
    }

    override fun getItemCount(): Int {
        return arr?.size ?: 0
    }

    fun addUsers(users: List<ProductModal>, homeViewModel1: HomeViewModel) {
        this.arr?.apply {
            clear()
            addAll(users)
        }
    }

    class HomeViewHolder(itemView: View, binding: ItemListHomeBinding) :
        RecyclerView.ViewHolder(itemView) {
        private var itemListBinding = binding
        fun bindData(arr: List<ProductModal>?, position: Int, context: Context) {
            val pm: ProductModal? = arr?.get(position)
            itemListBinding.tvTitle.text = pm?.title
            itemListBinding.tvPrice.text = "$"+pm?.price


            itemListBinding.root.setOnClickListener{
                Log.d("click", "bindData: ")
                val bundle = Bundle()
                bundle.apply {
                    this.putString(GlobalConstants.TITLE, pm?.title)
                    this.putString(GlobalConstants.DESCP, pm?.description)
                    this.putString(GlobalConstants.PRICE, pm?.price)
                    this.putString(GlobalConstants.IMAGE, pm?.imageUrl)
                    this.putString(GlobalConstants.IMAGE, pm?.imageUrl)
                }


             it.findNavController().navigate(R.id.action_nav_home_to_productDetailFragment,bundle)


            }
            Glide.with(context)
                .load(arr?.get(position)?.imageUrl)
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
                .placeholder(R.drawable.infy_logo)
                .fallback(R.drawable.infy_logo)
                .into(itemListBinding.ivProduct)
        }

    }

}



