package com.infy.infystore.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.infy.infystore.R
import com.infy.infystore.databinding.FragmentPurchaseBinding


class PurchaseFragment : Fragment() {

    private lateinit var binding:FragmentPurchaseBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = FragmentPurchaseBinding.inflate(inflater,container,false)

        binding.btnGoToHomeAgain.setOnClickListener {

            findNavController().navigate(R.id.action_purchaseFragment_to_nav_home)

        }
        return binding.root
    }


}