package com.kekulta.tones.features.main.presentation.customviews


import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import com.kekulta.tones.R
import com.kekulta.tones.databinding.TestLayoutBinding
import com.kekulta.tones.features.main.domain.models.UiEventCallback
import com.kekulta.tones.features.main.domain.models.enums.SyllableNum
import com.kekulta.tones.features.main.domain.models.UiEvent
import com.kekulta.tones.features.main.presentation.vo.ButtonStateVo
import com.kekulta.tones.features.main.presentation.vo.ExplanationVo
import com.kekulta.tones.features.main.presentation.vo.QuestionVo
import com.kekulta.tones.features.shared.utils.animateTextColor
import com.kekulta.tones.features.shared.utils.disableInteractions
import com.kekulta.tones.features.shared.utils.enableInteractions
import com.kekulta.tones.features.shared.utils.getDrawable
import com.kekulta.tones.features.shared.utils.getMaterialColor
import com.kekulta.tones.features.shared.utils.getMaterialColorStateList
import com.kekulta.tones.features.shared.utils.hide
import com.kekulta.tones.features.shared.utils.setPadding
import com.kekulta.tones.features.shared.utils.show
import com.google.android.material.R as Rm


class QuizCard @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {
    private var uiEventCallback: UiEventCallback? = null

    private val binding: TestLayoutBinding =
        TestLayoutBinding.inflate(LayoutInflater.from(context), this)

    init {
        setPadding(10)
        orientation = VERTICAL

        binding.root.background = getDrawable(R.drawable.player_background)
        binding.root.backgroundTintList =
            getMaterialColorStateList(Rm.attr.colorSurfaceContainerLowest)

        binding.firstSyllableToggle.setUiCallback(SyllableNum.FIRST, ::dispatch)
        binding.secondSyllableToggle.setUiCallback(SyllableNum.SECOND, ::dispatch)

        binding.checkButton.setOnClickListener { dispatch(UiEvent.CheckAnswer) }
        binding.nextButton.setOnClickListener { dispatch(UiEvent.NextQuestion) }
    }

    fun bind(questionVo: QuestionVo, jumpToState: Boolean) {
        binding.apply {
            bindExplanation(questionVo.explanationVo, jumpToState)
            audioPlayer.bind(questionVo.audio)

            nextButton.bind(questionVo.controls.nextButton)
            checkButton.bind(questionVo.controls.checkButton)

            firstSyllableToggle.bind(
                questionVo.controls.firstToggle,
                jumpToState
            )
            secondSyllableToggle.bind(
                questionVo.controls.secondToggle,
                jumpToState
            )
        }
    }

    fun setUiCallback(callback: UiEventCallback?) {
        uiEventCallback = callback
    }

    private fun dispatch(event: UiEvent) {
        uiEventCallback?.invoke(event)
    }

    private fun Button.bind(state: ButtonStateVo) {
        isEnabled = state.isEnabled

        if (state.isShown) {
            show()
        } else {
            hide()
        }

        if (state.isInteractive) {
            enableInteractions()
        } else {
            disableInteractions()
        }
    }

    private fun bindExplanation(explanationVo: ExplanationVo, jumpToState: Boolean) {
        binding.apply {
            hanziTv.text = explanationVo.hanzi
            pinyinTv.text = explanationVo.pinyin
            englishTv.text = explanationVo.english
        }

        if (explanationVo.revealed) {
            if (jumpToState) {
                revealAnswersNow()
            } else {
                revealAnswers()
            }
        } else {
            hideAnswersNow()
        }
    }

    private fun revealAnswers() {
        binding.apply {
            hanziTv.revealText()
            pinyinTv.revealText()
            englishTv.revealText()
        }
    }

    private fun revealAnswersNow() {
        binding.apply {
            hanziTv.setTextColor(getMaterialColor(Rm.attr.colorOnSurface))
            pinyinTv.setTextColor(getMaterialColor(Rm.attr.colorOnSurface))
            englishTv.setTextColor(getMaterialColor(Rm.attr.colorOnSurface))
        }
    }

    private fun hideAnswersNow() {
        binding.apply {
            hanziTv.setTextColor(getMaterialColor(Rm.attr.colorSurfaceContainerLow))
            pinyinTv.setTextColor(getMaterialColor(Rm.attr.colorSurfaceContainerLow))
            englishTv.setTextColor(getMaterialColor(Rm.attr.colorSurfaceContainerLow))
        }
    }

    private fun TextView.revealText() {
        if (currentTextColor == getMaterialColor(Rm.attr.colorOnSurfaceVariant)) return

        animateTextColor(
            getMaterialColor(Rm.attr.colorSurfaceContainerLow),
            getMaterialColor(Rm.attr.colorOnSurfaceVariant)
        )
    }
}