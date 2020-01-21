package com.danhtran.androidbaseproject.ui.customview

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by danhtran on 29/07/16.
 */

class DividerItemDecoration(context: Context, orientation: Int, sizeSpace: Int) : RecyclerView.ItemDecoration() {
    private var sizeSpace = 1

    private val mDivider: Drawable?

    private var mOrientation: Int = 0

    init {
        this.sizeSpace = sizeSpace
        val a = context.obtainStyledAttributes(ATTRS)
        mDivider = a.getDrawable(0)
        a.recycle()
        setOrientation(orientation)
    }

    fun setOrientation(orientation: Int) {
        if (orientation != HORIZONTAL_LIST && orientation != VERTICAL_LIST) {
            throw IllegalArgumentException("invalid orientation")
        }
        mOrientation = orientation
    }

    /* @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        if (mOrientation == VERTICAL_LIST) {
            drawVertical(c, parent);
        } else {
            drawHorizontal(c, parent);
        }
    }

    public void drawVertical(Canvas c, RecyclerView parent) {
        final int left = parent.getPaddingLeft();
        final int right = parent.getWidth() - parent.getPaddingRight();

        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                    .getLayoutParams();
            final int top = child.getBottom() + params.bottomMargin;
            final int bottom = top + SIZE;
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }
    }

    public void drawHorizontal(Canvas c, RecyclerView parent) {
        final int top = parent.getPaddingTop();
        final int bottom = parent.getHeight() - parent.getPaddingBottom();

        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                    .getLayoutParams();
            final int left = child.getRight() + params.rightMargin;
            final int right = left + SIZE;
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }
    }*/

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        //        if (parent.getChildAdapterPosition(view) != parent.getAdapter().getItemCount() - 1) {
        if (mOrientation == VERTICAL_LIST) {
            setVertical(outRect, parent, view)
            //                outRect.set(0, 0, 0, mDivider.getIntrinsicHeight());

        } else {
            setHorizontal(outRect, parent, view)
            //                outRect.set(0, 0, mDivider.getIntrinsicWidth(), 0);
        }
    }
    //    }

    private fun setVertical(outRect: Rect, parent: RecyclerView, view: View) {
        val pos = parent.getChildLayoutPosition(view)
        if (pos == parent.adapter!!.itemCount - 1) {
            outRect.set(0, 0, 0, 0)
        } else {
            outRect.set(0, 0, 0, sizeSpace)
        }
    }

    private fun setHorizontal(outRect: Rect, parent: RecyclerView, view: View) {
        val pos = parent.getChildLayoutPosition(view)
        if (pos == parent.adapter!!.itemCount - 1) {
            outRect.set(0, 0, 0, 0)
        } else {
            outRect.set(0, 0, sizeSpace, 0)
        }
    }

    companion object {
        private val SIZE = 1
        private val ATTRS = intArrayOf(android.R.attr.listDivider)

        val HORIZONTAL_LIST = LinearLayoutManager.HORIZONTAL

        val VERTICAL_LIST = LinearLayoutManager.VERTICAL
    }
}
