package com.infy.infystore.ui.Cart

import android.content.Context
import android.graphics.drawable.LayerDrawable
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.infy.infystore.R
import com.infy.infystore.databinding.FragmentCartBinding
import com.infy.infystore.utils.CountDrawable
import com.infy.infystore.utils.Utils


class CartFragment : Fragment() {

    private lateinit var cartViewModel: CartViewModel
    private lateinit var binding:FragmentCartBinding
    private lateinit var rv: RecyclerView
    private lateinit var myAdapter: CartAdapter
    private val arr:Array<String>? = arrayOf("A", "B", "C", "D", "E", "F", "G", "H", "I", "J")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        cartViewModel = ViewModelProvider(this).get(CartViewModel::class.java)
        binding = FragmentCartBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true);
        rv = binding.rvCart
        myAdapter = CartAdapter(this, arr)
        rv.layoutManager = LinearLayoutManager(activity)
        rv.adapter = myAdapter


        cartViewModel.text.observe(viewLifecycleOwner, Observer {

        })

        binding.btnGoToPurchase.setOnClickListener {
            findNavController().navigate(R.id.action_cartFragment_to_purchaseFragment)
        }
        return binding.root
    }



    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        Utils.setCount(activity,menu, "0")
        super.onCreateOptionsMenu(menu, inflater)

    }

//    override fun onPrepareOptionsMenu(menu: Menu) {
//        setCount(activity,menu, "9")
//        super.onPrepareOptionsMenu(menu)
//
//    }


}