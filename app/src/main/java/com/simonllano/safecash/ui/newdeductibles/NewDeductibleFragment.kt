package com.simonllano.safecash.ui.newdeductibles

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.simonllano.safecash.databinding.FragmentNewDeductibleBinding

import java.util.Calendar


class NewDeductibleFragment : Fragment() {
    private lateinit var deductiblesbinding: FragmentNewDeductibleBinding
    private lateinit var newDeductiblesViewModel : NewDeductiblesViewModel


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        deductiblesbinding = FragmentNewDeductibleBinding.inflate(inflater, container, false)
        newDeductiblesViewModel = ViewModelProvider(this)[NewDeductiblesViewModel::class.java]
         val view = deductiblesbinding.root

        newDeductiblesViewModel.errorMSG.observe(viewLifecycleOwner){ msg -> // y con fragmentos no va this sino viewLifeCycleOwner
            showErrorMSG(msg)
        }

        newDeductiblesViewModel.createDeductiblesSuccess.observe(viewLifecycleOwner){
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

        val dateTextView = deductiblesbinding.dateDeducEditText

        dateTextView.setOnClickListener {
            showDatePickerDialog()
        }

        with(deductiblesbinding){
            addDeducButton.setOnClickListener{

                newDeductiblesViewModel.validateFields(
                    valueDeducEditText.text.toString(),
                    typeDeducEditText.text.toString(),
                    dateDeducEditText.text.toString()
                )
            }
        }

        return view

    }

    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val currentYear = calendar.get(Calendar.YEAR)
        val currentMonth = calendar.get(Calendar.MONTH)
        val currentDay = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            requireContext(),
            DatePickerDialog.OnDateSetListener { view: DatePicker, year: Int, monthOfYear: Int, dayOfMonth: Int ->
                val selectedDate = "$year-${monthOfYear + 1}-$dayOfMonth"
                deductiblesbinding.dateDeducEditText.text = selectedDate
            },
            currentYear,
            currentMonth,
            currentDay
        )

        datePickerDialog.show()
    }

    private fun showErrorMSG(msg: String?) {
        Toast.makeText(requireActivity(), msg, Toast.LENGTH_LONG).show()

    }

}
