package com.kekulta.tones.features.main.presentation.vo

import android.text.Spannable

data class ExplanationVo(
    val hanzi: Spannable,
    val pinyin: String,
    val english: String,
    val revealed: Boolean,
)