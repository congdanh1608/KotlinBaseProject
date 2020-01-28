package com.glide.androidbaseproject.ui.customview

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by danhtran on 17/06/2017.
 */

class SpacesItemDecoration(private val space: Int, private val column: Int) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect, view: View,
        parent: RecyclerView, state: RecyclerView.State
    ) {
        val pos = parent.getChildLayoutPosition(view)

        // add top margin
        if (pos < column) {
            outRect.top = 0
        } else {
            outRect.top = space
        }

        //bottom
        val rows = pos / column
        val endRows = pos - rows * column % column
        if (endRows < column) {
            outRect.bottom = 0
        } else {
            outRect.bottom = space
        }

        //left
        if (pos % column == 0) {
            outRect.left = 0
        } else {
            outRect.left = space
        }

        //right
        if (pos % column == column - 1) {
            outRect.right = 0
        } else {
            outRect.right = space
        }
    }
}
