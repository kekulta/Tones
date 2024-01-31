package com.kekulta.tones.features.shared.utils

import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.content.res.ColorStateList
import android.view.View
import android.view.animation.DecelerateInterpolator
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.annotation.Dimension

fun View.animateBackgroundTint(
    @ColorInt colorFrom: Int, @ColorInt colorTo: Int
) {
    if (colorFrom == colorTo) return
    val colorAnimator = ValueAnimator.ofObject(ArgbEvaluator(), colorFrom, colorTo)
    colorAnimator.duration = 300
    colorAnimator.interpolator = DecelerateInterpolator()
    colorAnimator.addUpdateListener { animator ->
        backgroundTintList = ColorStateList.valueOf(animator.animatedValue as Int)
    }
    colorAnimator.start()
}

fun TextView.animateTextColor(@ColorInt colorFrom: Int, @ColorInt colorTo: Int) {
    if (colorFrom == colorTo) return
    val colorAnimator = ValueAnimator.ofObject(ArgbEvaluator(), colorFrom, colorTo)
    colorAnimator.duration = 300
    colorAnimator.interpolator = DecelerateInterpolator()
    colorAnimator.addUpdateListener { animator ->
        setTextColor(animator.animatedValue as Int)
    }
    colorAnimator.start()
}

fun View.animateWidth(@Dimension(unit = Dimension.DP) old: Int, @Dimension(unit = Dimension.DP) new: Int) {
    if (old == new) return
    val widthAnimator = ValueAnimator.ofInt(dip(old), dip(new))
    widthAnimator.duration = 300
    widthAnimator.interpolator = DecelerateInterpolator()
    widthAnimator.addUpdateListener { animation ->
        layoutParams.width = animation.animatedValue as Int
        requestLayout()
    }
    widthAnimator.start()
}