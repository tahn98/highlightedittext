package com.example.myapplication

import android.app.ActionBar.LayoutParams
import android.os.Bundle
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.Animation
import android.view.animation.ScaleAnimation
import android.view.animation.Transformation
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    private lateinit var edt: HighlightEditText
    private lateinit var dialog: View
    private lateinit var dummyView: View
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        edt = findViewById(R.id.ed_highlight)
        edt.setContent("Generate Python code for Hello World")
            .submitKeywords(listOf("Python", "Hello World"))
            .highlightKeywords()

        dialog = findViewById(R.id.dialog)
        dummyView = findViewById(R.id.dummyView)

//        expand(dialog)
//        Helper.resize(
//            dialog,
//            100f,
//            4.0f,
//            1.1f,
//            1.0f,
//            0.0f,
//            0.0f,
//            5000,
//            null,
//            null,
//            null);

//        val x = resources.displayMetrics.widthPixels / 2
//        val anim: Animation =
//            ScaleAnimation(
//            dummyView.x,
//            0f,  // Start and end values for the X axis scaling
//            1f,
//                1f,  // Start and end values for the Y axis scaling
//            Animation.RELATIVE_TO_SELF, 0f,  // Pivot point of X scaling
//            Animation.RELATIVE_TO_SELF, 1f
//        ) // Pivot point of Y scaling
//
//        anim.fillAfter = true // Needed to keep the result of the animation
//
//        anim.duration = 1000
//        dialog.startAnimation(anim)

        val anim: Animation = ScaleAnimation(
            0.2f, 1f,  // Start and end values for the X axis scaling
            1f, 1f,  // Start and end values for the Y axis scaling
            Animation.RELATIVE_TO_SELF, 0.5f,  // Pivot point of X scaling
            Animation.RELATIVE_TO_SELF, 0f
        ) // Pivot point of Y scaling
        anim.interpolator = AccelerateDecelerateInterpolator()
        anim.fillAfter = true // Needed to keep the result of the animation
        anim.duration = 1000
        dialog.startAnimation(anim)
    }
    fun scaleView(v: View, startScale: Float, endScale: Float) {
        val anim: Animation = ScaleAnimation(
            1f, 1f,  // Start and end values for the X axis scaling
            startScale, endScale,  // Start and end values for the Y axis scaling
            Animation.RELATIVE_TO_SELF, 0f,  // Pivot point of X scaling
            Animation.RELATIVE_TO_SELF, 1f
        ) // Pivot point of Y scaling
        anim.fillAfter = true // Needed to keep the result of the animation
        anim.duration = 1000
        v.startAnimation(anim)
    }

    fun expand(v: View) {
        val matchParentMeasureSpec =
            View.MeasureSpec.makeMeasureSpec((v.parent as View).width, View.MeasureSpec.EXACTLY)
        val wrapContentMeasureSpec =
            View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
        v.measure(matchParentMeasureSpec, wrapContentMeasureSpec)
        val targetHeight = 100

        // Older versions of android (pre API 21) cancel animations for views with a height of 0.
        v.layoutParams.height = 1
        v.visibility = View.VISIBLE
        val a: Animation = object : Animation() {
            override fun applyTransformation(interpolatedTime: Float, t: Transformation?) {
                v.layoutParams.height =
                    if (interpolatedTime == 1f) LayoutParams.WRAP_CONTENT else (targetHeight * interpolatedTime).toInt()
                v.requestLayout()
            }

            override fun willChangeBounds(): Boolean {
                return true
            }
        }

        // Expansion speed of 1dp/ms
//        a.duration = (targetHeight / v.context.resources.displayMetrics.density).toInt().toLong()
        a.duration = 10000
        v.startAnimation(a)
    }

}