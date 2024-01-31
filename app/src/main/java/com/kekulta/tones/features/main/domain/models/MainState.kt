package com.kekulta.tones.features.main.domain.models

import com.kekulta.tones.features.main.domain.models.enums.Tone

data class MainState(
    val isAnswered: Boolean = false,
    val firstCheck: Tone? = null,
    val secondCheck: Tone? = null,
)