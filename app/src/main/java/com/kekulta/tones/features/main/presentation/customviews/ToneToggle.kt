package com.kekulta.tones.features.main.presentation.customviews


import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import com.google.android.material.button.MaterialButtonToggleGroup
import com.kekulta.tones.R
import com.kekulta.tones.databinding.ToneToggleLayoutBinding
import com.kekulta.tones.features.main.domain.models.UiEventCallback
import com.kekulta.tones.features.main.domain.models.enums.SyllableNum
import com.kekulta.tones.features.main.domain.models.enums.Tone
import com.kekulta.tones.features.main.domain.models.UiEvent
import com.kekulta.tones.features.main.presentation.vo.AnswersToggleVo
import com.kekulta.tones.features.shared.utils.disableInteractions
import com.kekulta.tones.features.shared.utils.enableInteractions
import com.kekulta.tones.features.shared.utils.hide
import com.kekulta.tones.features.shared.utils.show


class ToneToggle @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0, defStyleRes: Int = 0
) : MaterialButtonToggleGroup(context, attrs, defStyleAttr) {
    private val binding: ToneToggleLayoutBinding =
        ToneToggleLayoutBinding.inflate(LayoutInflater.from(context), this)
    private var isInteractive: Boolean = true
        set(value) {
            if (value != field) {
                if (value) {
                    enableInteractions()
                } else {
                    disableInteractions()
                }
            }
            field = value
        }

    private var syllableNum: SyllableNum? = null
    private var uiEventCallback: UiEventCallback? = null

    init {
        orientation = VERTICAL
        addOnButtonCheckedListener(::onButtonChecked)
    }


    fun bind(answersToggleVo: AnswersToggleVo, jumpToState: Boolean = false) {
        with(binding) {
            with(answersToggleVo.answers) {
                toneEven.bind(even, jumpToState)
                toneRising.bind(rising, jumpToState)
                toneFallRise.bind(fallRise, jumpToState)
                toneFalling.bind(falling, jumpToState)
                toneNeutral.bind(neutral, jumpToState)
            }
        }

        with(answersToggleVo) {
            isEnabled = state.isEnabled
            isInteractive = state.isInteractive
            if (state.isShown) {
                show()
            } else {
                hide()
            }
        }
    }

    fun setUiCallback(syllableNum: SyllableNum, callback: UiEventCallback?) {
        uiEventCallback = callback
        this.syllableNum = syllableNum
    }

    private fun dispatchSelect(tone: Tone) {
        uiEventCallback?.let { uiEventCallback ->
            syllableNum?.let { syllable ->
                uiEventCallback(UiEvent.SelectSyllable(syllable, tone))
            }
        }
    }

    private fun onButtonChecked(
        group: MaterialButtonToggleGroup, checkedId: Int, isChecked: Boolean
    ) {
        val tone = when (checkedId) {
            R.id.toneEven -> Tone.EVEN
            R.id.toneRising -> Tone.RISING
            R.id.toneFallRise -> Tone.FALL_RISE
            R.id.toneFalling -> Tone.FALLING
            R.id.toneNeutral -> Tone.NEUTRAL
            else -> null
        } ?: return

        dispatchSelect(tone)
    }
}