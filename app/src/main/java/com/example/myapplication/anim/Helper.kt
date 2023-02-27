package com.example.myapplication.anim

import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.Animation
import android.view.animation.Animation.AnimationListener
import android.view.animation.ScaleAnimation
import java.util.concurrent.Callable


object Helper {
    fun resize(
        view: View,
        fromX: Float,
        toX: Float,
        fromY: Float,
        toY: Float,
        pivotX: Float,
        pivotY: Float,
        duration: Int
    ) {
        resize(
            view,
            fromX,
            toX,
            fromY,
            toY,
            pivotX,
            pivotY,
            duration,
            null,
            null,
            null
        )
    }

    /**
     * Resize a view with handlers.
     *
     * @param view     A view to resize.
     * @param fromX    X scale at start.
     * @param toX      X scale at end.
     * @param fromY    Y scale at start.
     * @param toY      Y scale at end.
     * @param pivotX   Rotate angle at start.
     * @param pivotY   Rotate angle at end.
     * @param duration Animation duration.
     * @param start    Actions on animation start. Otherwise NULL.
     * @param repeat   Actions on animation repeat. Otherwise NULL.
     * @param end      Actions on animation end. Otherwise NULL.
     */
    fun resize(
        view: View,
        fromX: Float,
        toX: Float,
        fromY: Float,
        toY: Float,
        pivotX: Float,
        pivotY: Float,
        duration: Int,
        start: Callable<View>?,
        repeat: Callable<View>?,
        end: Callable<View>?
    ) {
        val animation: Animation
        animation = ScaleAnimation(
            fromX,
            toX,
            fromY,
            toY,
            Animation.RELATIVE_TO_SELF,
            pivotX,
            Animation.RELATIVE_TO_SELF,
            pivotY
        )
        animation.setDuration(
            duration.toLong()
        )
        animation.setInterpolator(
            AccelerateDecelerateInterpolator()
        )
        animation.setFillAfter(true)
        view.startAnimation(
            animation
        )
        animation.setAnimationListener(object : AnimationListener {
            override fun onAnimationStart(animation: Animation) {
                if (start != null) {
                    try {
                        start.call()
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }

            override fun onAnimationEnd(animation: Animation) {
                if (end != null) {
                    try {
                        end.call()
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }

            override fun onAnimationRepeat(
                animation: Animation
            ) {
                if (repeat != null) {
                    try {
                        repeat.call()
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }
        })
    }
}