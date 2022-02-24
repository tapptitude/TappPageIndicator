package com.tapptitude.pageindicator.indicator

import com.tapptitude.pageindicator.util.DpValue
import androidx.annotation.IntRange

/**
 *  Defines the size of the indicators using dp values
 */
data class IndicatorSize(
    @IntRange(from = 1)
    @DpValue val width: Int,
    @IntRange(from = 1)
    @DpValue val height: Int
)
