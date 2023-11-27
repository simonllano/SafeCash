package com.simonllano.safecash.ui.costlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.simonllano.safecash.data.model.Costs

import com.simonllano.safecash.databinding.FragmentCostsBinding


class CostsFragment : Fragment() {

    private lateinit var costsBinding: FragmentCostsBinding
    private lateinit var costsViewModel: CostsViewModel
    private lateinit var costsAdapter: CostsAdapter
    private var costsList = mutableListOf<Costs?>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        costsViewModel = ViewModelProvider(this)[CostsViewModel::class.java]
        costsBinding = FragmentCostsBinding.inflate(inflater, container, false)
        val view = costsBinding.root

        costsViewModel.loadCosts()
        costsAdapter= CostsAdapter(costsList, onItemClicked = {onIncomeItemClicked(it)})

        costsViewModel.errorMSG.observe(viewLifecycleOwner){ msg -> // y con fragmentos no va this sino viewLifeCycleOwner
            showErrorMSG(msg)
        }

        costsViewModel.costsList.observe(viewLifecycleOwner){ costsList ->
            costsAdapter.appendItems(costsList)
        }

        costsBinding.costsRecyclerView.apply{
            layoutManager= LinearLayoutManager(this@CostsFragment.requireContext())
            adapter = costsAdapter
            setHasFixedSize(false)
        }


        return view
    }

    private fun onIncomeItemClicked(costs: Costs?) {

    }

    private fun showErrorMSG(msg: String?) {
        Toast.makeText(requireActivity(), msg, Toast.LENGTH_LONG).show()

    }

}