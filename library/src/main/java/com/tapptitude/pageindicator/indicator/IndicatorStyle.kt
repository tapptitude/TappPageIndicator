package com.tapptitude.pageindicator.indicator

import android.graphics.Color
import android.graphics.drawable.Drawable
import androidx.annotation.FloatRange
import com.tapptitude.pageindicator.util.DpValue
import androidx.annotation.IntRange

/**
 *  Defines the style of the indicators
 *
 *  [spacing] represents the space between 2 indicators, set as ItemDecoration
 *
 *  [size] represents the size of an indicator width & height
 *
 *  [unselectedIndicatorAlpha]  represents the alpha of all the unselected indicators,
 *                             by default all indicators have the same alpha = 1f
 */
sealed class IndicatorStyle {

    @DpValue
    abstract val spacing: Int
    abstract val size: IndicatorSize
    abstract val unselectedIndicatorAlpha: Float

    /**
     *  Styling option that uses vector drawables provided by integrators
     *
     * [selectedDrawable] drawable used for the currently selected indicator
     *
     * [unselectedDrawable] drawable used for all the unselected indicators
     *
     * [unselectedIndicatorAlpha] is applied over the [unselectedDrawable], meaning any alpha already set to the vector drawable will
     * compound with [unselectedIndicatorAlpha]
     */
    data class DrawableIndicator(
        val selectedDrawable: Drawable,
        val unselectedDrawable: Drawable,
        @DpValue
        @IntRange(from = 0)
        override val spacing: Int,
        override val size: IndicatorSize,
        @FloatRange(from = 0.01, to = 1.0)
        override val unselectedIndicatorAlpha: Float = 1f
    ) : IndicatorStyle()

    /**
     *  Styling option that uses colors and a corner radius provided by integrators
     *
     * [cornerRadius] corner radius applied to all indicators
     *
     * [selectedColor] color used for the currently selected indicator
     *
     * [unselectedColor] color used for all the unselected indicators
     *
     * [unselectedIndicatorAlpha] is applied over the [unselectedColor], meaning any alpha already set to the Color will
     * compound with [unselectedIndicatorAlpha]
     */
    data class ShapeIndicator(
        @DpValue
        @IntRange(from = 0)
        val cornerRadius: Int,
        val selectedColor: Color,
        val unselectedColor: Color,
        @DpValue
        @IntRange(from = 0)
        override val spacing: Int,
        override val size: IndicatorSize,
        @FloatRange(from = 0.01, to = 1.0)
        override val unselectedIndicatorAlpha: Float = 0.75f
    ) : IndicatorStyle()
}