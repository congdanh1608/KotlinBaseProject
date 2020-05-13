package com.danhtran.androidbaseproject.ui.base_recyclerview

import android.os.Handler
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.observe
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.danhtran.androidbaseproject.extras.LiveEvent
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import java.util.*

/**
 * Created by danhtran on 11/06/2017.
 */

abstract class BaseRecyclerViewViewModel<T>(private val lifecycleOwner: LifecycleOwner, private val swipeRefreshLayout: SwipeRefreshLayout?) : ViewModel(),
    SwipeRefreshLayout.OnRefreshListener {
    protected var items: MutableList<T?> = ArrayList()
    lateinit var adapter: BaseRecyclerAdapter<T>

    protected var isRefresh = false
    protected var isLoading = false
    protected var currentPage = 1
    protected var maxPage = 0                           //use if server return total pages load.    //paging loading
    protected var isLoadMore = false                    //use if server return loadMore ? true : false. -> lazy loading

    protected val progressState = LiveEvent<Boolean>()
    protected val errorHandler = LiveEvent<Throwable>()

    protected var disposable: Disposable
    protected var disposable2: Disposable
    protected var disposable3: Disposable

    abstract fun initInject()

    init {
        this.disposable = CompositeDisposable()
        this.disposable2 = CompositeDisposable()
        this.disposable3 = CompositeDisposable()

        initInject()

        swipeRefreshLayout?.setOnRefreshListener(this)
    }

    fun showError(throwable: Throwable) {
        errorHandler.postValue(throwable)
    }

    fun showProgress() {
        progressState.postValue(true)
    }

    fun hideProgress() {
        progressState.postValue(false)
    }

    open fun initData() {
        //load more
        adapter.loadMoreAction.observe(lifecycleOwner) {
            onLoadMore(it)
        }
        adapter.itemClickAction.observe(lifecycleOwner) {
            onClickItem(it)
        }

        loadData()
    }

    open fun loadData() {}

    //use it if don't load data in background.
    protected fun addItemsInHandler(items: List<T>) {
        val handler = Handler()
        Thread(Runnable {
            handler.post {
                if (isRefresh) {
                    isRefresh = false
                    this@BaseRecyclerViewViewModel.items.clear()
                    swipeRefreshLayout?.let {
                        it.isRefreshing = false
                    }
                } else {
                    adapter.setProgressMore(false)
                    adapter.isMoreLoading = false
                }

                this@BaseRecyclerViewViewModel.items.addAll(items)
                isLoading = false
                adapter.notifyDataSetChanged()
            }
        }).run()
    }

    //if it if load data in background.
    protected fun addItems(items: List<T>) {
        if (isRefresh) {
            isRefresh = false
            this.items.clear()
            swipeRefreshLayout?.let {
                it.isRefreshing = false
            }
        } else {
            adapter.setProgressMore(false)
            adapter.isMoreLoading = false
        }

        this.items.addAll(items)
        isLoading = false
        adapter.notifyDataSetChanged()
    }

    override fun onRefresh() {
        currentPage = 1
        isLoadMore = false

        swipeRefreshLayout?.let {
            it.isRefreshing = true
        }
        isRefresh = true
        loadData()
    }

    open fun onRefreshWithoutIndicator() {
        currentPage = 1
        isLoadMore = false

        isRefresh = true
        loadData()
    }

    protected fun deleteItem(position: Int) {
        this.items.removeAt(position)
        adapter.notifyItemRemoved(position)
    }

    private fun onLoadMore(position: Int) {
        if ((currentPage < maxPage || isLoadMore) && !isLoading) {
            currentPage++
            isLoading = true
            isLoadMore = false
            adapter.setProgressMore(true)
            adapter.isMoreLoading = true
            isRefresh = false
            loadData()
        }
    }

    private fun <T> onClickItem(item: T) {

    }
}
