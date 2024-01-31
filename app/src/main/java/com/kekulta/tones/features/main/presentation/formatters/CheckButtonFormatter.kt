package com.kekulta.tones.features.main.presentation.formatters

import com.kekulta.tones.features.main.domain.models.MainState
import com.kekulta.tones.features.main.presentation.vo.ButtonStateVo

class CheckButtonFormatter {
    fun format(state: MainState): ButtonStateVo {
        return state.let { (isAnswered, firstChecked, secondChecked) ->
            if (isAnswered) {
                ButtonStateVo(
                    isEnabled = false,
                    isShown = false,
                )
            } else {
                if (firstChecked != null && secondChecked != null) {
                    ButtonStateVo(isEnabled = true, isShown = true)
                } else {
                    ButtonStateVo(isEnabled = false, isShown = true)
                }
            }
        }
    }
}