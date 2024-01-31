package com.kekulta.tones.features.shared.utils

import android.content.Context
import android.util.TypedValue
import android.view.View
import androidx.annotation.Dimension
import androidx.annotation.Px
import androidx.fragment.app.Fragment


@Px
fun Context.dp(@Dimension(unit = Dimension.DP) dp: Int): Float {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP, dp.toFloat(), resources.displayMetrics
    )
}

@Px
fun View.dp(@Dimension(unit = Dimension.DP) dp: Int): Float {
    return context.dp(dp)
}

@Px
fun Fragment.dp(@Dimension(unit = Dimension.DP) dp: Int): Float {
    return requireContext().dp(dp)
}

@Px
fun Context.dip(@Dimension(unit = Dimension.DP) dp: Int): Int {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP, dp.toFloat(), resources.displayMetrics
    ).toInt()
}

@Px
fun View.dip(@Dimension(unit = Dimension.DP) dp: Int): Int {
    return context.dip(dp)
}

@Px
fun Fragment.dip(@Dimension(unit = Dimension.DP) dp: Int): Int {
    return requireContext().dip(dp)
}