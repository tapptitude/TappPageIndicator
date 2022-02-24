package com.tapptitude.pageindicator.util.extensions

import android.view.LayoutInflater
import android.view.ViewGroup

internal val ViewGroup.layoutInflater: LayoutInflater
    get() = LayoutInflater.from(context)