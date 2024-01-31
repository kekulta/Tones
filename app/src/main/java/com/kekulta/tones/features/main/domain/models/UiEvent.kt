package com.kekulta.tones.features.main.domain.models

import com.kekulta.tones.features.main.domain.models.enums.SyllableNum
import com.kekulta.tones.features.main.domain.models.enums.Tone
import com.kekulta.tones.features.settings.SettingPoint

sealed class UiEvent() {
    data object CheckAnswer : UiEvent()
    data object NextQuestion : UiEvent()
    data class ChangeSettings(val type: SettingPoint, val isEnabled: Boolean) : UiEvent()
    data class SelectSyllable(val syllableNum: SyllableNum, val tone: Tone) : UiEvent()
}