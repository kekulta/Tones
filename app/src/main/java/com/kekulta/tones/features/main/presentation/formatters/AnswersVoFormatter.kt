package com.kekulta.tones.features.main.presentation.formatters

import com.kekulta.tones.features.main.domain.models.enums.Tone
import com.kekulta.tones.features.main.presentation.vo.AnswerVo
import com.kekulta.tones.features.main.presentation.vo.AnswersVo

class AnswersVoFormatter(private val answerVoFormatter: AnswerVoFormatter) {
    fun format(
        syllables: List<String>,
        corrTone: Tone,
        checkedTone: Tone?,
        isAnswered: Boolean,
        shouldHighlightCorrectAnswers: Boolean,
    ): AnswersVo {
        fun answerVo(num: Int): AnswerVo {
            return answerVoFormatter.format(
                syllables[num],
                Tone.entries[num],
                checkedTone,
                corrTone,
                isAnswered,
                shouldHighlightCorrectAnswers,
            )
        }

        return AnswersVo(
            even = answerVo(0),
            rising = answerVo(1),
            fallRise = answerVo(2),
            falling = answerVo(3),
            neutral = answerVo(4),
        )
    }
}