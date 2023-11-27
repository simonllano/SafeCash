package com.simonllano.safecash.ui.incomelist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.simonllano.safecash.R
import com.simonllano.safecash.data.model.Income
import com.simonllano.safecash.databinding.CardViewIncomeItemBinding

class IncomeAdapter (

    private val incomeList : MutableList<Income?>,
    private val onItemClicked : (Income?) -> Unit
   // private onItemLongCliced

) : RecyclerView.Adapter<IncomeAdapter.IncomeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IncomeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_view_income_item, parent, false)
        return IncomeViewHolder(view)
    }

    override fun getItemCount(): Int = incomeList.size

    override fun onBindViewHolder(holder: IncomeViewHolder, position: Int) {
        val income = incomeList[position]
        holder.bind(income)
        holder.itemView.setOnClickListener{ onItemClicked(income)}
    }

    fun appendItems(newList : MutableList<Income?>){
        incomeList.clear()
        incomeList.addAll(newList)
        notifyDataSetChanged()
    }
    class IncomeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        private val binding = CardViewIncomeItemBinding.bind(itemView)

        fun bind(income: Income?){
            with(binding){
                noteTextView.text = income?.note
                valueTextView.text = income?.valor
                tipoTextView.text= income?.tipo
                dateTextView.text = income?.date
            }
        }
    }

}

