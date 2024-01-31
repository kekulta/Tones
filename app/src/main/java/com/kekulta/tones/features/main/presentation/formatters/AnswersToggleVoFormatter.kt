package com.kekulta.tones.features.main.presentation.formatters

import com.kekulta.tones.features.main.domain.models.enums.Tone
import com.kekulta.tones.features.main.presentation.vo.AnswersToggleVo
import com.kekulta.tones.features.main.presentation.vo.ButtonStateVo

class AnswersToggleVoFormatter(
    private val answersVoFormatter: AnswersVoFormatter,
) {

    fun format(
        syllables: List<String>,
        corrTone: Tone,
        checkedTone: Tone?,
        isAnswered: Boolean,
        shouldHighlightCorrectAnswers: Boolean,
    ): AnswersToggleVo {
        val answers = answersVoFormatter.format(syllables, corrTone, checkedTone, isAnswered, shouldHighlightCorrectAnswers)
        val state = if (isAnswered) {
            ButtonStateVo(isEnabled = true, isShown = true, isInteractive = false)
        } else {
            ButtonStateVo(isEnabled = true, isShown = true, isInteractive = true)
        }

        return AnswersToggleVo(answers, state)
    }
}