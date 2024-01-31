package com.kekulta.tones.features.main.presentation.customviews


import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.appcompat.widget.SwitchCompat
import com.kekulta.tones.R
import com.kekulta.tones.databinding.SettingOnoffLayoutBinding
import com.kekulta.tones.features.main.domain.models.UiEventCallback
import com.kekulta.tones.features.main.domain.models.UiEvent
import com.kekulta.tones.features.main.presentation.vo.SettingVo
import com.kekulta.tones.features.settings.SettingPoint
import com.kekulta.tones.features.shared.utils.getDrawable
import com.kekulta.tones.features.shared.utils.getMaterialColorStateList
import com.google.android.material.R as Rm


class SettingOnOff @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0, defStyleRes: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {
    private val binding: SettingOnoffLayoutBinding =
        SettingOnoffLayoutBinding.inflate(LayoutInflater.from(context), this)
    private var uiEventCallback: UiEventCallback? = null
    private var type: SettingPoint? = null

    init {
        background = getDrawable(R.drawable.player_background)
        backgroundTintList =
            getMaterialColorStateList(Rm.attr.colorSurfaceContainerHigh)
    }

    fun bind(vo: SettingVo, jumpToState: Boolean = false) {
        binding.settingSwitch.setOnClickListener {
            dispatch(binding.settingSwitch.isChecked)
        }

        binding.settingName.text = vo.name
        type = vo.type

        binding.settingSwitch.checkIfNot(vo.isEnabled)
        if (jumpToState) {
            binding.settingSwitch.jumpDrawablesToCurrentState()
        }
    }


    fun setUiEventCallback(callback: UiEventCallback?) {
        uiEventCallback = callback
    }

    private fun dispatch(isEnabled: Boolean) {
        uiEventCallback?.let { uiEventCallback ->
            type?.let { type ->
                uiEventCallback(UiEvent.ChangeSettings(type, isEnabled))
            }
        }
    }

    private fun SwitchCompat.checkIfNot(checked: Boolean) {
        if (isChecked == checked) return

        isChecked = checked
        jumpDrawablesToCurrentState()
    }

}

