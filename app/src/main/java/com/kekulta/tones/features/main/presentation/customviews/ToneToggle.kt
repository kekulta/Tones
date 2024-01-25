package com.kekulta.tones.features.main.presentation.customviews


import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.content.Context
import android.content.res.ColorStateList
import android.media.MediaPlayer
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.animation.DecelerateInterpolator
import android.widget.Button
import androidx.annotation.ColorInt
import androidx.core.view.get
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButtonToggleGroup
import com.kekulta.tones.R
import com.kekulta.tones.databinding.ToneToggleLayoutBinding
import com.kekulta.tones.features.shared.animateWidth
import com.kekulta.tones.features.shared.disableInteractions
import com.kekulta.tones.features.shared.dp
import com.kekulta.tones.features.shared.enableInteractions
import com.kekulta.tones.features.shared.getMaterialColor
import com.kekulta.tones.features.shared.getMaterialColorStateList
import com.google.android.material.R as Rm


class ToneToggle @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0, defStyleRes: Int = 0
) : MaterialButtonToggleGroup(context, attrs, defStyleAttr) {
    private val binding: ToneToggleLayoutBinding =
        ToneToggleLayoutBinding.inflate(LayoutInflater.from(context), this)
    private var correct: Int? = null


    init {
        layoutParams = LayoutParams(
            RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.MATCH_PARENT
        )

        orientation = VERTICAL
        isSelectionRequired = true
        isSingleSelection = true
        isEnabled = false

        addOnButtonCheckedListener(::onButtonChecked)
    }

    fun bind(tones: List<String>, correct: Int) {
        this.correct = correct

        binding.toneEven.text = tones[0]
        binding.toneRising.text = tones[1]
        binding.toneFallRise.text = tones[2]
        binding.toneFalling.text = tones[3]
        binding.toneNeutral.text = tones[4]

        isEnabled = true
        isSingleSelection = true

        uncheckAll()
        enableInteractions()
    }

    fun check() {
        disableInteractions()

        repeat(5) { i ->

            when {
                get(i).id in checkedButtonIds && i == correct -> {
                    get(i).animateBackgroundTint(
                        getMaterialColor(Rm.attr.colorSecondaryContainer),
                        getMaterialColor(R.attr.colorCorrect)
                    )
                    getButton(i).setTextColor(getMaterialColor(R.attr.colorOnCorrect))
                }

//                get(i).id !in checkedButtonIds && i == correct -> {
//                    get(i).animateBackgroundTint(
//                        getMaterialColor(Rm.attr.colorSecondary),
//                        getMaterialColor(R.attr.colorCorrectContainer)
//                    )
//                    get(i).animateWidth(dp(100), dp(120))
//                    getButton(i).setTextColor(getMaterialColor(R.attr.colorOnCorrectContainer))
//                }

                get(i).id in checkedButtonIds && i != correct -> {
                    get(i).animateBackgroundTint(
                        getMaterialColor(Rm.attr.colorSecondaryContainer),
                        getMaterialColor(Rm.attr.colorError)
                    )
                    getButton(i).setTextColor(getMaterialColor(Rm.attr.colorOnError))
                }
            }
        }
    }

//    private fun clearHighlighting() {
//        repeat(5) { i ->
//            getButton(i).apply {
//                if (backgroundTintList?.defaultColor == getMaterialColor(R.attr.colorCorrectContainer)) {
//                    animateBackgroundTint(
//                        getMaterialColor(R.attr.colorCorrectContainer),
//                        getMaterialColor(Rm.attr.colorSecondaryContainer)
//                    )
//                    setTextColor(getMaterialColorStateList(Rm.attr.colorOnSecondaryContainer))
//                    animateWidth(dp(120), dp(100))
//                }
//            }
//        }
//    }

    private fun onButtonChecked(
        group: MaterialButtonToggleGroup, checkedId: Int, isChecked: Boolean
    ) {

        group.findViewById<Button>(checkedId).apply {
            if (isChecked) {
                animateBackgroundTint(
                    backgroundTintList?.defaultColor
                        ?: getMaterialColor(Rm.attr.colorSecondaryContainer),
                    getMaterialColor(Rm.attr.colorSecondary)
                )
                setTextColor(getMaterialColorStateList(Rm.attr.colorOnSecondary))
                animateWidth(dp(100), dp(120))
            } else {
                animateBackgroundTint(
                    backgroundTintList?.defaultColor
                        ?: getMaterialColor(Rm.attr.colorSecondary),
                    getMaterialColor(Rm.attr.colorSecondaryContainer)
                )
                setTextColor(getMaterialColorStateList(Rm.attr.colorOnSecondaryContainer))
                animateWidth(dp(120), dp(100))
            }
        }
    }

    private fun MaterialButtonToggleGroup.getButton(index: Int): Button {
        return get(index) as Button
    }

    private fun View.animateBackgroundTint(
        @ColorInt colorFrom: Int,
        @ColorInt colorTo: Int
    ) {
        val colorAnimator = ValueAnimator.ofObject(ArgbEvaluator(), colorFrom, colorTo)
        colorAnimator.duration = 300
        colorAnimator.interpolator = DecelerateInterpolator()
        colorAnimator.addUpdateListener { animator ->
            backgroundTintList = ColorStateList.valueOf(animator.animatedValue as Int)
        }
        colorAnimator.start()
    }

    private fun View.animateWidth(old: Float, new: Float) {
        animateWidth(old.toInt(), new.toInt())
    }

    private fun uncheckAll() {
        // clearHighlighting()
        val temp = isSelectionRequired
        isSelectionRequired = false
        checkedButtonIds.forEach { uncheck(it) }
        isSelectionRequired = temp
    }
}