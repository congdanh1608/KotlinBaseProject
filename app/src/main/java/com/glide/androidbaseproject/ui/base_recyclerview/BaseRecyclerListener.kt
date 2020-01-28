package com.glide.androidbaseproject.ui.base_recyclerview

/**
 * Created by danhtran on 11/06/2017.
 */

interface BaseRecyclerListener {
    fun onLoadMore(position: Int)

    fun <T : Any> onClickItem(item: T)
}
