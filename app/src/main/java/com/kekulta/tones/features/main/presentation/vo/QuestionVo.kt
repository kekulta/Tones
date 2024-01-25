package com.kekulta.tones.features.main.presentation.vo

import android.net.Uri
import android.text.Spannable
import com.kekulta.tones.features.main.domain.models.Tone

sealed class QuestionVo {
    data class MinPair(
        val tones: Pair<Int, Int>,
        val firstTonedSyllable: List<String>,
        val secondTonedSyllable: List<String>,
        val audio: Uri,
        val hanzi: Spannable,
        val pinyin: String,
        val english: String,
    ) : QuestionVo()

    data object Empty : QuestionVo()
}