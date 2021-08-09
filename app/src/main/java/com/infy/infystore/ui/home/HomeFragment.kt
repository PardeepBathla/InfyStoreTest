package com.infy.infystore.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.infy.infystore.DashboardActivity
import com.infy.infystore.R
import com.infy.infystore.database.RoomAppDb
import com.infy.infystore.database.entity.ProductItems
import com.infy.infystore.databinding.FragmentHomeBinding
import com.infy.infystore.ui.ProductsViewModelFactory
import com.infy.infystore.ui.home.homeModal.ProductModal

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var binding:FragmentHomeBinding
    private lateinit var rv: RecyclerView
    private lateinit var myAdapter: HomeAdapter
    private val arr: Array<String> = arrayOf("A","B","C","D","E","F","G","H","I","J")

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {


        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

//        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)


        binding = FragmentHomeBinding.inflate(inflater,container,false)



        var modal:ProductModal = ProductModal("1","150","coke","14","Coke coke coke coke")
        var modal1:ProductModal = ProductModal("2","160","coke1","14","coke1 coke1 coke1 coke1")
        var modal2:ProductModal = ProductModal("3","170","coke2","14","coke2 coke2 coke2 coke2")
        var modal3:ProductModal = ProductModal("4","180","coke3","14","coke3 coke3 coke3 coke3")
        var modal4:ProductModal = ProductModal("5","190","coke4","14","coke4 coke4 coke4 coke4")

        val list:ArrayList<ProductModal> = ArrayList()
        list.add(modal)
        list.add(modal1)
        list.add(modal2)
        list.add(modal3)
        list.add(modal4)
        val item:ProductItems = ProductItems(0,"erwwe",list)
        homeViewModel.insert(item)
//        db.getProductsDao().insert(item)

        setList()

        clickListners()

        homeViewModel.text.observe(viewLifecycleOwner, Observer {

        })
        return binding.root
    }

    private fun clickListners() {
        binding.btnGoToProductDetail?.setOnClickListener {
            findNavController().navigate(R.id.action_nav_home_to_productDetailFragment)
        }
    }

    private fun setList() {
        rv = binding.rvHome
        myAdapter = HomeAdapter(this, arr)
        rv.layoutManager = LinearLayoutManager(activity);
        rv.adapter = myAdapter
    }
}