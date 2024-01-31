package com.kekulta.tones.features.main.presentation.framework

import com.kekulta.tones.features.settings.SettingPoint

class SettingsNamesManager() {
    // TODO get internationalized names for settings
    fun getName(setting: SettingPoint): String {
        return when (setting) {
            SettingPoint.SHOULD_SHOW_CORRECT_ANSWERS -> "Should show correct answers"
        }
    }
}