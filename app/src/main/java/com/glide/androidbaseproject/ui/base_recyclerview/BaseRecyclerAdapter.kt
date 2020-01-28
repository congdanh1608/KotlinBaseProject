package com.glide.androidbaseproject.ui.base_recyclerview

import android.content.Context
import android.os.Handler
import android.view.LayoutInflater
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by danhtran on 10/06/2017.
 */

abstract class BaseRecyclerAdapter<T> : RecyclerView.Adapter<BindingViewHolder<*>> {
    private var items: MutableList<T?>
    protected var listener: BaseRecyclerListener?

    //if we use Application Context in getSystemService(), We will get error if we have autolink in textview.
    constructor(items: MutableList<T?>, context: Context, listener: BaseRecyclerListener?) : super() {
        this.items = items
        this.listener = listener

        this.mLayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    }

    val VISIBLE_THRESHOLD = 1
    val VIEW_PROG = 0
    val VIEW_ITEM = 1
    var isMoreLoading = false
    val mLayoutInflater: LayoutInflater

    override fun onBindViewHolder(holder: BindingViewHolder<*>, position: Int) {
        if (getItemViewType(position) != VIEW_PROG) {
            if (position == itemCount - VISIBLE_THRESHOLD) {
                if (listener != null && !isMoreLoading) {
                    listener!!.onLoadMore(position)
                }
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