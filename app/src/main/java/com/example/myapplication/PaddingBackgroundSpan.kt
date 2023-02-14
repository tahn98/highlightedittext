package com.example.myapplication

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.text.style.LineBackgroundSpan


class PaddingBackgroundColorSpan(private val mBackgroundColor: Int, private val mPadding: Int) :
    LineBackgroundSpan {
    private val mBgRect: Rect

    init {
        // Precreate rect for performance
        mBgRect = Rect()
    }

    override fun drawBackground(
        canvas: Canvas,
        paint: Paint,
        left: Int,
        right: Int,
        top: Int,
        baseline: Int,
        bottom: Int,
        text: CharSequence,
        start: Int,
        end: Int,
        lineNumber: Int
    ) {
        val textWidth = Math.round(paint.measureText(text, start, end)).toInt()
        val paintColor: Int = paint.getColor()
        // Draw the background
        mBgRect.set(
            left - mPadding,
            top - if (lineNumber == 0) mPadding / 2 else -(mPadding / 2),
            left + textWidth + mPadding,
            bottom + mPadding / 2
        )
        paint.color = mBackgroundColor
        canvas.drawRect(mBgRect, paint)
        paint.color = paintColor
    }
}