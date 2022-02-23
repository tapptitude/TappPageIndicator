package com.tapptitude.pageindicator.adapter

import android.widget.ImageView
import androidx.core.view.updateLayoutParams
import androidx.recyclerview.widget.RecyclerView
import com.tapptitude.pageindicator.indicator.IndicatorStyle
import com.tapptitude.pageindicator.util.extensions.toPx

class IndicatorViewHolder(
    private val indicator: ImageView
) : RecyclerView.ViewHolder(indicator) {

    private var indicatorData: IndicatorData? = null

    fun bind(indicatorData: IndicatorData) {
        this.indicatorData = indicatorData

        indicator.updateLayoutParams {
            val indicatorSize = indicatorData.indicatorStyle.size

            width = indicatorSize.width.toPx()
            height = indicatorSize.height.toPx()
        }
        setState(false)
    }

    fun setState(isSelected: Boolean) {
        if (isSelected) {
            setSelectedState()
        } else {
            setUnselectedState()
        }
    }

    private fun setUnselectedState() {
        val indicatorData = this.indicatorData ?: return

        when (val indicatorStyle = indicatorData.indicatorStyle) {
            is IndicatorStyle.DrawableIndicator -> indicator.setImageDrawable(indicatorStyle.unselectedDrawable)
            is IndicatorStyle.ShapeIndicator -> indicator.setBackgroundColor(indicatorStyle.unselectedColor)
        }

        indicator.alpha = indicatorData.indicatorStyle.unselectedIndicatorAlpha
    }

    private fun setSelectedState() {
        val indicatorData = this.indicatorData ?: return

        when (val indicatorStyle = indicatorData.indicatorStyle) {
            is IndicatorStyle.DrawableIndicator -> indicator.setImageDrawable(indicatorStyle.selectedDrawable)
            is IndicatorStyle.ShapeIndicator -> indicator.setBackgroundColor(indicatorStyle.selectedColor)
        }

        indicator.alpha = SELECTED_INDICATOR_ALPHA
    }

    companion object {
        private const val SELECTED_INDICATOR_ALPHA = 1f
    }
}