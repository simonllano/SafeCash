package com.simonllano.safecash.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.simonllano.safecash.databinding.FragmentMainBinding



class MainFragment : Fragment() {

    private lateinit var mainbinding: FragmentMainBinding
    private lateinit var mainViewModel : MainViewModel



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mainbinding = FragmentMainBinding.inflate(inflater, container, false)
        mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]
        val view = mainbinding.root

        mainViewModel.errorMSG.observe(viewLifecycleOwner){msg -> // y con fragmentos no va this sino viewLifeCycleOwner
            showErrorMSG(msg)
        }


        return view

    }
    private fun showErrorMSG(msg: String?) {
        Toast.makeText(requireActivity(), msg, Toast.LENGTH_LONG).show()

    }

}
