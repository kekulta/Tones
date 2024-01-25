package com.kekulta.tones.features.main.presentation.formatters

import android.text.SpannableString
import com.kekulta.tones.features.main.domain.models.QuestionDo
import com.kekulta.tones.features.main.presentation.vo.QuestionVo
import com.kekulta.tones.features.shared.getTonedSyllable
import timber.log.Timber

class QuestionVoFormatter {
    fun format(qdo: QuestionDo): QuestionVo {
        return when (qdo) {
            is QuestionDo.Empty -> QuestionVo.Empty
            is QuestionDo.MinPair -> {
                val firstTones = qdo.pinyin.split(" ").getOrNull(0)?.let { getTonedSyllable(it) }
                val secondTones = qdo.pinyin.split(" ").getOrNull(1)?.let { getTonedSyllable(it) }

                Timber.w("first: $firstTones, second: $secondTones")

                if (firstTones == null || secondTones == null) {
                    QuestionVo.Empty
                } else {
                    QuestionVo.MinPair(
                        tones = qdo.tones.first.ordinal to qdo.tones.second.ordinal,
                        firstTonedSyllable = firstTones,
                        secondTonedSyllable = secondTones,
                        audio = qdo.audio,
                        hanzi = SpannableString(qdo.hanzi),
                        english = qdo.english,
                        pinyin = qdo.pinyin,
                    )
                }
            }
        }
    }
}