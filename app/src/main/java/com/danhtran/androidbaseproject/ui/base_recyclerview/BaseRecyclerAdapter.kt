package com.danhtran.androidbaseproject.ui.base_recyclerview

import android.content.Context
import android.os.Handler
import android.view.LayoutInflater
import androidx.recyclerview.widget.RecyclerView
import com.danhtran.androidbaseproject.extras.LiveEvent

/**
 * Created by danhtran on 10/06/2017.
 */

abstract class BaseRecyclerAdapter<T>(private var items: MutableList<T?>, protected var context: Context) : RecyclerView.Adapter<BindingViewHolder<T>>() {
    protected val VISIBLE_THRESHOLD = 1
    protected val VIEW_PROG = 0
    protected val VIEW_ITEM = 1
    var isMoreLoading = false

    //if we use Application Context in getSystemService(), We will get error if we have auto link in text view.
    protected val mLayoutInflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    var loadMoreAction = LiveEvent<Int>()
    var itemClickAction = LiveEvent<T>()

    override fun onBindViewHolder(holder: BindingViewHolder<T>, position: Int) {
        if (getItemViewType(position) != VIEW_PROG) {
            if (position == itemCount - VISIBLE_THRESHOLD && !isMoreLoading) {
                loadMoreAction.postValue(position)
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun remove(position: Int) {
        items.removeAt(position)
        notifyItemRemoved(position)
    }

    fun clear() {
        items.clear()
        notifyDataSetChanged()
    }

    fun setProgressMore(isProgress: Boolean) {
        if (items.size > 0) {
            if (isProgress) {
                Handler().post {
                    items.add(null)
                    notifyItemInserted(items.size - 1)
                }
            } else {
                items.removeAt(items.size - 1)
                notifyItemRemoved(items.size)
                notifyItemRangeChanged(items.size, itemCount)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (items[position] == null) {
            VIEW_PROG
        } else
            VIEW_ITEM
    }
}