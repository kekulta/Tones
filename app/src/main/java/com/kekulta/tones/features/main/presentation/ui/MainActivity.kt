package com.kekulta.tones.features.main.presentation.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.kekulta.tones.R
import com.kekulta.tones.databinding.ActivityMainBinding
import com.kekulta.tones.features.main.domain.viewmodels.MainViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModel()
    private val binding by viewBinding(ActivityMainBinding::bind)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding.testCard.onNextCallback = {
            viewModel.nextQuestion()
        }

        lifecycleScope.launch {
            viewModel.currQuestion.collect { vo ->
                Timber.w("Collect! $vo")
                binding.testCard.bind(vo)
            }
        }
    }
}

