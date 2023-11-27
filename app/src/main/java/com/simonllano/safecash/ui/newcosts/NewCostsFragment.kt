package com.simonllano.safecash.ui.newcosts

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import java.util.Calendar
import android.widget.DatePicker
import android.app.DatePickerDialog
import com.simonllano.safecash.databinding.FragmentNewCostsBinding


class NewCostsFragment : Fragment() {
    private lateinit var costsbinding: FragmentNewCostsBinding
    private lateinit var newCostsViewModel : NewCostsViewModel
    private var dateCosts: String = ""
    private var cal = Calendar.getInstance()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        costsbinding = FragmentNewCostsBinding.inflate(inflater, container, false)
        newCostsViewModel = ViewModelProvider(this)[NewCostsViewModel::class.java]
        val  view = costsbinding.root

        newCostsViewModel.errorMSG.observe(viewLifecycleOwner){ msg -> // y con fragmentos no va this sino viewLifeCycleOwner
            showErrorMSG(msg)
        }

        newCostsViewModel.createCostsSuccess.observe(viewLifecycleOwner){
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
        val dateTextView = costsbinding.dateCostsEditText

        dateTextView.setOnClickListener {
            showDatePickerDialog()
        }


        with(costsbinding){
            addcostsButton.setOnClickListener{
                newCostsViewModel.validateFields(
                    valueCostsEditText.text.toString(),
                    tipoCostsEditText.text.toString(),
                    dateCostsEditText.text.toString(),
                    categorySpinner.selectedItem.toString()
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
                costsbinding.dateCostsEditText.text = selectedDate
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
