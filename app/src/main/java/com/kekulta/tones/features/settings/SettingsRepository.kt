package com.kekulta.tones.features.settings

import com.kekulta.tones.features.main.domain.models.UiEvent
import com.kekulta.tones.features.shared.AbstractCoroutineRepository
import com.kekulta.tones.features.shared.utils.mapState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class SettingsRepository : AbstractCoroutineRepository() {
    private val shouldHighlightCorrectAnswers = MutableStateFlow(false)
    private val settings = collectSettings()

    fun observeSettings(): StateFlow<SettingsDo> = settings
    private fun setShouldHighlightCorrectAnswers(param: Boolean) {
        shouldHighlightCorrectAnswers.value = param
    }

    fun dispatch(event: UiEvent.ChangeSettings) {
        when (event.type) {
            SettingPoint.SHOULD_SHOW_CORRECT_ANSWERS -> setShouldHighlightCorrectAnswers(event.isEnabled)
        }
    }

    private fun collectSettings(): StateFlow<SettingsDo> {
        return shouldHighlightCorrectAnswers.mapState { SettingsDo(shouldHighlightCorrectAnswers = it) }
    }

}