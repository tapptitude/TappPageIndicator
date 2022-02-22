package com.tapptitude.pageindicator.adapter

import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.tapptitude.pageindicator.R
import com.tapptitude.pageindicator.util.layoutInflater

class TappPageIndicatorAdapter : ListAdapter<IndicatorData, IndicatorViewHolder>(indicatorsDiffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IndicatorViewHolder =
        IndicatorViewHolder(parent.layoutInflater.inflate(R.layout.item_indicator, parent) as ImageView)

    override fun onBindViewHolder(holder: IndicatorViewHolder, position: Int) = holder.bind(getItem(position))

    companion object {

        private val indicatorsDiffUtil by lazy { initIndicatorsDiffUtil() }

        private fun initIndicatorsDiffUtil() = object : DiffUtil.ItemCallback<IndicatorData>() {

            override fun areItemsTheSame(oldItem: IndicatorData, newItem: IndicatorData): Boolean =
                oldItem.index == newItem.index

            override fun areContentsTheSame(oldItem: IndicatorData, newItem: IndicatorData): Boolean =
                oldItem == newItem
        }
    }
}