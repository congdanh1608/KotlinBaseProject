package com.danhtran.androidbaseproject.ui.base_recyclerview

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.danhtran.androidbaseproject.extras.LiveEvent

/**
 * Created by danhtran on 10/06/2017.
 */

class BindingViewHolder<T>(val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root) {
    var itemClickAction = LiveEvent<T>()

    fun bind(itemClickAction: LiveEvent<T>) {
        this.itemClickAction = itemClickAction
    }
}
