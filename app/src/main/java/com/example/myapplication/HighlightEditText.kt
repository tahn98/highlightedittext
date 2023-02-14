package com.example.myapplication

import android.content.Context
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
                //todo: if user selected all or copy ?
                val userChange = Math.abs(count - before) == 1
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
    }

    fun updateSpannableMargin(){
        val keys = mKeywordMap.keys.toList()
        val spannableStringBuilder = SpannableString(mEditText.text)
        keys.forEach {
            val spannableKeyWordPosition = mKeywordMap[it] ?: return@forEach
//            spannableStringBuilder[spannableKeyWordPosition.first - 1].
        }
    }

    fun unHighlightKeywords(){

    }

    fun isModifyKeyword(): Boolean{
        val content = mEditText.text
        if(content.isNullOrEmpty()) return false
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