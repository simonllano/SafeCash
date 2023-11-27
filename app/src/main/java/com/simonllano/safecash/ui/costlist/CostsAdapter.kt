package com.simonllano.safecash.ui.costlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.simonllano.safecash.R
import com.simonllano.safecash.data.model.Costs
import com.simonllano.safecash.databinding.CardViewCostsItemBinding
import com.simonllano.safecash.ui.costlist.CostsAdapter

class CostsAdapter (

    private val costsList : MutableList<Costs?>,
    private val onItemClicked : (Costs?) -> Unit
    // private onItemLongCliced

) : RecyclerView.Adapter<CostsAdapter.CostsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CostsViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_view_costs_item, parent, false)
        return CostsViewHolder(view)
    }

    override fun getItemCount(): Int = costsList.size

    override fun onBindViewHolder(holder: CostsViewHolder, position: Int) {
        val costs = costsList[position]
        holder.bind(costs)
        holder.itemView.setOnClickListener { onItemClicked(costs) }
    }

    fun appendItems(newList: MutableList<Costs?>) {
        costsList.clear()
        costsList.addAll(newList)
        notifyDataSetChanged()
    }

    class CostsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val binding = CardViewCostsItemBinding.bind(itemView)

        fun bind(costs: Costs?) {
            with(binding) {
                nameTextView.text = costs?.name
                value1TextView.text = costs?.value
                categorysTextView.text = costs?.category
                date1TextView.text = costs?.date
            }
        }
    }
}
