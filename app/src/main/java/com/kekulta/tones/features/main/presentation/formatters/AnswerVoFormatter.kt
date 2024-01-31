package com.kekulta.tones.features.main.presentation.formatters

import com.kekulta.tones.features.main.domain.models.enums.AnswerState
import com.kekulta.tones.features.main.domain.models.enums.Tone
import com.kekulta.tones.features.main.presentation.vo.AnswerVo

class AnswerVoFormatter() {
    fun format(
        syllable: String,
        tone: Tone,
        checkedTone: Tone?,
        corrTone: Tone,
        isAnswered: Boolean,
        shouldHighlightCorrectAnswers: Boolean,
    ): AnswerVo {
        val state = if (isAnswered) {
            when {
                corrTone == checkedTone && tone == corrTone -> AnswerState.ANSWERED_CORRECT
                tone == checkedTone -> AnswerState.ANSWERED_INCORRECT
                tone == corrTone -> if (shouldHighlightCorrectAnswers) AnswerState.UNANSWERED_CORRECT else AnswerState.NORMAL
                else -> AnswerState.NORMAL
            }
        } else {
            if (tone == checkedTone) {
                AnswerState.CHECKED
            } else {
                AnswerState.NORMAL
            }
        }

        return AnswerVo(syllable, state)
    }
}