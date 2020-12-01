package org.tmdb.jetpack.base.ui.adapter

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.widget.RecyclerView

class UniversalViewHolder(val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: AbstractAdapterItem, isActivated: Boolean) {
        binding.setVariable(item.variableId(), item)
        binding.executePendingBindings()
        itemView.isActivated = isActivated
    }

    fun getItemDetails(): ItemDetailsLookup.ItemDetails<Long> =
        object : ItemDetailsLookup.ItemDetails<Long>() {
            override fun getPosition(): Int = absoluteAdapterPosition

            override fun getSelectionKey(): Long? = absoluteAdapterPosition.toLong()

        }
}