package com.kekulta.tones.features.main.presentation.formatters

import com.kekulta.tones.features.main.presentation.vo.SettingVo
import com.kekulta.tones.features.main.presentation.vo.SettingsVo
import com.kekulta.tones.features.main.presentation.framework.SettingsNamesManager
import com.kekulta.tones.features.settings.SettingPoint
import com.kekulta.tones.features.settings.SettingsDo

class SettingsFormatter(private val settingsNames: SettingsNamesManager) {
    fun format(sdo: SettingsDo): SettingsVo {
        return SettingsVo(
            SettingVo(
                SettingPoint.SHOULD_SHOW_CORRECT_ANSWERS,
                settingsNames.getName(SettingPoint.SHOULD_SHOW_CORRECT_ANSWERS),
                sdo.shouldHighlightCorrectAnswers,
            )
        )
    }


}