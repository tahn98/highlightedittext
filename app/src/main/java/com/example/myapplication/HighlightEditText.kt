package com.example.myapplication

import android.content.Context
import android.graphics.Color
import android.text.Editable
import android.text.Spannable
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.TextWatcher
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.example.myapplication.databinding.LayoutHighlightEdtBinding
import com.example.myapplication.span.RoundedBgEditText
import kotlin.math.abs

/**
 * EditText to support highlight key
 */
class HighlightEditText(context: Context, attrs: AttributeSet?, defStyle: Int) :
    FrameLayout(context, attrs, defStyle) {

    constructor(context: Context, attrs: AttributeSet) : this(context, attrs, 0)
    constructor(context: Context) : this(context, null, 0)

    private var mKeywords: MutableSet<String> = mutableSetOf()
    private var mKeywordMap = mutableMapOf<String, Pair<Int, Int>>()
    private var mBinding: LayoutHighlightEdtBinding = LayoutHighlightEdtBinding.inflate(LayoutInflater.from(context), this, true)
    private val mEditText: RoundedBgEditText = mBinding.edt
    private var mContent: String? = null
    private var isHighlightFirstTime: Boolean = false
    private var prevSelection = 0

    init {
        mEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                val userChange = abs(count - before) == 1
                if(userChange && isModifyKeyword()){
                    Log.d("tahn_check", "onTextChanged")
                    validKeywords()
                }
            }
        })
    }

    fun setContent(content: String?): HighlightEditText{
        mContent = content
        mEditText.setText(content)
        return this
    }

    fun setContentAndHoldSelection(content: String?){
        mContent = content
        prevSelection = mEditText.selectionStart
        mEditText.setText(content)
        mEditText.setSelection(prevSelection)
    }

    private fun setContentAndHoldSelection(content: SpannableStringBuilder?){
        prevSelection = mEditText.selectionStart
        mEditText.text = content
        mEditText.setSelection(prevSelection)
    }

    fun submitKeywords(keywords: List<String>?): HighlightEditText{
        if(mContent.isNullOrEmpty()) return this
        if(keywords.isNullOrEmpty()) return this
        keywords.toSet().let {
            mKeywords.clear()
            mKeywords.addAll(it)
        }

        mKeywords.forEach { keyword ->
            val indexOfKeyword = mContent!!.indexOf(keyword, ignoreCase = true)
            if(indexOfKeyword >= 0){
                mKeywordMap[keyword] = Pair(indexOfKeyword, indexOfKeyword + keyword.length)
            }
        }

        return this
    }

    fun highlightKeywords(){
        val spannableString = SpannableString(mContent)
        mKeywordMap.forEach {
            spannableString.setSpan(android.text.Annotation("", "rounded"), it.value.first, it.value.second, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        }
        mEditText.setText(spannableString)
        isHighlightFirstTime = true

        updateSpannableMargin()
    }

    /**
     * cheat replace whitespace to ReplacementSpan to make margin between Spannable
     */
    private fun updateSpannableMargin(){
        val keys = mKeywordMap.keys.toList()
        val spannableStringBuilder = SpannableStringBuilder(mEditText.text)
        keys.forEach {
            val spannableKeyWordPositionStart = spannableStringBuilder.indexOf(it)
            val spannableKeyWordPositionEnd = spannableKeyWordPositionStart + it.length

            val spaceBackgroundSpan = RoundedBackgroundSpan(Color.BLUE, Color.TRANSPARENT, 0f, mPaddingHorizontal = DimenUtils.convertDpToPx(30f))
            val spaceSpannable = SpannableString("\u2002")
            spaceSpannable.setSpan(spaceBackgroundSpan,0, spaceSpannable.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

            //check white space then replace
            if(spannableStringBuilder[spannableKeyWordPositionStart - 1].isWhitespace()) spannableStringBuilder.replace(spannableKeyWordPositionStart - 1, spannableKeyWordPositionStart, spaceSpannable)
            if(spannableKeyWordPositionEnd + 1 <= spannableStringBuilder.length){
                if(spannableStringBuilder[spannableKeyWordPositionEnd].isWhitespace()) spannableStringBuilder.replace(spannableKeyWordPositionEnd, spannableKeyWordPositionEnd + 1, spaceSpannable)
            }
        }

        setContentAndHoldSelection(spannableStringBuilder)
    }


    fun isModifyKeyword(): Boolean{
        val content = mEditText.text
        if(content.isNullOrEmpty()) return false
        if(mKeywordMap.keys.size == 0) return false
        var keywordSize = 0
        mKeywordMap.forEach {
            if(content.contains(it.key)){
                keywordSize++
            }
        }

        return keywordSize != mKeywordMap.size
    }

    fun validKeywords(){
        if(!isHighlightFirstTime) return
        val content = mEditText.text.toString()
        val spannableString = SpannableString(content)

        mKeywordMap.clear()
        mKeywords.forEach { keyword ->
            val indexOfKeyword = content.indexOf(keyword, ignoreCase = false)
            if(indexOfKeyword >= 0){
                mKeywordMap[keyword] = Pair(indexOfKeyword, indexOfKeyword + keyword.length)
            }
        }

        mKeywords.clear()
        mKeywordMap.forEach {
            spannableString.setSpan(android.text.Annotation("", "rounded"), it.value.first, it.value.second, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            mKeywords.add(it.key)
        }

        prevSelection = mEditText.selectionStart
        mEditText.setText(spannableString)
        mEditText.setSelection(prevSelection)
    }
}