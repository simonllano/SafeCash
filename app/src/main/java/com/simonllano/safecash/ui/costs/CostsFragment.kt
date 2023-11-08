package com.simonllano.safecash.ui.costs

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.simonllano.safecash.databinding.FragmentCostsBinding
import com.simonllano.safecash.databinding.FragmentIncomeBinding
import com.simonllano.safecash.ui.income.IncomeViewModel


class CostsFragment : Fragment() {
    private lateinit var costsbinding: FragmentCostsBinding
    private lateinit var costsViewModel : CostsViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        costsbinding = FragmentCostsBinding.inflate(inflater, container, false)
        costsViewModel = ViewModelProvider(this)[CostsViewModel::class.java]
        val  view = costsbinding.root

        costsViewModel.errorMSG.observe(viewLifecycleOwner){msg -> // y con fragmentos no va this sino viewLifeCycleOwner
            showErrorMSG(msg)
        }

        costsViewModel.createCostsSuccess.observe(viewLifecycleOwner){

        }

        with(costsbinding){
            addcostsButton.setOnClickListener{


                costsViewModel.validateFields(

                    valueCostsEditText.text.toString().toDouble(),
                    tipoCostsEditText.text.toString(),
                    dateCostsEditText.text.toString(),
                    categorySpinner.selectedItem.toString()


                )
            }
        }

        return view

    }
    private fun showErrorMSG(msg: String?) {
        Toast.makeText(requireActivity(), msg, Toast.LENGTH_LONG).show()

    }

}