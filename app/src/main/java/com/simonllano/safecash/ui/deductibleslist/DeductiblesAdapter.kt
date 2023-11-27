package com.simonllano.safecash.ui.deductibleslist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.simonllano.safecash.R
import com.simonllano.safecash.data.model.Deductibles
import com.simonllano.safecash.databinding.CardViewDeductiblesItemBinding


class DeductiblesAdapter(

    private val deductiblesList : MutableList<Deductibles?>,
    private val onItemClicked : (Deductibles?) -> Unit
    // private onItemLongCliced
) : RecyclerView.Adapter<DeductiblesAdapter.DeductiblesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeductiblesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_view_deductibles_item, parent, false)
        return DeductiblesViewHolder(view)
    }

    override fun getItemCount(): Int = deductiblesList.size

    override fun onBindViewHolder(holder: DeductiblesViewHolder, position: Int) {
        val deductibles = deductiblesList[position]
        holder.bind(deductibles)
        holder.itemView.setOnClickListener{ onItemClicked(deductibles)}
    }

    fun appendItems(newList : MutableList<Deductibles?>){
        deductiblesList.clear()
        deductiblesList.addAll(newList)
        notifyDataSetChanged()
    }
    class DeductiblesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        private val binding = CardViewDeductiblesItemBinding.bind(itemView)

        fun bind(deductibles: Deductibles?){
            with(binding){
                name1TextView.text = deductibles?.name
                value2TextView.text = deductibles?.value
                date2TextView.text = deductibles?.date
            }
        }
    }
}