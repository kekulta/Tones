package com.kekulta.tones.features.main.domain

import com.kekulta.tones.features.main.data.api.NetworkDataStore
import com.kekulta.tones.features.main.domain.mappers.QuestionMapper
import com.kekulta.tones.features.main.domain.mappers.TonePairMapper
import com.kekulta.tones.features.main.domain.models.QuestionDo
import com.kekulta.tones.features.main.domain.models.TonePairDo
import com.kekulta.tones.features.shared.AbstractCoroutineRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.withContext
import timber.log.Timber
import kotlin.math.abs
import kotlin.random.Random

class QuestionsRepository(
    private val dataStore: NetworkDataStore,
    private val tonePairMapper: TonePairMapper,
    private val questionsMapper: QuestionMapper,
) : AbstractCoroutineRepository() {
    private val questions = mutableListOf<TonePairDo>()
    private val _currQuestion = MutableStateFlow<QuestionDo>(QuestionDo.Empty)
    val currQuestion: StateFlow<QuestionDo> get() = _currQuestion

    init {
        loadQuestions()
    }

    fun nextQuestion() {
        if (questions.isEmpty()) {
            _currQuestion.value = QuestionDo.Empty
            loadQuestions()
        } else {
            if (questions.size <= 5) {
                loadQuestions()
            }
            _currQuestion.value =
                questionsMapper.map(questions.popRandom().also { Timber.w("Set! $it") })
        }
        Timber.w("Next question! Questions: ${questions.size}")
    }

    private fun loadQuestions() {
        launchScope(LOAD_KEY) {
            loadQuestionsSync()
        }
    }

    private suspend fun loadQuestionsSync() {
        withContext(Dispatchers.IO) {
            questions.addAll(tonePairMapper.map(dataStore.getTones()))

            _currQuestion.compareAndSet(
                QuestionDo.Empty,
                questionsMapper.map(questions.popRandom().also { Timber.w("Set! $it") })
            )
        }
    }

    private fun <T> MutableList<T>.popRandom(): T {
        val pickIndex = abs(Random.nextInt()) % size
        return removeAt(pickIndex)
    }

    companion object {
        private const val LOAD_KEY = "load_key"
    }
}