package com.simonllano.safecash.ui.newincome

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.simonllano.safecash.databinding.FragmentNewIncomeBinding
import java.util.Calendar


class NewIncomeFragment : Fragment() {

    private lateinit var incomebinding: FragmentNewIncomeBinding
    private lateinit var newIncomeViewModel : NewIncomeViewModel


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        incomebinding = FragmentNewIncomeBinding.inflate(inflater, container, false)
        newIncomeViewModel = ViewModelProvider(this)[NewIncomeViewModel::class.java]
        val  view = incomebinding.root

        newIncomeViewModel.errorMSG.observe(viewLifecycleOwner){ msg -> // y con fragmentos no va this sino viewLifeCycleOwner
            showErrorMSG(msg)
        }

        newIncomeViewModel.createIncomeSuccess.observe(viewLifecycleOwner){
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }



        var tipo = "Salario"
        if(incomebinding.savingRadioButton.isClickable){
            var tipo = "Ahorro"
        }
        else {
            var tipo = "Extras"
        }

        val dateTextView = incomebinding.dateIncomeEditText

        dateTextView.setOnClickListener {
            showDatePickerDialog()
        }
        with(incomebinding){
            addincomeButton.setOnClickListener{

                newIncomeViewModel.validateFields(
                    valueIncomeEditText.text.toString(),
                    noteIncomeEditText.text.toString(),
                    dateIncomeEditText.text.toString(),
                    tipo

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
                incomebinding.dateIncomeEditText.text = selectedDate
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


