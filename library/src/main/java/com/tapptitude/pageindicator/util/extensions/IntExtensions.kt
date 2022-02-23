package com.tapptitude.pageindicator.util.extensions

import android.content.res.Resources
import kotlin.math.roundToInt

internal fun Int.toDp(): Int = (this.toFloat() / Resources.getSystem().displayMetrics.density).roundToInt()

internal fun Int.toPx(): Int = (this.toFloat() * Resources.getSystem().displayMetrics.density).roundToInt()