package com.kekulta.tones.features.main.domain.viewmodels

import com.kekulta.tones.features.main.domain.QuestionsRepository
import com.kekulta.tones.features.main.presentation.formatters.QuestionVoFormatter
import com.kekulta.tones.features.main.presentation.vo.QuestionVo
import com.kekulta.tones.features.shared.AbstractCoroutineViewModel
import com.kekulta.tones.features.shared.mapState
import kotlinx.coroutines.flow.StateFlow
import timber.log.Timber

class MainViewModel(
    private val repository: QuestionsRepository,
    private val formatter: QuestionVoFormatter,
) : AbstractCoroutineViewModel() {
    val currQuestion: StateFlow<QuestionVo> =
        repository.currQuestion.mapState {
            Timber.w("Mapped: ${formatter.format(it)}")
            formatter.format(it)
        }

    fun nextQuestion() {
        repository.nextQuestion()
    }
}