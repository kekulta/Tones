package com.kekulta.tones.features.main.presentation.vo

import android.net.Uri

data class QuestionVo(
    val controls: AnswersControlsVo,
    val explanationVo: ExplanationVo,
    val audio: Uri?,
)

