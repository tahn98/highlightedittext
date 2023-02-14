package com.example.myapplication.span

import android.content.Context
import android.graphics.Canvas
import android.text.Spanned
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.graphics.withTranslation


//refer: https://github.com/googlearchive/android-text/tree/master/RoundedBackground-Kotlin
class RoundedBgEditText : AppCompatEditText {

    private val textRoundedBgHelper: TextRoundedBgHelper

    @JvmOverloads
    constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = android.R.attr.editTextStyle
    ) : super(context, attrs, defStyleAttr) {
        val attributeReader = TextRoundedBgAttributeReader(context, attrs)
        textRoundedBgHelper = TextRoundedBgHelper(
            horizontalPadding = attributeReader.horizontalPadding,
            verticalPadding = attributeReader.verticalPadding,
            drawable = attributeReader.drawable,
            drawableLeft = attributeReader.drawableLeft,
            drawableMid = attributeReader.drawableMid,
            drawableRight = attributeReader.drawableRight
        )
    }

    override fun onDraw(canvas: Canvas) {
        // need to draw bg first so that text can be on top during super.onDraw()
        if (text is Spanned && layout != null) {
            canvas.withTranslation(totalPaddingLeft.toFloat(), totalPaddingTop.toFloat()) {
                textRoundedBgHelper.draw(canvas, text as Spanned, layout)
            }
        }
        super.onDraw(canvas)
    }
}