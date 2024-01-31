package com.kekulta.tones.features.main.presentation.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.kekulta.tones.R
import com.kekulta.tones.databinding.FragmentQuizBinding
import com.kekulta.tones.features.main.domain.viewmodels.QuizViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class QuizFragment : Fragment(R.layout.fragment_quiz) {

    private val binding by viewBinding(FragmentQuizBinding::bind)
    private val viewModel: QuizViewModel by viewModel(ownerProducer = { requireActivity() })
    private var isInit = true

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.backButton.setOnClickListener { findNavController().popBackStack() }

        binding.quizCard.setUiCallback(viewModel::dispatch)
        lifecycleScope.launch {
            viewModel.uiState.collect { vo ->
                binding.quizCard.bind(vo, isInit || savedInstanceState != null)

                isInit = false
            }
        }
    }
}