package com.kekulta.tones.features.main.presentation.vo

import com.kekulta.tones.features.main.domain.models.enums.AnswerState

data class AnswerVo(
    val pinyin: String, val state: AnswerState = AnswerState.NORMAL,
)