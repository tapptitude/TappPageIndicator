package com.tapptitude.pageindicator

import androidx.annotation.IntRange

/**
 * Defines how the [TappPageIndicator] RecyclerView should size itself, while also taking in consideration its orientation
 */
sealed class TappPageIndicatorSizing {

    /**
     *  This sizing mode will allow all indicators to be visible
     *  In order to do this it might override the provided size for the indicators
     *
     *  Based on the [TappPageIndicator] size and orientation, together with [totalIndicatorsCount] and indicator spacing it will calculate an
     *  appropriate size for individual indicators that will allow them all to be visible
     */
    data class FitAll(
        @IntRange(from = 1)
        val totalIndicatorsCount: Int
    ) : TappPageIndicatorSizing()

    /**
     *  This sizing mode will allow only a certain number of indicators to be visible
     *  The first/last visible indicator will always be an unselected one so the user is aware that there are more pages available,
     *  unless the first/last page is selected
     *
     *  Based on the [TappPageIndicator] size and orientation, together with [visibleIndicatorsCount] and indicator spacing it will calculate an
     *  appropriate size for itself in order to display the required amount of indicators
     */
    data class FixedNumberOfVisibleIndicators(
        @IntRange(from = 1)
        val visibleIndicatorsCount: Int
    ) : TappPageIndicatorSizing()
}
