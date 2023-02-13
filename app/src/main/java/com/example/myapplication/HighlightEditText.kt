package com.example.myapplication

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.appcompat.widget.AppCompatEditText
import com.example.myapplication.databinding.LayoutHighlightEdtBinding

class HighlightEditText(context: Context, attrs: AttributeSet?, defStyle: Int) :
    AppCompatEditText(context, attrs, defStyle) {
    constructor(context: Context, attrs: AttributeSet) : this(context, attrs, 0)
    constructor(context: Context) : this(context, null, 0)

    private var binding: LayoutHighlightEdtBinding =
        LayoutHighlightEdtBinding.inflate(LayoutInflater.from(context), null, true)
}