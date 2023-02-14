package com.example.myapplication.span

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import androidx.core.content.res.getDrawableOrThrow
import com.example.myapplication.R

class TextRoundedBgAttributeReader(context: Context, attrs: AttributeSet?) {

    val horizontalPadding: Int
    val verticalPadding: Int
    val drawable: Drawable
    val drawableLeft: Drawable
    val drawableMid: Drawable
    val drawableRight: Drawable

    init {
        val typedArray = context.obtainStyledAttributes(
            attrs,
            R.styleable.TextRoundedBgHelper,
            0,
            R.style.RoundedBgTextView
        )
        horizontalPadding = typedArray.getDimensionPixelSize(
            R.styleable.TextRoundedBgHelper_roundedTextHorizontalPadding,
            0
        )
        verticalPadding = typedArray.getDimensionPixelSize(
            R.styleable.TextRoundedBgHelper_roundedTextVerticalPadding,
            0
        )
        drawable = typedArray.getDrawableOrThrow(
            R.styleable.TextRoundedBgHelper_roundedTextDrawable
        )
        drawableLeft = typedArray.getDrawableOrThrow(
            R.styleable.TextRoundedBgHelper_roundedTextDrawableLeft
        )
        drawableMid = typedArray.getDrawableOrThrow(
            R.styleable.TextRoundedBgHelper_roundedTextDrawableMid
        )
        drawableRight = typedArray.getDrawableOrThrow(
            R.styleable.TextRoundedBgHelper_roundedTextDrawableRight
        )
        typedArray.recycle()
    }
}
