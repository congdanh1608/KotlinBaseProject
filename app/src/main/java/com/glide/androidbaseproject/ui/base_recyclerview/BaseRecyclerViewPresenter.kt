package com.glide.androidbaseproject.ui.base_recyclerview

import android.os.Handler
import androidx.databinding.BaseObservable
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import java.util.*

/**
 * Created by danhtran on 11/06/2017.
 */

abstract class BaseRecyclerViewPresenter<T> : BaseObservable, BaseRecyclerListener,
    SwipeRefreshLayout.OnRefreshListener {
    protected var swipeRefreshLayout: SwipeRefreshLayout? = null
    protected var items: MutableList<T> = ArrayList()
    protected var adapter: BaseRecyclerAdapter<T>? = null
    protected var isRefresh = false
    protected var isLoading = false
    protected var currentPage = 1
    protected var maxPage = 0                          //use if server return total pages load.    //paging loading
    protected var isLoadMore = false               //use if server return loadMore ? true : false. -> lazy loading

    constructor(swipeRefreshLayout: SwipeRefreshLayout?) {
        this.swipeRefreshLayout = swipeRefreshLayout

        swipeRefreshLayout?.setOnRefreshListener(this)
    }

    constructor() {}

    fun setAdapter(): BaseRecyclerAdapter<T>? {
        return adapter
    }

    protected fun initData() {
        loadData()
    }

    protected fun loadData() {}

    //use it if don't load data in background.
    protected fun addItemsInHandler(items: List<T>) {
        val handler = Handler()
        Thread(Runnable {
            handler.post {
                if (isRefresh) {
                    isRefresh = false
                    this@BaseRecyclerViewPresenter.items.clear()
                    if (swipeRefreshLayout != null)
                        swipeRefreshLayout!!.isRefreshing = false
                } else {
                    adapter!!.setProgressMore(false)
                    adapter!!.isMoreLoading = false
                }

                this@BaseRecyclerViewPresenter.items.addAll(items)
                isLoading = false
                adapter!!.notifyDataSetChanged()
            }
        }).run()
    }

    //if  it if load data in background.
    protected fun addItems(items: List<T>) {
        if (isRefresh) {
            isRefresh = false
            this.items.clear()
            if (swipeRefreshLayout != null)
                swipeRefreshLayout!!.isRefreshing = false
        } else {
            adapter!!.setProgressMore(false)
            adapter!!.isMoreLoading = false
        }

        this.items.addAll(items)
        isLoading = false
        adapter!!.notifyDataSetChanged()
    }

    protected fun deleteItem(position: Int) {
        this.items.removeAt(position)
        adapter!!.notifyItemRemoved(position)
    }

    override fun onLoadMore(position: Int) {
        if ((currentPage < maxPage || isLoadMore) && !isLoading) {
            currentPage++
            isLoading = true
            isLoadMore = false
            adapter!!.setProgressMore(true)
            adapter!!.isMoreLoading = true
            isRefresh = false
            loadData()
        }
    }

    override fun onRefresh() {
        currentPage = 1
        isLoadMore = false

        if (swipeRefreshLayout != null)
            swipeRefreshLayout!!.isRefreshing = true
        isRefresh = true
        loadData()
    }

    override fun <T : Any> onClickItem(item: T) {

    }
}
