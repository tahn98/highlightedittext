package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.widget.FrameLayout
import androidx.appcompat.widget.AppCompatEditText
import com.example.myapplication.span.RoundedBgEditText

class MainActivity : AppCompatActivity() {
    private lateinit var edt: HighlightEditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        edt = findViewById(R.id.ed_highlight)
        edt.setContent("Generate Python code for Hello World")
            .submitKeywords(listOf("Python", "Hello World"))
            .highlightKeywords()
    }
}