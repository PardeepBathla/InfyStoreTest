package com.infy.infystore.ui.home

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
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
    private val arr: Array<String> = arrayOf("A", "B", "C", "D", "E", "F", "G", "H", "I", "J")


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        Log.d("hh", "onCreateView: ")

//        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        binding = FragmentHomeBinding.inflate(inflater, container, false)

        setupViewModel()
//        insertStaticDataInDb()
        setList()
        clickListeners()
        setupObservers()

        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        Log.d("hh", "onAttach: ")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        Log.d("hh", "onCreate: ")
    }

    override fun onStart() {
        super.onStart()
        Log.d("hh", "onStart: ")
    }

    override fun onResume() {
        super.onResume()

        Log.d("hh", "onResume: ")
    }

    private fun setupViewModel() {
        homeViewModel = ViewModelProviders.of(this,
            RoomAppDb.getAppDatabase(activity as DashboardActivity)
                ?.let { ViewModelFactory(ApiHelper(RetrofitBuilder.apiService), it) })
            .get(HomeViewModel::class.java)
    }


    private fun setupObservers() {
        val dbList: List<ProductEntities>? = homeViewModel.fetchProducts()
        /*if (dbList != null && dbList.isNotEmpty()) {
            retrieveList(dbList)
        }*/
        homeViewModel.getProducts().observe(activity as DashboardActivity, Observer {
            it.let { resource ->
                when (resource.status) {
                    SUCCESS -> {
                        binding.rvHome.visibility = View.VISIBLE
                        binding.svTiles?.stopShimmerAnimation()
                        binding.svTiles?.visibility = View.GONE

                        if (dbList != null && dbList.isNotEmpty()) {
                            homeViewModel.deleteTable()
                        }
                        val list: ArrayList<ProductModal> = ArrayList()
                        resource.data?.let { products ->
                            addListToDB(list, products)
                            retrieveList(products.categories)
                        }
                    }
                    ERROR -> {
                        binding.svTiles?.stopShimmerAnimation()
                        binding.svTiles?.visibility = View.GONE
                        binding.rvHome.visibility = View.VISIBLE

                    }
                    LOADING -> {
                        binding.svTiles?.startShimmerAnimation()
                        binding.svTiles?.visibility = View.VISIBLE
                        binding.rvHome.visibility = View.GONE
                    }
                }
            }
        })
    }

    private fun addListToDB(list: ArrayList<ProductModal>, products: DummyProduct) {
        for (product in products.categories) {
            product.description = getString(R.string.dummy_desc)
            product.price = "45"
            list.add(product)
        }
        val item = ProductEntities(0, list)
        homeViewModel.insert(item)
    }


    /*private fun insertStaticDataInDb() {
        homeViewModel.deleteTable()
        var modal: ProductModal =
            ProductModal("1", "150", "coke", "14", "Coke coke coke coke")
        var modal1: ProductModal =
            ProductModal("2", "160", "coke1", "14", "coke1 coke1 coke1 coke1")
        var modal2: ProductModal =
            ProductModal("3", "170", "coke2", "14", "coke2 coke2 coke2 coke2")
        var modal3: ProductModal =
            ProductModal("4", "180", "coke3", "14", "coke3 coke3 coke3 coke3")
        var modal4: ProductModal =
            ProductModal("5", "190", "coke4", "14", "coke4 coke4 coke4 coke4")

        val list: ArrayList<ProductModal> = ArrayList()
        list.add(modal)
        list.add(modal1)
        list.add(modal2)
        list.add(modal3)
        list.add(modal4)
        val item: ProductItems = ProductItems(0, "erwwe", list)
        homeViewModel.insert(item)
        //        db.getProductsDao().insert(item)
    }*/

    private fun retrieveList(categories: List<ProductModal>) {
        myAdapter.apply {
            addUsers(categories)
            notifyDataSetChanged()
        }
    }


    private fun clickListeners() {


        binding.swipeToRefresh?.setOnRefreshListener {
            setupObservers()
            binding.swipeToRefresh?.isRefreshing = false

        }
    }

    private fun setList() {
        rv = binding.rvHome
        myAdapter = HomeAdapter(activity as DashboardActivity, ArrayList())
        rv.layoutManager = LinearLayoutManager(activity)
        rv.adapter = myAdapter
    }
}