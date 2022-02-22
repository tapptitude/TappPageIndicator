package com.tapptitude.pageindicator

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tapptitude.pageindicator.adapter.TappPageIndicatorAdapter
import com.tapptitude.pageindicator.indicator.IndicatorStyle

class TappPageIndicator @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : RecyclerView(context, attrs, defStyleAttr) {

    @Orientation
    private var orientation: Int = HORIZONTAL

    private val pageIndicatorAdapter by lazy { TappPageIndicatorAdapter() }
    /** TBD:
     *
     * The viewHolders of this adapter will have a simple layout containing an ImageView
     * The items will be data classes describing how ImageView looks (background, corners, sizing etc.)
     * By default all viewHolders will appear unselected
     * A ViewHolder's view will be changed through the layoutManager's getChildAt(index) callback, see [getViewAtPosition]
     */

    /**
     * Determines if the scrolling should circle back to top/bottom when using the [next]/[previous] methods
     */
    private var isInfinitelyScrollable: Boolean = false
    var currentPage: Int = 0
        private set

    init {
        layoutManager = LinearLayoutManager(context, orientation, false)

        adapter = pageIndicatorAdapter
    }

    /**
     * Styling set through this method or [setIndicatorStyle] & [setIndicatorSizing] will override xml attributes accordingly
     */
    fun initPageIndicator(
        indicatorStyle: IndicatorStyle,
        pageIndicatorSizing: TappPageIndicatorSizing,
        @Orientation
        orientation: Int = HORIZONTAL,
        isInfinitelyScrollable: Boolean = false
    ) = Unit

    /**
     * [setIndicatorStyle] & [setIndicatorSizing] will submit a new list tom [pageIndicatorAdapter] and force a layoutManager reset
     * in order to make sure all viewHolders were assigned the appropriate styling
     */
    fun setIndicatorStyle(indicatorStyle: IndicatorStyle) = Unit

    fun setIndicatorSizing(pageIndicatorSizing: TappPageIndicatorSizing) = Unit

    /**
     * [next] & [previous] methods are syntax sugar wrappers for [setCurrentlySelectedPage]
     * They change the current page by +/- 1
     */
    fun next(smoothScroll: Boolean = true) = Unit

    fun previous(smoothScroll: Boolean = true) = Unit

    /**
     * Using [getViewAtPosition] will change the previous page to unselected state and the current page to selected state
     *
     * Performs smooth scroll if necessary and adjusts the scrolling position of the RecyclerView
     */
    fun setCurrentlySelectedPage(pageIndex: Int, smoothScroll: Boolean = true) = Unit

    private fun getViewAtPosition(position: Int): View? = layoutManager?.findViewByPosition(position)
}