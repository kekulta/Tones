package com.kekulta.tones.features.main.domain.models

data class TonePairDo(
    val tones: Pair<Tone, Tone>,
    val audio: String,
    val hanzi: String,
    val pinyin: String,
    val english: String,
)