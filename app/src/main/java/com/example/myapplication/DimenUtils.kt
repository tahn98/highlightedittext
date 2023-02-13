package com.example.myapplication

import android.content.Context
import android.content.res.Resources
import android.util.DisplayMetrics


object DimenUtils {
    fun convertDpToPx(dp: Float): Float{
        return dp * (Resources.getSystem().displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
    }
}