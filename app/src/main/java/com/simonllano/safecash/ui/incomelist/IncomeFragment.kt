package com.simonllano.safecash.ui.incomelist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.simonllano.safecash.data.model.Income
import com.simonllano.safecash.databinding.FragmentIncomeBinding
import androidx.recyclerview.widget.RecyclerView


class IncomeFragment : Fragment() {

    private lateinit var incomeBinding: FragmentIncomeBinding
    private lateinit var incomeViewModel: IncomeViewModel
    private lateinit var incomeAdapter: IncomeAdapter
    private var incomeList = mutableListOf<Income?>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        incomeViewModel = ViewModelProvider(this)[IncomeViewModel::class.java]
        incomeBinding = FragmentIncomeBinding.inflate(inflater, container, false)
        val view = incomeBinding.root

        incomeViewModel.loadIncome()
        incomeAdapter= IncomeAdapter(incomeList, onItemClicked = {onIncomeItemClicked(it)})

        incomeViewModel.errorMSG.observe(viewLifecycleOwner){ msg -> // y con fragmentos no va this sino viewLifeCycleOwner
            showErrorMSG(msg)
        }

        incomeViewModel.incomeList.observe(viewLifecycleOwner){ incomeList ->
            incomeAdapter.appendItems(incomeList)
        }

        incomeBinding.incomeRecyclerView.apply{
            layoutManager= LinearLayoutManager(this@IncomeFragment.requireContext())
            adapter = incomeAdapter
            setHasFixedSize(false)
        }


        return view
    }

    private fun onIncomeItemClicked(income: Income?) {

    }

    private fun showErrorMSG(msg: String?) {
        Toast.makeText(requireActivity(), msg, Toast.LENGTH_LONG).show()

    }


}