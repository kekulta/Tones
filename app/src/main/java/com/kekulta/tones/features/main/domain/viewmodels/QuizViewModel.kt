package com.kekulta.tones.features.main.domain.viewmodels

import com.kekulta.tones.features.main.domain.QuestionsRepository
import com.kekulta.tones.features.main.domain.models.MainState
import com.kekulta.tones.features.main.domain.models.enums.SyllableNum
import com.kekulta.tones.features.main.domain.models.enums.Tone
import com.kekulta.tones.features.main.presentation.formatters.QuestionVoFormatter
import com.kekulta.tones.features.main.domain.models.UiEvent
import com.kekulta.tones.features.settings.SettingsRepository
import com.kekulta.tones.features.shared.AbstractCoroutineViewModel
import com.kekulta.tones.features.shared.utils.combineStates
import kotlinx.coroutines.flow.MutableStateFlow

class QuizViewModel(
    private val questionsRepository: QuestionsRepository,
    private val settingsRepository: SettingsRepository,
    private val formatter: QuestionVoFormatter,
) : AbstractCoroutineViewModel() {
    private val currQuestion = questionsRepository.currQuestion
    private val state = MutableStateFlow(MainState())
    private val settings = settingsRepository.observeSettings()

    val uiState =
        combineStates(currQuestion, state, settings) { qdo, state, settings ->
            formatter.format(
                qdo,
                state,
                settings
            )
        }

    fun dispatch(event: UiEvent) {
        when (event) {
            UiEvent.CheckAnswer -> onCheck()
            UiEvent.NextQuestion -> onNext()
            is UiEvent.SelectSyllable -> {
                when (event.syllableNum) {
                    SyllableNum.FIRST -> onFirstTonePick(event.tone)
                    SyllableNum.SECOND -> onSecondTonePick(event.tone)
                }
            }

            is UiEvent.ChangeSettings -> settingsRepository.dispatch(event)
        }
    }

    private fun onCheck() {
        if (state.value.isAnswered) {
            return
        }

        state.value = state.value.copy(isAnswered = true)
    }

    private fun onNext() {
        if (!state.value.isAnswered) {
            return
        }

        state.value = state.value.copy(isAnswered = false, firstCheck = null, secondCheck = null)
        questionsRepository.nextQuestion()
    }

    private fun onFirstTonePick(tone: Tone) {
        state.value = state.value.copy(firstCheck = tone)
    }

    private fun onSecondTonePick(tone: Tone) {
        state.value = state.value.copy(secondCheck = tone)
    }
}

