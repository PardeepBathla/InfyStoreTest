package com.infy.infystore.ui.home.productDetail

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.infy.infystore.DashboardActivity
import com.infy.infystore.R
import com.infy.infystore.api.ApiHelper
import com.infy.infystore.api.RetrofitBuilder
import com.infy.infystore.database.RoomAppDb
import com.infy.infystore.database.entity.CartEntities
import com.infy.infystore.databinding.FragmentProductDetailBinding
import com.infy.infystore.storage.Preference
import com.infy.infystore.ui.Cart.CartViewModel
import com.infy.infystore.ui.ViewModelFactory
import com.infy.infystore.utils.GlobalConstants


class ProductDetailFragment : Fragment() {
    private lateinit var cartViewModel: CartViewModel
    private lateinit var binding: FragmentProductDetailBinding
    private lateinit var  itemCart: CartEntities
    private var name: String? = null
    private var price: String? = null
    private var image: String? = null
    private var descp: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = FragmentProductDetailBinding.inflate(inflater, container, false)
        setupViewModel()
        arguments?.apply {
            name = getString(GlobalConstants.TITLE)
            price = getString(GlobalConstants.PRICE)
            image = getString(GlobalConstants.IMAGE)
            descp = getString(GlobalConstants.DESCP)
        }

        setData()

        itemCart = CartEntities(name!!, price!!, descp!!, image!!,
            Preference.instance.getPreferenceString(GlobalConstants.EMAIL)!!
        )

        binding.btnAddToCart.setOnClickListener {
            if (binding.btnAddToCart.text.equals(getString(R.string.add_to_cart))) {


                cartViewModel.insert(itemCart)
                binding.btnAddToCart.text = getString(R.string.go_to_cart)
                setHasOptionsMenu(true)//to refresh  toolbar
            } else {
                findNavController().navigate(R.id.action_productDetailFragment_to_nav_cart)
            }
        }
        return binding.root
    }


    private fun setupViewModel() {
        cartViewModel = ViewModelProviders.of(this,
            RoomAppDb.getAppDatabase(activity as DashboardActivity)?.let {
                ViewModelFactory(ApiHelper(RetrofitBuilder.apiService), it)
            }).get(CartViewModel::class.java)
    }

    private fun setData() {
        binding.tvTitle.text = name
        binding.tvPrice.text = "$" + price
        binding.tvDescp.text = descp


        val cartList = cartViewModel.fetchProducts()?.let { it as ArrayList<CartEntities> }

        Handler(Looper.myLooper()!!).postDelayed({
            if (cartList != null && cartList.size > 0) {
                if (cartList.contains(itemCart)) {
                    binding.btnAddToCart.text = getString(R.string.go_to_cart)
                }
            }
        }, 100)

        Glide.with(activity?.applicationContext!!)
            .load(image)
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    p0: GlideException?,
                    p1: Any?,
                    p2: Target<Drawable>?,
                    p3: Boolean
                ): Boolean {

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
                    return false
                }
            })
            .placeholder(R.drawable.infy_logo)
            .fallback(R.drawable.infy_logo)
            .into(binding.ivProduct)
    }


}