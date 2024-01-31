package com.kekulta.tones.features.main.presentation.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.kekulta.tones.R
import com.kekulta.tones.databinding.FragmentSettingsBinding
import com.kekulta.tones.features.main.domain.viewmodels.SettingsViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class SettingsFragment : Fragment(R.layout.fragment_settings) {

    private val binding by viewBinding(FragmentSettingsBinding::bind)
    private val viewModel: SettingsViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.backButton.setOnClickListener { findNavController().popBackStack() }

        binding.shouldShowCorrectAnswersSetting.setUiEventCallback(viewModel::dispatch)

        lifecycleScope.launch {
            viewModel.uiState.collect { vo ->
                binding.shouldShowCorrectAnswersSetting.bind(vo.shouldShowCorrectAnswers)
            }
        }
    }
}

