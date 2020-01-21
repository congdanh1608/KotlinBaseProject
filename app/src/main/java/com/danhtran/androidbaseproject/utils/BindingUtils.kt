package com.danhtran.androidbaseproject.utils

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.danhtran.androidbaseproject.ui.customview.DividerItemDecoration
import com.danhtran.androidbaseproject.ui.customview.SpacesItemDecoration
import com.danhtran.customglide.GlideHelper


/**
 * Created by danhtran on 2/20/16.
 */
object BindingUtils {
    //size in dp
    @BindingAdapter("absParams", "absWidth", "absHeight")
    fun absParams(imageView: ImageView, absParams: Boolean, width: Int, height: Int) {
        var width = width
        var height = height
        width = SizeUtils.dpToPx(width)
        height = SizeUtils.dpToPx(height)
        val layoutParams = AbsListView.LayoutParams(width, height)
        imageView.layoutParams = layoutParams
    }

    //size in dp
    @BindingAdapter("linearParams", "linearWidth", "linearHeight")
    fun linearParams(imageView: ImageView, linearParams: Boolean, width: Int, height: Int) {
        var width = width
        var height = height
        width = SizeUtils.dpToPx(width)
        height = SizeUtils.dpToPx(height)
        val layoutParams = LinearLayout.LayoutParams(width, height)
        imageView.layoutParams = layoutParams
    }

    //size in dp
    @BindingAdapter("relativeParams", "relativeWidth", "relativeHeight")
    fun relativeParams(imageView: ImageView, relativeParams: Boolean, width: Int, height: Int) {
        var width = width
        var height = height
        width = SizeUtils.dpToPx(width)
        height = SizeUtils.dpToPx(height)
        val layoutParams = RelativeLayout.LayoutParams(width, height)
        imageView.layoutParams = layoutParams
    }

    //no size
    @BindingAdapter("setImage")
    fun setImage(imageView: ImageView, url: String?) {
        if (url != null) {
            GlideHelper.getInstance().displayImage(url, imageView)
        }
    }

    //size in dp
    @BindingAdapter("setImage", "width", "height")
    fun setImage(imageView: ImageView, url: String?, width: Int, height: Int) {
        var width = width
        var height = height
        //set default value
        width = SizeUtils.dpToPx(width)
        height = SizeUtils.dpToPx(height)
        if (url != null) {
            GlideHelper.getInstance().displayImage(url, imageView, width, height)
        }
    }

    //size in dp
    @BindingAdapter("setImage", "width", "height", "isCircle")
    fun setImage(imageView: ImageView, url: String?, width: Int, height: Int, isCircle: Boolean) {
        var width = width
        var height = height
        //set default value
        width = SizeUtils.dpToPx(width)
        height = SizeUtils.dpToPx(height)
        if (url != null) {
            if (isCircle)
                GlideHelper.getInstance().displayImageCircle(url, imageView, width, height)
            else
                GlideHelper.getInstance().displayImage(url, imageView, width, height)
        }
    }

    //size in dp
    @BindingAdapter("setImage", "width", "height", "isCircle", "borderSize")
    fun setImage(imageView: ImageView, url: String?, width: Int, height: Int, isCircle: Boolean, borderSize: Int) {
        var width = width
        var height = height
        //set default value
        width = SizeUtils.dpToPx(width)
        height = SizeUtils.dpToPx(height)
        if (url != null) {
            if (isCircle)
                GlideHelper.getInstance().displayImageCircle(url, imageView, width, height, borderSize)
            else
                GlideHelper.getInstance().displayImage(url, imageView, width, height)
        }
    }

    @BindingAdapter("setImage", "radius")
    fun radiusImage(imageView: ImageView, url: String?, radius: Int) {
        if (url != null) {
            GlideHelper.getInstance().displayImageRounder(url, imageView, radius)
        }
    }

    //height in DP
    @BindingAdapter("heightOfView")
    fun heightOfView(view: View, height: Int) {
        val layoutParams = view.layoutParams
        layoutParams.height = SizeUtils.dpToPx(height)
        view.layoutParams = layoutParams
    }

    @BindingAdapter("linearManager")
    fun linearManager(recyclerView: RecyclerView, _int: Int) {
        when (_int) {
            0     //0 - horizontal
            -> recyclerView.layoutManager = LinearLayoutManager(
                recyclerView.context,
                LinearLayoutManager.HORIZONTAL, false
            )
            1     //1 - vertical
            -> recyclerView.layoutManager = LinearLayoutManager(
                recyclerView.context,
                LinearLayoutManager.VERTICAL, false
            )
            2     //2 - no scroll vertical
            -> recyclerView.layoutManager = object : LinearLayoutManager(recyclerView.context) {
                override fun canScrollVertically(): Boolean {
                    return false
                }
            }
            3     //3 - no scroll horizontal
            -> recyclerView.layoutManager = object : LinearLayoutManager(recyclerView.context) {
                override fun canScrollHorizontally(): Boolean {
                    return false
                }
            }
            else -> {
            }
        }
    }

    @BindingAdapter("gridManager", "rows")
    fun gridManager(recyclerView: RecyclerView, _int: Int, spanCount: Int) {
        when (_int) {
            0     //0 - grid layout
            -> recyclerView.layoutManager = GridLayoutManager(recyclerView.context, spanCount)
            else -> {
            }
        }
    }

    @BindingAdapter("dividerItemLinear", "sizeSpace")
    fun dividerItemDecorationLinear(recyclerView: RecyclerView, type: Int, size: Int) {
        val context = recyclerView.context
        when (type) {
            0 -> recyclerView.addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.HORIZONTAL, size))
            1 -> recyclerView.addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL, size))
            else -> {
            }
        }
    }

    @BindingAdapter("dividerItemGrid", "sizeSpace")
    fun dividerItemDecorationGrid(recyclerView: RecyclerView, column: Int, size: Int) {
        recyclerView.addItemDecoration(SpacesItemDecoration(size, column))
    }

    @BindingAdapter("hideKeyboadLostFocus")
    fun closeKeyboard(editText: EditText, id: Boolean) {
        val context = editText.context
        editText.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
                UIUtils.hideSoftKeyboard(context, v)
            }
        }
    }
}
