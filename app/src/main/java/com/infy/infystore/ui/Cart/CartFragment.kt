package com.infy.infystore.ui.Cart

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.infy.infystore.DashboardActivity
import com.infy.infystore.R
import com.infy.infystore.api.ApiHelper
import com.infy.infystore.api.RetrofitBuilder
import com.infy.infystore.database.RoomAppDb
import com.infy.infystore.database.entity.CartEntities
import com.infy.infystore.databinding.FragmentCartBinding
import com.infy.infystore.ui.ViewModelFactory
import com.infy.infystore.utils.Utils
import kotlinx.android.synthetic.main.fragment_cart.*


class CartFragment : Fragment() {

    private lateinit var cartViewModel: CartViewModel
    private lateinit var binding: FragmentCartBinding
    private lateinit var rv: RecyclerView
    private lateinit var myAdapter: CartAdapter
    private var cartList: ArrayList<CartEntities>? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

//        cartViewModel = ViewModelProvider(this).get(CartViewModel::class.java)
        binding = FragmentCartBinding.inflate(inflater, container, false)

        setupViewModel()

        binding.shimmerTiles?.startShimmerAnimation()
        binding.shimmerTiles?.visibility = View.VISIBLE

        cartList = cartViewModel.fetchProducts()?.let { it as ArrayList<CartEntities> }

        Handler(Looper.myLooper()!!).postDelayed({
            if (cartList!=null) {
                dataFound()
            }else{
                dataNotFound()
            }
            setHasOptionsMenu(true)
            binding.shimmerTiles?.stopShimmerAnimation()
            binding.shimmerTiles?.visibility = View.GONE
        }, 1500)





        binding.btnGoToPurchase.setOnClickListener {
            cartViewModel.deleteCart()
            findNavController().navigate(R.id.action_cartFragment_to_purchaseFragment)
        }
        return binding.root
    }

    private fun dataNotFound() {
        binding.tvvNoData?.visibility = View.VISIBLE
        binding.btnGoToPurchase.visibility = View.GONE
        binding.rvCart.visibility = View.GONE
    }

    private fun dataFound() {
        setAdapter(cartList)
        binding.tvvNoData?.visibility = View.GONE
        binding.btnGoToPurchase.visibility = View.VISIBLE
        binding.rvCart.visibility = View.VISIBLE
    }


    private fun setupViewModel() {
        cartViewModel = ViewModelProviders.of(this,
            RoomAppDb.getAppDatabase(activity as DashboardActivity)?.let {
                ViewModelFactory(ApiHelper(RetrofitBuilder.apiService), it)
            }).get(CartViewModel::class.java)
    }


    private fun setAdapter(fetchProducts: ArrayList<CartEntities>?) {
        rv = binding.rvCart
        myAdapter = CartAdapter(activity as DashboardActivity, fetchProducts)
        rv.layoutManager = LinearLayoutManager(activity)
        rv.adapter = myAdapter
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        if (this.cartList !=null && cartList!!.size!! >0) {
            Utils.setCount(activity, menu, cartList?.size.toString())
        }
        super.onCreateOptionsMenu(menu, inflater)

    }


}