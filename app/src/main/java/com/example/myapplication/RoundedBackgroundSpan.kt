package com.example.myapplication

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF

import android.text.style.ReplacementSpan
import kotlin.math.roundToInt


class RoundedBackgroundSpan(private val mBackgroundColor: Int, private val mTextColor: Int, private val mTextSize: Float, private val mPaddingVertical: Float = PADDING_X, private val mPaddingHorizontal: Float = PADDING_Y) :
    ReplacementSpan() {
    override fun draw(
        canvas: Canvas,
        text: CharSequence,
        start: Int,
        end: Int,
        x: Float,
        top: Int,
        y: Int,
        bottom: Int,
        paint: Paint
    ) {
        var newPaint: Paint = paint
        // make a copy for not editing the referenced paint
        newPaint = Paint(newPaint)
        newPaint.textSize = mTextSize

        // draw the rounded background
        newPaint.color = mBackgroundColor
        val textHeightWrapping: Float = DimenUtils.convertDpToPx(4f)
        val tagBottom = top + textHeightWrapping + 2*PADDING_Y + mTextSize + textHeightWrapping
        val tagRight = x + getTagWidth(text, start, end, newPaint)
        val rect = RectF(x, top.toFloat(), tagRight, tagBottom)
        canvas.drawRoundRect(rect, CORNER_RADIUS, CORNER_RADIUS, newPaint)

        // draw the text
        newPaint.color = mTextColor
        canvas.drawText(
            text,
            start,
            end,
            x + PADDING_X,
            tagBottom - PADDING_Y - textHeightWrapping - OFFSET_VERTICAL_NUMBER,
            newPaint
        )
    }

    private fun getTagWidth(text: CharSequence, start: Int, end: Int, paint: Paint): Int {
        return (2*PADDING_X + paint.measureText(text.subSequence(start, end).toString())).roundToInt()
    }

    override fun getSize(
        paint: Paint,
        text: CharSequence,
        start: Int,
        end: Int,
        fm: Paint.FontMetricsInt?
    ): Int {
        var newPaint: Paint = paint
        newPaint = Paint(newPaint) // make a copy for not editing the referenced paint
        newPaint.textSize = mTextSize
        return getTagWidth(text, start, end, newPaint)
    }

    companion object {
        private const val CORNER_RADIUS = 12f
        private val PADDING_X: Float = DimenUtils.convertDpToPx(12f)
        private val PADDING_Y: Float = DimenUtils.convertDpToPx(2f)
        private val OFFSET_VERTICAL_NUMBER: Float = DimenUtils.convertDpToPx(2f)
    }
}