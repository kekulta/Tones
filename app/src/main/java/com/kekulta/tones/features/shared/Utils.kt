package com.kekulta.tones.features.shared


import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.util.TypedValue
import android.view.View
import android.view.animation.DecelerateInterpolator
import android.widget.TextView
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.allViews
import androidx.core.view.setMargins
import androidx.fragment.app.Fragment
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView.LayoutParams
import com.google.android.material.color.MaterialColors
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope

fun View.hide() {
    visibility = View.INVISIBLE
}

fun View.show() {
    visibility = View.VISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun View.disableInteractions() {
    setOnTouchListener { _, _ -> true }
    allViews.forEach { it.setOnTouchListener { _, _ -> true } }
}

fun View.enableInteractions() {
    setOnTouchListener(null)
    allViews.forEach { it.setOnTouchListener(null) }
}

val View.lifecycleScope: CoroutineScope?
    get() = findViewTreeLifecycleOwner()?.lifecycleScope

fun TextView.animateTextColor(@ColorInt colorFrom: Int, @ColorInt colorTo: Int) {
    val colorAnimator = ValueAnimator.ofObject(ArgbEvaluator(), colorFrom, colorTo)
    colorAnimator.duration = 300
    colorAnimator.interpolator = DecelerateInterpolator()
    colorAnimator.addUpdateListener { animator ->
        setTextColor(animator.animatedValue as Int)
    }
    colorAnimator.start()
}

fun View.animateWidth(old: Int, new: Int) {
    val widthAnimator = ValueAnimator.ofInt(old, new)
    widthAnimator.duration = 300
    widthAnimator.interpolator = DecelerateInterpolator()
    widthAnimator.addUpdateListener { animation ->
        layoutParams.width = animation.animatedValue as Int
        requestLayout()
    }
    widthAnimator.start()
}

fun View.setMargins(margin: Int) {
    layoutParams = LayoutParams(layoutParams).apply {
        this.setMargins(margin)
    }
}

fun View.setPadding(padding: Int) {
    setPadding(padding, padding, padding, padding)
}

fun View.getColorStateList(@ColorRes resId: Int): ColorStateList {
    return ColorStateList.valueOf(getColor(resId))
}

@ColorInt
fun View.getColor(@ColorRes resId: Int): Int {
    return resources.getColor(resId, context.theme)
}

@ColorInt
fun View.getMaterialColor(@AttrRes resId: Int): Int {
    return MaterialColors.getColor(this, resId)
}


fun View.getMaterialColorStateList(@AttrRes resId: Int): ColorStateList {
    return ColorStateList.valueOf(getMaterialColor(resId))
}

fun View.getDrawable(@DrawableRes resId: Int): Drawable? {
    return ResourcesCompat.getDrawable(resources, resId, context.theme)
}

fun Context.dp(dp: Number): Float {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP, dp.toFloat(), resources.displayMetrics
    )
}

fun View.dp(dp: Number): Float {
    return context.dp(dp)
}

fun Fragment.dp(dp: Number): Float {
    return requireContext().dp(dp)
}

fun View.snackbar(
    text: String,
    duration: Int = Snackbar.LENGTH_SHORT,
    actionText: String = "",
    action: ((snackbar: Snackbar) -> Unit)? = null
) {
    Snackbar.make(this, text, duration).apply {
        setAction(actionText) {
            action?.invoke(this)
        }
        show()
    }
}