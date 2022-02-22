package com.tapptitude.pageindicator.adapter

import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.tapptitude.pageindicator.indicator.IndicatorStyle

class IndicatorViewHolder(
    private val indicator: ImageView
) : RecyclerView.ViewHolder(indicator) {

    fun bind(indicatorData: IndicatorData) {
        if (indicatorData.indicatorStyle is IndicatorStyle.DrawableIndicator) {
            indicator.setImageDrawable(indicatorData.indicatorStyle.selectedDrawable)
        }
    }
}