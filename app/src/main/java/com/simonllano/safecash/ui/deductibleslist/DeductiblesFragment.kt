package com.simonllano.safecash.ui.deductibleslist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.simonllano.safecash.data.model.Deductibles
import com.simonllano.safecash.databinding.FragmentDeductiblesBinding

class DeductiblesFragment : Fragment() {

    private lateinit var deductiblesBinding: FragmentDeductiblesBinding
    private lateinit var deductiblesViewModel: DeductiblesViewModel
    private lateinit var deductiblesAdapter: DeductiblesAdapter
    private var deductiblesList = mutableListOf<Deductibles?>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        deductiblesViewModel = ViewModelProvider(this)[DeductiblesViewModel::class.java]
        deductiblesBinding = FragmentDeductiblesBinding.inflate(inflater, container, false)
        val view = deductiblesBinding.root

        deductiblesViewModel.loadDeductibles()
        deductiblesAdapter= DeductiblesAdapter(deductiblesList, onItemClicked = {onIncomeItemClicked(it)})

        deductiblesViewModel.errorMSG.observe(viewLifecycleOwner){ msg -> // y con fragmentos no va this sino viewLifeCycleOwner
            showErrorMSG(msg)
        }

        deductiblesViewModel.deductiblesList.observe(viewLifecycleOwner){ deductiblesList ->
            deductiblesAdapter.appendItems(deductiblesList)
        }

        deductiblesBinding.deductiblesRecyclerView.apply{
            layoutManager= LinearLayoutManager(this@DeductiblesFragment.requireContext())
            adapter = deductiblesAdapter
            setHasFixedSize(false)
        }


        return view
    }

    private fun onIncomeItemClicked(deductibles: Deductibles?) {

    }

    private fun showErrorMSG(msg: String?) {
        Toast.makeText(requireActivity(), msg, Toast.LENGTH_LONG).show()

    }

}