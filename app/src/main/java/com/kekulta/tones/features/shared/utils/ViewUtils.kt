package com.kekulta.tones.features.shared.utils

import android.annotation.SuppressLint
import android.view.View
import androidx.annotation.Dimension
import androidx.core.view.allViews
import androidx.core.view.setMargins
import androidx.recyclerview.widget.RecyclerView

fun View.hide() {
    visibility = View.INVISIBLE
}

fun View.show() {
    visibility = View.VISIBLE
}

@SuppressLint("ClickableViewAccessibility")
fun View.disableInteractions() {
    setOnTouchListener { _, _ -> true }
    allViews.forEach { it.setOnTouchListener { _, _ -> true } }
}

fun View.enableInteractions() {
    setOnTouchListener(null)
    allViews.forEach { it.setOnTouchListener(null) }
}

fun View.setPadding(@Dimension(unit = Dimension.DP) padding: Int) {
    setPadding(dip(padding), dip(padding), dip(padding), dip(padding))
}
