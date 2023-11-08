package com.simonllano.safecash.ui.deductibles

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.simonllano.safecash.databinding.FragmentDeductibleBinding


class DeductibleFragment : Fragment() {
    private lateinit var deductiblesbinding: FragmentDeductibleBinding
    private lateinit var deductiblesViewModel : DeductiblesViewModel


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        deductiblesbinding = FragmentDeductibleBinding.inflate(inflater, container, false)
        deductiblesViewModel = ViewModelProvider(this)[DeductiblesViewModel::class.java]
         val view = deductiblesbinding.root

        deductiblesViewModel.errorMSG.observe(viewLifecycleOwner){msg -> // y con fragmentos no va this sino viewLifeCycleOwner
            showErrorMSG(msg)
        }

        deductiblesViewModel.createDeductiblesSuccess.observe(viewLifecycleOwner){

        }

        with(deductiblesbinding){
            addDeducButton.setOnClickListener{

                deductiblesViewModel.validateFields(
                    valueDeducEditText.text.toString().toDouble(),
                    typeDeducEditText.text.toString(),
                    dateDeducEditText.text.toString(),


                )
            }
        }

        return view

    }
    private fun showErrorMSG(msg: String?) {
        Toast.makeText(requireActivity(), msg, Toast.LENGTH_LONG).show()

    }

}
