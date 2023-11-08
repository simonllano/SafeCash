package com.simonllano.safecash.ui.income

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.simonllano.safecash.databinding.FragmentIncomeBinding



class IncomeFragment : Fragment() {

    private lateinit var incomebinding: FragmentIncomeBinding
    private lateinit var incomeViewModel : IncomeViewModel


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        incomebinding = FragmentIncomeBinding.inflate(inflater, container, false)
        incomeViewModel = ViewModelProvider(this)[IncomeViewModel::class.java]
        val  view = incomebinding.root

        incomeViewModel.errorMSG.observe(viewLifecycleOwner){msg -> // y con fragmentos no va this sino viewLifeCycleOwner
            showErrorMSG(msg)
        }

        incomeViewModel.createIncomeSuccess.observe(viewLifecycleOwner){

        }

        incomeViewModel.incomeList.observe(viewLifecycleOwner){

        }

        var tipo = "Salario"
        if(incomebinding.savingRadioButton.isClickable){
            var tipo = "Ahorro"
        }
        else {
            var tipo = "Extras"
        }
        with(incomebinding){
            addincomeButton.setOnClickListener{

                incomeViewModel.validateFields(
                    valueIncomeEditText.text.toString().toDouble(),
                    noteIncomeEditText.text.toString(),
                    dateIncomeEditText.text.toString(),
                    tipo

                )

                incomeViewModel.loadDatos()
            }
        }



        return view

    }

    private fun showErrorMSG(msg: String?) {
        Toast.makeText(requireActivity(), msg, Toast.LENGTH_LONG).show()

    }

}


