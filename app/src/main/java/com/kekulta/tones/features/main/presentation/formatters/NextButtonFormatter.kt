package com.kekulta.tones.features.main.presentation.formatters

import com.kekulta.tones.features.main.domain.models.MainState
import com.kekulta.tones.features.main.presentation.vo.ButtonStateVo

class NextButtonFormatter {
    fun format(state: MainState): ButtonStateVo {
        return state.let { (isAnswered) ->
            if (isAnswered) {
                ButtonStateVo(isEnabled = true, isShown = true)
            } else {
                ButtonStateVo(
                    isEnabled = false,
                    isShown = false,
                )
            }
        }
    }
}