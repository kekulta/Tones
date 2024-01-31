package com.kekulta.tones.features.main.presentation.vo

import com.kekulta.tones.features.settings.SettingPoint

data class SettingVo(
    val type: SettingPoint,
    val name: String,
    val isEnabled: Boolean,
)