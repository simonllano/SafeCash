package com.simonllano.safecash.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.simonllano.safecash.R
import com.simonllano.safecash.databinding.FragmentMainBinding
import com.simonllano.safecash.ui.newincome.NewIncomeFragment
import com.simonllano.safecash.ui.newincome.NewIncomeViewModel
import com.simonllano.safecash.ui.register.RegisterActivity


class MainFragment : Fragment() {

    private lateinit var mainbinding: FragmentMainBinding
    private lateinit var mainViewModel : MainViewModel
    private lateinit var newIncomeViewModel : NewIncomeViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mainbinding = FragmentMainBinding.inflate(inflater, container, false)
        mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]
        newIncomeViewModel = ViewModelProvider(this)[NewIncomeViewModel::class.java]
        val view = mainbinding.root

        mainViewModel.errorMSG.observe(viewLifecycleOwner){msg -> // y con fragmentos no va this sino viewLifeCycleOwner
            showErrorMSG(msg)
        }

        mainbinding.newIncomeButton.setOnClickListener {//Aca se envia la orden para abrir la actividad
            findNavController().navigate(R.id.action_mainFragment_to_newIncomeFragment)
        }

        mainbinding.newCostButton.setOnClickListener{
            findNavController().navigate(R.id.action_mainFragment_to_newCostsFragment)
        }

        mainbinding.newDeductibleButton.setOnClickListener{
            findNavController().navigate(R.id.action_mainFragment_to_newDeductiblesFragment)
        }

        return view

    }
    private fun showErrorMSG(msg: String?) {
        Toast.makeText(requireActivity(), msg, Toast.LENGTH_LONG).show()

    }

}
