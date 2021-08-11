package com.infy.infystore.ui.account

import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.infy.infystore.R
import com.infy.infystore.databinding.FragmentAccountBinding
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

            if (TextUtils.isEmpty(binding.etName.text)){
                Toast.makeText(activity, "Please enter name", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            if (TextUtils.isEmpty(binding.etEmail.text)){
                Toast.makeText(activity, "Please enter email", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            if (!isValidEmail(binding.etEmail.text.toString())){
                Toast.makeText(activity, "Please enter valid email", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            Preference.instance.setPreferenceString(GlobalConstants.EMAIL,binding.etEmail.text.toString())
            Preference.instance.setPreferenceString(GlobalConstants.NAME,binding.etName.text.toString())
            findNavController().navigate(R.id.action_nav_account_to_nav_home)
        }
        return binding.root
    }

    fun isValidEmail(target: CharSequence?): Boolean {
        return !TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(AccountViewModel::class.java)
        // TODO: Use the ViewModel
    }

}