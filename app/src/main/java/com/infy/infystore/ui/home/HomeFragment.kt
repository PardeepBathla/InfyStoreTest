package com.infy.infystore.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.infy.infystore.DashboardActivity
import com.infy.infystore.DummyProduct
import com.infy.infystore.R
import com.infy.infystore.api.ApiHelper
import com.infy.infystore.api.RetrofitBuilder
import com.infy.infystore.database.RoomAppDb
import com.infy.infystore.database.entity.ProductEntities
import com.infy.infystore.databinding.FragmentHomeBinding
import com.infy.infystore.ui.ViewModelFactory
import com.infy.infystore.ui.home.homeModal.ProductModal
import com.infy.infystore.utils.Status.*

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var binding: FragmentHomeBinding
    private lateinit var rv: RecyclerView
    private lateinit var myAdapter: HomeAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupViewModel()
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        Log.d("hh", "onCreateView: ")
        binding = FragmentHomeBinding.inflate(inflater, container, false)


        setList()
        clickListeners()
//        deleteDb()

        checkInternet()

        return binding.root
    }

    private fun checkInternet() {
        if (!com.infy.infystore.utils.Utils.checkInternetConnectivity(activity)) {
            com.infy.infystore.utils.Utils.showAlertDialog(activity)
            homeViewModel.fetchProducts()?.let {
                if (it.isNotEmpty()) {
                    dataFound()
                    Log.d("checkInternet: ", it[0].toString())
                    retrieveList(it[0].products)
                } else {
                    noDataFound()
                }
            }

        } else {
            homeViewModel.deleteTable()
            setupObservers()
        }
    }

    private fun dataFound() {
        binding.tvvNoData?.visibility = View.GONE
        binding.rvHome.visibility = View.VISIBLE
        binding.shimmerTiles?.stopShimmerAnimation()
        binding.shimmerTiles?.visibility = View.GONE

    }

    private fun noDataFound() {
        binding.tvvNoData?.visibility = View.VISIBLE
        binding.rvHome.visibility = View.GONE
        binding.shimmerTiles?.stopShimmerAnimation()
        binding.shimmerTiles?.visibility = View.GONE

    }

    private fun deleteDb() {
        val dbList: List<ProductEntities>? = homeViewModel.fetchProducts()
        if (dbList != null && dbList.isNotEmpty()) {
            homeViewModel.deleteTable()
        }

    }

    private fun setupViewModel() {
        homeViewModel = ViewModelProviders.of(this,
            RoomAppDb.getAppDatabase(activity as DashboardActivity)
                ?.let { ViewModelFactory(ApiHelper(RetrofitBuilder.apiService), it) })
            .get(HomeViewModel::class.java)
    }




    private fun setupObservers() {
        homeViewModel.getProducts().observe(activity as DashboardActivity, Observer {
            it.let { resource ->
                when (resource.status) {
                    SUCCESS -> {
                        dataFound()
                        val list: ArrayList<ProductModal> = ArrayList()
                        resource.data?.let { products ->
                            addListToDB(list, products)
                            retrieveList(products.categories)
                        }
                    }
                    ERROR -> {

                        noDataFound()

                    }
                    LOADING -> {
                        binding.shimmerTiles.startShimmerAnimation()
                        binding.shimmerTiles.visibility = View.VISIBLE
                        binding.rvHome.visibility = View.GONE
                    }
                }
            }
        })
    }

    private fun addListToDB(list: ArrayList<ProductModal>, products: DummyProduct) {
        for (product in products.categories) {
            product.description = getString(R.string.privacy_policy)
            product.price = "45"
            list.add(product)
        }
        val item = ProductEntities(0, list)
        homeViewModel.insert(item)
    }

    private fun retrieveList(categories: List<ProductModal>) {
        myAdapter.apply {
            addUsers(categories,homeViewModel)
            notifyDataSetChanged()
        }
    }


    private fun clickListeners() {
        binding.swipeToRefresh?.setOnRefreshListener {
            checkInternet()
            binding.swipeToRefresh?.isRefreshing = false

        }
    }

    private fun setList() {
        rv = binding.rvHome
        myAdapter = HomeAdapter(activity as DashboardActivity, ArrayList())
        rv.layoutManager = LinearLayoutManager(activity)
        rv.adapter = myAdapter
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return super.onOptionsItemSelected(item)
    }
}