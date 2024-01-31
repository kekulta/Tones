package com.kekulta.tones.features.main.domain.viewmodels

import androidx.lifecycle.ViewModel
import com.kekulta.tones.features.main.domain.models.UiEvent
import com.kekulta.tones.features.main.presentation.formatters.SettingsFormatter
import com.kekulta.tones.features.settings.SettingsRepository
import com.kekulta.tones.features.shared.utils.mapState
import timber.log.Timber

class SettingsViewModel(
    private val settingsRepository: SettingsRepository,
    private val settingsFormatter: SettingsFormatter,
) : ViewModel() {

    val uiState = settingsRepository.observeSettings().mapState { settingsFormatter.format(it) }

    fun dispatch(event: UiEvent) {
        Timber.d("New event: $event")
        when (event) {
            is UiEvent.ChangeSettings -> settingsRepository.dispatch(event)
            else -> Timber.e("Unhandled event: $event")
        }
    }
}