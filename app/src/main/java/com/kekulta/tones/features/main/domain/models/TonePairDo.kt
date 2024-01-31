package com.kekulta.tones.features.main.domain.models

import com.kekulta.tones.features.main.domain.models.enums.Tone

data class TonePairDo(
    val tones: Pair<Tone, Tone>,
    val audio: String,
    val hanzi: String,
    val pinyin: String,
    val english: String,
)