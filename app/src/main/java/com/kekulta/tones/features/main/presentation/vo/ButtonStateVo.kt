package com.kekulta.tones.features.main.presentation.vo

data class ButtonStateVo(
    val isEnabled: Boolean,
    val isShown: Boolean,
    val isInteractive: Boolean = true,
)