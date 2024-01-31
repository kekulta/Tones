package com.kekulta.tones.features.main.presentation.customviews

import android.app.ActionBar.LayoutParams
import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import android.widget.LinearLayout
import androidx.annotation.ColorInt
import androidx.annotation.Dimension
import androidx.annotation.Dimension.Companion.DP
import com.google.android.material.R
import com.google.android.material.button.MaterialButton
import com.kekulta.tones.features.main.domain.models.enums.AnswerState
import com.kekulta.tones.features.main.presentation.vo.AnswerVo
import com.kekulta.tones.features.shared.utils.animateBackgroundTint
import com.kekulta.tones.features.shared.utils.animateTextColor
import com.kekulta.tones.features.shared.utils.animateWidth
import com.kekulta.tones.features.shared.utils.dip
import com.kekulta.tones.features.shared.utils.getMaterialColor

class AnswerButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = R.style.Widget_Material3_Button_TonalButton
) : MaterialButton(context, attrs, defStyleAttr) {
    private var state = AnswerState.NORMAL

    init {
        width = dip(getSize(state))
        setTextColor(getTextColor(state))
        backgroundTintList = ColorStateList.valueOf(getTint(state))
    }

    fun bind(vo: AnswerVo, jumpToState: Boolean = false) {
        if (jumpToState) {
            jumpToState(vo.state)
        } else {
            animateState(vo.state)
        }
        text = vo.pinyin
    }

    private fun jumpToState(newState: AnswerState) {
        backgroundTintList = ColorStateList.valueOf(getTint(newState))
        setTextColor(getTextColor(newState))
        layoutParams = LinearLayout.LayoutParams(dip(getSize(newState)), LayoutParams.WRAP_CONTENT)

        state = newState
    }

    private fun animateState(newState: AnswerState) {
        animateBackgroundTint(getTint(state), getTint(newState))
        animateTextColor(getTextColor(state), getTextColor(newState))
        animateWidth(getSize(state), getSize(newState))

        state = newState
    }

    @ColorInt
    private fun getTint(state: AnswerState): Int {
        val color = when (state) {
            AnswerState.NORMAL -> R.attr.colorSecondaryContainer
            AnswerState.CHECKED -> R.attr.colorSecondary
            AnswerState.ANSWERED_CORRECT -> com.kekulta.tones.R.attr.colorCorrect
            AnswerState.ANSWERED_INCORRECT -> R.attr.colorError
            AnswerState.UNANSWERED_CORRECT -> com.kekulta.tones.R.attr.colorCorrectContainer
        }

        return getMaterialColor(color)
    }

    @ColorInt
    private fun getTextColor(state: AnswerState): Int {
        val color = when (state) {
            AnswerState.NORMAL -> R.attr.colorOnSecondaryContainer
            AnswerState.CHECKED -> R.attr.colorOnSecondary
            AnswerState.ANSWERED_CORRECT -> com.kekulta.tones.R.attr.colorOnCorrect
            AnswerState.ANSWERED_INCORRECT -> R.attr.colorOnError
            AnswerState.UNANSWERED_CORRECT -> com.kekulta.tones.R.attr.colorOnCorrectContainer
        }

        return getMaterialColor(color)
    }

    @Dimension(unit = DP)
    private fun getSize(state: AnswerState): Int {
        return when (state) {
            AnswerState.NORMAL -> 100
            AnswerState.CHECKED, AnswerState.ANSWERED_CORRECT, AnswerState.ANSWERED_INCORRECT, AnswerState.UNANSWERED_CORRECT -> 120
        }
    }
}