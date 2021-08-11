package com.infy.infystore.ui.account

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.infy.infystore.R
import com.infy.infystore.databinding.FragmentAccountBinding
import com.infy.infystore.databinding.FragmentCartBinding
import com.infy.infystore.storage.Preference
import com.infy.infystore.utils.GlobalConstants

class AccountFragment : Fragment() {
    private lateinit var binding: FragmentAccountBinding


    private lateinit var viewModel: AccountViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAccountBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)

        binding.etName?.setText(Preference.instance.getPreferenceString(GlobalConstants.NAME))
        binding.etEmail?.setText(Preference.instance.getPreferenceString(GlobalConstants.EMAIL))

        binding.btnUpdate?.setOnClickListener{


        }
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(AccountViewModel::class.java)
        // TODO: Use the ViewModel
    }

}