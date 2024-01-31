package com.kekulta.tones.features.main.domain.models

import android.net.Uri
import com.kekulta.tones.features.main.domain.models.enums.Tone

sealed class QuestionDo {
    data class MinPair(
        val tones: Pair<Tone, Tone>,
        val audio: Uri,
        val hanzi: String,
        val pinyin: String,
        val english: String,
    ) : QuestionDo()

    data object Empty : QuestionDo()
}