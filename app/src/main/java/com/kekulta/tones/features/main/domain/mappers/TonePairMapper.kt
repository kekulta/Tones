package com.kekulta.tones.features.main.domain.mappers

import com.kekulta.tones.features.main.data.models.TonePairDto
import com.kekulta.tones.features.main.domain.models.Tone
import com.kekulta.tones.features.main.domain.models.TonePairDo
import timber.log.Timber

class TonePairMapper {
    fun map(dtos: List<TonePairDto>): List<TonePairDo> {
        return dtos.mapNotNull { dto ->
            val tones = parseTones(dto.tones)

            if (tones == null) {
                null
            } else {
                TonePairDo(
                    tones = tones,
                    audio = dto.audio,
                    hanzi = dto.hanzi,
                    english = dto.english,
                    pinyin = dto.pinyin,
                )
            }
        }
    }

    private fun parseTones(tones: String): Pair<Tone, Tone>? {
        val firstTone = tones.getOrNull(0)?.let { parseTone(it) }
            ?: return null.logW("Invalid tones pair: $tones")

        val secondTone = tones.getOrNull(1)?.let { parseTone(it) }
            ?: return null.logW("Invalid tones pair: $tones")

        return firstTone to secondTone
    }

    private fun parseTone(toneChar: Char): Tone? {
        val toneNum = toneChar.digitToIntOrNull() ?: return null.logW("Unknown tone: $toneChar")
        return Tone.entries.getOrNull(toneNum - 1) ?: null.logW("Unknown tone number $toneNum")
    }

    private fun <T> T.logW(mes: String): T {
        Timber.w(mes)
        return this
    }
}