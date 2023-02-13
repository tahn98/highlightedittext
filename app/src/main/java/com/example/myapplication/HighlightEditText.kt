package com.example.myapplication

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.text.Spannable
import android.text.style.StyleSpan
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.appcompat.widget.AppCompatEditText
import com.example.myapplication.databinding.LayoutHighlightEdtBinding

/**
 * EditText to support highlight key
 */
class HighlightEditText(context: Context, attrs: AttributeSet?, defStyle: Int) :
    FrameLayout(context, attrs, defStyle) {

    private var keywords: MutableSet<String> = mutableSetOf()
    private var mKeywordMap = mutableMapOf<String, Pair<Int, Int>>()

    constructor(context: Context, attrs: AttributeSet) : this(context, attrs, 0)
    constructor(context: Context) : this(context, null, 0)

    private var binding: LayoutHighlightEdtBinding =
        LayoutHighlightEdtBinding.inflate(LayoutInflater.from(context), this, true)
    private val editText: AppCompatEditText = binding.edt

    init {
    }

    //match keyword in string
    fun submitKeywords(keywords: List<String>?){
        if(keywords.isNullOrEmpty()) return
        keywords.toSet().let {
            this.keywords.clear()
            this.keywords.addAll(it)
        }
    }

    fun getKeywordListInContent(){

    }

    private fun spannable(){
        val boldSpan = StyleSpan(Typeface.BOLD)
        val start: Int = 2
        val end: Int = 4
        val flag = Spannable.SPAN_INCLUSIVE_INCLUSIVE
        editText.text?.setSpan(boldSpan, start, end, flag)

        val bg = RoundedBackgroundSpan(Color.BLUE, Color.BLACK, 16 * resources.displayMetrics.scaledDensity)

        editText.text?.setSpan(bg,2, 4, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
    }

    private fun background(){
    }

}