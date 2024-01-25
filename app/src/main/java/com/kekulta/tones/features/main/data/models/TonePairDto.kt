package com.kekulta.tones.features.main.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TonePairDto(
    @SerialName("pair") val tones: String,
    @SerialName("audio") val audio: String,
    @SerialName("hanzi") val hanzi: String,
    @SerialName("pinyin") val pinyin: String,
    @SerialName("english") val english: String,
)