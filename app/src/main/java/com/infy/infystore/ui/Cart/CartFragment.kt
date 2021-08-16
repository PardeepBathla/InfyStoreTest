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
import com.infy.infystore.database.entity.OrdersEntities
import com.infy.infystore.databinding.FragmentCartBinding
import com.infy.infystore.storage.Preference
import com.infy.infystore.ui.ViewModelFactory
import com.infy.infystore.ui.orders.OrdersViewModel
import com.infy.infystore.utils.GlobalConstants
import com.infy.infystore.utils.Utils


class CartFragment : Fragment() {

    private lateinit var cartViewModel: CartViewModel
    private lateinit var ordersViewModel: OrdersViewModel
    private lateinit var binding: FragmentCartBinding
    private lateinit var rv: RecyclerView
    private lateinit var myAdapter: CartAdapter
    private var cartList: ArrayList<CartEntities>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

//        cartViewModel = ViewModelProvider(this).get(CartViewModel::class.java)
        binding = FragmentCartBinding.inflate(inflater, container, false)

        setupViewModel()
        binding.shimmerTiles.startShimmerAnimation()
        binding.shimmerTiles.visibility = View.VISIBLE
        init()



        clickListners()
        return binding.root
    }


    private fun clickListners() {
        binding.btnGoToPurchase.setOnClickListener {
            val list = myAdapter.getList()
            var namesList: ArrayList<String> = ArrayList()

            for (name in list!!) {
                namesList.add(name.itemName)
            }
            val ordersEntities = OrdersEntities(
                (list.size * 45).toString(), namesList, list[0].itemImage,
                Preference.instance.getPreferenceString(GlobalConstants.EMAIL)!!
            )
            ordersViewModel.insert(ordersEntities)

            cartViewModel.deleteCart()

            findNavController().navigate(R.id.action_cartFragment_to_purchaseFragment)
        }


    }

    private fun init() {
        cartList = cartViewModel.fetchProducts()?.let { it as ArrayList<CartEntities> }

        Handler(Looper.myLooper()!!).postDelayed({
            if (cartList != null && cartList!!.size > 0) {
                dataFound()
            } else {
                dataNotFound()
            }
            setHasOptionsMenu(true)
            binding.shimmerTiles.stopShimmerAnimation()
            binding.shimmerTiles.visibility = View.GONE
        }, 100)
    }

    private fun dataNotFound() {
        binding.tvvNoData.visibility = View.VISIBLE
        binding.btnGoToPurchase.visibility = View.GONE
        binding.rvCart.visibility = View.GONE
    }

    private fun dataFound() {

        setAdapter(cartList)
        binding.tvvNoData.visibility = View.GONE
        binding.btnGoToPurchase.visibility = View.VISIBLE
        binding.rvCart.visibility = View.VISIBLE
    }


    private fun setupViewModel() {
        cartViewModel = ViewModelProviders.of(this,
            RoomAppDb.getAppDatabase(activity as DashboardActivity)?.let {
                ViewModelFactory(ApiHelper(RetrofitBuilder.apiService), it)
            }).get(CartViewModel::class.java)

        ordersViewModel = ViewModelProviders.of(this,
            RoomAppDb.getAppDatabase(activity as DashboardActivity)?.let {
                ViewModelFactory(ApiHelper(RetrofitBuilder.apiService), it)
            }).get(OrdersViewModel::class.java)
    }


    private fun setAdapter(fetchProducts: ArrayList<CartEntities>?) {

        try {
            rv = binding.rvCart
            myAdapter =
                CartAdapter(activity as DashboardActivity, fetchProducts, cartViewModel, this)
            rv.layoutManager = LinearLayoutManager(activity)
            rv.adapter = myAdapter
        } catch (e: Exception) {
        }
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        if (this.cartList != null && cartList!!.size!! > 0) {
            Utils.setCount(activity, menu, cartList?.size.toString())
        }
        super.onCreateOptionsMenu(menu, inflater)

    }

    fun noData() {
        dataNotFound()
    }


}