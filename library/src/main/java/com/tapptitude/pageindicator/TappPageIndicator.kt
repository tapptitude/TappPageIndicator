package com.tapptitude.pageindicator

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.View
import androidx.annotation.IntRange
import androidx.core.view.doOnLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tapptitude.pageindicator.adapter.IndicatorData
import com.tapptitude.pageindicator.adapter.IndicatorViewHolder
import com.tapptitude.pageindicator.adapter.TappPageIndicatorAdapter
import com.tapptitude.pageindicator.indicator.IndicatorSize
import com.tapptitude.pageindicator.indicator.IndicatorSpacingDecoration
import com.tapptitude.pageindicator.indicator.IndicatorStyle
import com.tapptitude.pageindicator.util.DpValue
import com.tapptitude.pageindicator.util.extensions.toPx

class TappPageIndicator @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : RecyclerView(context, attrs, defStyleAttr) {

    @Orientation
    var pageIndicatorOrientation: Int = HORIZONTAL
        private set

    private val pageIndicatorAdapter by lazy { TappPageIndicatorAdapter() }
    /**
     * The viewHolders of this adapter will have a simple layout containing an ImageView
     * The items will be data classes describing how ImageView looks (background, corners, sizing etc.)
     * By default all viewHolders will appear unselected
     * A ViewHolder's view will be changed through the layoutManager's getChildAt(index) callback, see [getViewAtPosition]
     */

    /**
     * Determines if the scrolling should circle back to top/bottom when using the [next]/[previous] methods
     */
    private var isInfinitelyScrollable: Boolean = false

    /**
     * [indicatorSpacing] represents the space between 2 indicators, set as ItemDecoration
     */
    @DpValue
    @IntRange(from = 0)
    private var indicatorSpacing: Int = 0
    private var indicatorStyle: IndicatorStyle = defaultIndicatorStyle
    private lateinit var pageIndicatorSizing: TappPageIndicatorSizing
    var currentPage: Int = 0
        private set

    //TODO: When extracting attrs, map pageIndicatorOrientation enum values to @RecyclerView.Orientation equivalents
    init {
        adapter = pageIndicatorAdapter
    }

    /**
     * Styling set through this method or [setIndicatorStyle] & [setIndicatorSizing] will override xml attributes accordingly
     */
    fun initPageIndicator(
        @IntRange(from = 1)
        indicatorsCount: Int,
        pageIndicatorSizing: TappPageIndicatorSizing = TappPageIndicatorSizing.FitAll(indicatorsCount),
        indicatorStyle: IndicatorStyle = defaultIndicatorStyle,
        @DpValue
        @IntRange(from = 0)
        indicatorSpacing: Int = 0,
        @Orientation
        pageIndicatorOrientation: Int = HORIZONTAL,
        isInfinitelyScrollable: Boolean = false
    ) {
        setIndicatorStyle(indicatorStyle)
        setIndicatorSizing(pageIndicatorSizing)
        setOrientation(pageIndicatorOrientation)
        setIndicatorSpacing(indicatorSpacing)
        this.isInfinitelyScrollable = isInfinitelyScrollable
        placeIndicators(indicatorsCount)
    }

    /**
     * [setIndicatorStyle] & [setIndicatorSizing] will submit a new list tom [pageIndicatorAdapter] and force a layoutManager reset
     * in order to make sure all viewHolders were assigned the appropriate styling
     */
    //TODO: Update UI
    fun setIndicatorStyle(indicatorStyle: IndicatorStyle) {
        this.indicatorStyle = indicatorStyle
    }

    //TODO: Update UI
    fun setIndicatorSizing(pageIndicatorSizing: TappPageIndicatorSizing) {
        this.pageIndicatorSizing = pageIndicatorSizing
    }

    fun setIndicatorSpacing(
        @DpValue
        @IntRange(from = 0)
        indicatorSpacing: Int
    ) {
        removeExistingSpacingDecoration()

        this.indicatorSpacing = indicatorSpacing
        addItemDecoration(IndicatorSpacingDecoration(indicatorSpacing.toPx(), pageIndicatorOrientation))
    }

    private fun removeExistingSpacingDecoration() {
        if (itemDecorationCount > 0) {
            (0 until itemDecorationCount).forEach { decorationIndex ->
                val decoration = getItemDecorationAt(decorationIndex)
                if (decoration is IndicatorSpacingDecoration) {
                    removeItemDecoration(decoration)
                }
            }
        }
    }

    //TODO: Check if UI is updated accordingly
    fun setOrientation(@Orientation pageIndicatorOrientation: Int) {
        this.pageIndicatorOrientation = pageIndicatorOrientation
        layoutManager = LinearLayoutManager(context, pageIndicatorOrientation, false)
        doOnLayout {
            updateIndicatorViewHolderState(currentPage, true)
        }
    }

    private fun placeIndicators(
        @IntRange(from = 1) indicatorsCount: Int,
        @IntRange(from = 0) selectedPosition: Int = 0
    ) {
        val indicatorsList = mutableListOf<IndicatorData>().apply {
            (0 until indicatorsCount).forEach { index ->
                add(IndicatorData(index, indicatorStyle))
            }
        }
        pageIndicatorAdapter.submitList(indicatorsList)
        doOnLayout {
            currentPage = selectedPosition
            getViewAtPosition(selectedPosition)?.let { selectedIndicator ->
                (getChildViewHolder(selectedIndicator) as? IndicatorViewHolder)?.setState(true)
            }
        }
    }

    /**
     * [next] & [previous] methods are syntax sugar wrappers for [setCurrentlySelectedPage]
     * They change the current page by +/- 1
     */
    //TODO: Implement taking in consideration smoothScroll & isInfinitelyScrollable
    fun next(smoothScroll: Boolean = true) {
        if (currentPage < pageIndicatorAdapter.itemCount - 1) {
            changeSelectedIndicator(currentPage + 1)
        }
    }

    //TODO: Implement taking in consideration smoothScroll & isInfinitelyScrollable
    fun previous(smoothScroll: Boolean = true) {
        if (currentPage > 0) {
            changeSelectedIndicator(currentPage - 1)
        }
    }

    /**
     * Using [getViewAtPosition] will change the previous page to unselected state and the current page to selected state
     *
     * Performs smooth scroll if necessary and adjusts the scrolling position of the RecyclerView
     */
    fun setCurrentlySelectedPage(pageIndex: Int, smoothScroll: Boolean = true) {
        changeSelectedIndicator(pageIndex)
    }

    private fun changeSelectedIndicator(newSelectedPage: Int) {
        updateIndicatorViewHolderState(currentPage, false)

        currentPage = newSelectedPage

        updateIndicatorViewHolderState(currentPage, true)
    }

    private fun getViewAtPosition(position: Int): View? = layoutManager?.findViewByPosition(position)

    private fun updateIndicatorViewHolderState(position: Int, isSelected: Boolean) {
        getViewAtPosition(position)?.let { selectedIndicator ->
            (getChildViewHolder(selectedIndicator) as? IndicatorViewHolder)?.setState(isSelected)
        }
    }

    companion object {

        private val defaultIndicatorStyle: IndicatorStyle by lazy { initDefaultIndicatorStyle() }

        private const val DEFAULT_INDICATOR_CORNER_RADIUS: Int = 8
        private const val DEFAULT_INDICATOR_SIZE: Int = 12

        // TODO: Revise default shape after implementing custom ImageView
        private fun initDefaultIndicatorStyle(): IndicatorStyle =
            IndicatorStyle.ShapeIndicator(
                DEFAULT_INDICATOR_CORNER_RADIUS,
                Color.LTGRAY,
                Color.LTGRAY,
                IndicatorSize(DEFAULT_INDICATOR_SIZE, DEFAULT_INDICATOR_SIZE)
            )
    }
}