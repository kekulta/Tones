package com.kekulta.tones.features.main.presentation.formatters

import android.text.SpannableString
import com.kekulta.tones.features.main.domain.models.MainState
import com.kekulta.tones.features.main.domain.models.QuestionDo
import com.kekulta.tones.features.main.presentation.vo.AnswerVo
import com.kekulta.tones.features.main.presentation.vo.AnswersControlsVo
import com.kekulta.tones.features.main.presentation.vo.AnswersToggleVo
import com.kekulta.tones.features.main.presentation.vo.AnswersVo
import com.kekulta.tones.features.main.presentation.vo.ButtonStateVo
import com.kekulta.tones.features.main.presentation.vo.ExplanationVo
import com.kekulta.tones.features.main.presentation.vo.QuestionVo
import com.kekulta.tones.features.settings.SettingsDo
import com.kekulta.tones.features.shared.utils.getTonedSyllable

class QuestionVoFormatter(
    private val answersToggleVoFormatter: AnswersToggleVoFormatter,
    private val nextButtonFormatter: NextButtonFormatter,
    private val checkButtonFormatter: CheckButtonFormatter,
) {
    fun format(qdo: QuestionDo, state: MainState, settingsDo: SettingsDo): QuestionVo {
        return when (qdo) {
            is QuestionDo.Empty -> emptyVo()
            is QuestionDo.MinPair -> {
                val (firstSyllables, secondSyllables) = extractSyllables(qdo)

                if (firstSyllables == null || secondSyllables == null || firstSyllables.size != 5 || secondSyllables.size != 5) {
                    return emptyVo()
                }

                val nextButton: ButtonStateVo = nextButtonFormatter.format(state)
                val checkButton: ButtonStateVo = checkButtonFormatter.format(state)

                val explanationVo = extractExplanation(qdo, state)

                val controls = assembleControls(
                    firstSyllables,
                    secondSyllables,
                    nextButton,
                    checkButton,
                    qdo,
                    state,
                    settingsDo,
                )

                return QuestionVo(
                    controls = controls,
                    audio = qdo.audio,
                    explanationVo = explanationVo,
                )
            }
        }
    }

    private fun assembleControls(
        firstSyllables: List<String>,
        secondSyllables: List<String>,
        nextButton: ButtonStateVo,
        checkButton: ButtonStateVo,
        qdo: QuestionDo.MinPair,
        state: MainState,
        settingsDo: SettingsDo
    ): AnswersControlsVo {
        return AnswersControlsVo(
            firstToggle = answersToggleVoFormatter.format(
                firstSyllables,
                qdo.tones.first,
                state.firstCheck,
                state.isAnswered,
                settingsDo.shouldHighlightCorrectAnswers,
            ),

            secondToggle = answersToggleVoFormatter.format(
                secondSyllables,
                qdo.tones.second,
                state.secondCheck,
                state.isAnswered,
                settingsDo.shouldHighlightCorrectAnswers,
            ),
            checkButton = checkButton,
            nextButton = nextButton
        )
    }

    private fun extractExplanation(qdo: QuestionDo.MinPair, state: MainState): ExplanationVo {
        return ExplanationVo(
            hanzi = SpannableString(qdo.hanzi),
            pinyin = qdo.pinyin,
            english = qdo.english,
            state.isAnswered
        )
    }

    private fun extractSyllables(qdo: QuestionDo.MinPair): Pair<List<String>?, List<String>?> {
        return Pair(
            qdo.pinyin.split(" ").getOrNull(0)?.let { getTonedSyllable(it) },
            qdo.pinyin.split(" ").getOrNull(1)?.let { getTonedSyllable(it) })
    }

    private fun emptyVo(): QuestionVo {
        val buttonStateVo = ButtonStateVo(isEnabled = false, isShown = false, isInteractive = false)
        val answerVo = AnswerVo("")
        val answersVo = AnswersVo(answerVo, answerVo, answerVo, answerVo, answerVo)
        val toggleVo = AnswersToggleVo(answersVo, buttonStateVo.copy(isShown = true))
        val answersControlsVo =
            AnswersControlsVo(toggleVo, toggleVo, buttonStateVo.copy(isShown = true), buttonStateVo)
        val explanationVo = ExplanationVo(SpannableString("中文"), "zhōng wén", "chinese", false)
        return QuestionVo(answersControlsVo, explanationVo, null)
    }
}

