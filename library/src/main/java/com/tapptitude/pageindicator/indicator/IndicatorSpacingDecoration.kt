package com.tapptitude.pageindicator.indicator

import android.graphics.Rect
import android.view.View
import androidx.annotation.IntRange
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.HORIZONTAL
import androidx.recyclerview.widget.RecyclerView.Orientation
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import com.tapptitude.pageindicator.util.DpValue

class IndicatorSpacingDecoration(
    @DpValue
    @IntRange(from = 0)
    private val spacing: Int,
    @Orientation
    private val orientation: Int
) : ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val position = parent.getChildAdapterPosition(view)
        val itemCount = parent.adapter?.itemCount ?: 0
        if (orientation == HORIZONTAL) {
            if (position != 0)
                outRect.left = spacing / 2
            if (position != itemCount - 1)
                outRect.right = spacing / 2
        } else {
            if (position != 0)
                outRect.top = spacing / 2
            if (position != itemCount - 1)
                outRect.bottom = spacing / 2
        }
    }
}