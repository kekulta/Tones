package com.kekulta.tones.features.settings

import com.kekulta.tones.features.shared.AbstractCoroutineRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class SettingsRepository : AbstractCoroutineRepository() {
    private val shouldHighlightCorrectAnswers = MutableStateFlow(false)

    fun observeShouldHighlightCorrectAnswers(): StateFlow<Boolean> = shouldHighlightCorrectAnswers
    fun setShouldHighlightCorrectAnswers(param: Boolean) {
        shouldHighlightCorrectAnswers.value = param
    }
}