package com.infy.infystore.ui.orders

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
import com.infy.infystore.database.entity.OrdersEntities
import com.infy.infystore.databinding.ItemCartBinding
import com.infy.infystore.databinding.ItemOrderBinding
import java.io.File.separator

class OrdersAdapter(
    private val context: Context,
    private var arr: ArrayList<OrdersEntities>?,
    private val ordersViewModel: OrdersViewModel,
    private val ordersFragment: OrdersFragment
) : RecyclerView.Adapter<OrdersAdapter.OrdersViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrdersViewHolder {
        val binding = ItemOrderBinding.inflate(LayoutInflater.from(context), parent, false)
        return OrdersViewHolder(binding.root, binding, ordersViewModel)
    }

    override fun onBindViewHolder(holder: OrdersViewHolder, position: Int) {

        holder.bindData(arr, position, context, this, ordersFragment);

    }

    override fun getItemCount(): Int {
        return arr?.size ?: 0
    }


    public class OrdersViewHolder(
        itemView: View,
        binding: ItemOrderBinding,
        private val ordersViewModel: OrdersViewModel
    ) : RecyclerView.ViewHolder(itemView) {
        private var itemListBinding = binding
        fun bindData(
            arr: ArrayList<OrdersEntities>?,
            position: Int,
            context: Context,
            ordersAdapter: OrdersAdapter,
            ordersFragment: OrdersFragment
        ) {

            val pm: OrdersEntities? = arr?.get(position)
            itemListBinding.tvProductnames.text = arr?.get(position)?.productsName?.get(0)
            itemListBinding.tvProductnames.text = arr?.joinToString (separator = "," )
            itemListBinding.tvPrice.text = "$" + pm?.productsName


            Glide.with(context)
                .load(arr?.get(position)?.productImage)
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