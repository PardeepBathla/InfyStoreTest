package com.infy.infystore.ui.orders

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.infy.infystore.DashboardActivity
import com.infy.infystore.api.ApiHelper
import com.infy.infystore.api.RetrofitBuilder
import com.infy.infystore.database.RoomAppDb
import com.infy.infystore.database.entity.CartEntities
import com.infy.infystore.database.entity.OrdersEntities
import com.infy.infystore.databinding.FragmentOrdersBinding
import com.infy.infystore.ui.Cart.CartAdapter
import com.infy.infystore.ui.Cart.CartViewModel
import com.infy.infystore.ui.ViewModelFactory

class OrdersFragment : Fragment() {


    private lateinit var binding: FragmentOrdersBinding
    private lateinit var ordersViewModel: OrdersViewModel
    private var ordersList: ArrayList<OrdersEntities>? = null
    private lateinit var orderViewModel: OrdersViewModel

    private lateinit var rv: RecyclerView
    private lateinit var myAdapter: OrdersAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentOrdersBinding.inflate(inflater, container, false)

        setupViewModel()
        binding.shimmerTiles?.startShimmerAnimation()
        binding.shimmerTiles?.visibility = View.VISIBLE
        init()

        return binding.root
    }


    private fun setupViewModel() {
        orderViewModel = ViewModelProviders.of(this,
            RoomAppDb.getAppDatabase(activity as DashboardActivity)?.let {
                ViewModelFactory(ApiHelper(RetrofitBuilder.apiService), it)
            }).get(OrdersViewModel::class.java)
    }


    private fun init() {
        ordersList = orderViewModel.fetchProducts()?.let { it as ArrayList<OrdersEntities> }

        Handler(Looper.myLooper()!!).postDelayed({
            if (ordersList != null && ordersList!!.size > 0) {
                dataFound()
            } else {
                dataNotFound()
            }
            setHasOptionsMenu(true)
            binding.shimmerTiles?.stopShimmerAnimation()
            binding.shimmerTiles?.visibility = View.GONE
        }, 100)
    }


    private fun dataNotFound() {
        binding.tvvNoData?.visibility = View.VISIBLE
        binding.rvOrders?.visibility = View.GONE
    }


    private fun setAdapter(fetchProducts: ArrayList<OrdersEntities>?) {

        try {
            rv = binding.rvOrders!!
            myAdapter = OrdersAdapter(activity as DashboardActivity, fetchProducts, ordersViewModel, this)
            rv.layoutManager = LinearLayoutManager(activity)
            rv.adapter = myAdapter
        } catch (e: Exception) {
        }
    }


    private fun dataFound() {

        setAdapter(ordersList)
        binding.tvvNoData?.visibility = View.GONE
        binding.rvOrders?.visibility = View.VISIBLE
    }
}