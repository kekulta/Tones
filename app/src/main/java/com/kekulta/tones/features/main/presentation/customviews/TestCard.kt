package com.kekulta.tones.features.main.presentation.customviews


import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kekulta.tones.R
import com.kekulta.tones.databinding.TestLayoutBinding
import com.kekulta.tones.features.main.domain.models.ReturnStatus
import com.kekulta.tones.features.main.presentation.vo.QuestionVo
import com.kekulta.tones.features.shared.animateTextColor
import com.kekulta.tones.features.shared.dp
import com.kekulta.tones.features.shared.getDrawable
import com.kekulta.tones.features.shared.getMaterialColor
import com.kekulta.tones.features.shared.getMaterialColorStateList
import com.kekulta.tones.features.shared.hide
import com.kekulta.tones.features.shared.setPadding
import com.kekulta.tones.features.shared.show
import com.google.android.material.R as Rm


class TestCard @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0, defStyleRes: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {
    var onNextCallback: ((status: ReturnStatus) -> Unit)? = null

    private var solved = false

    private val binding: TestLayoutBinding =
        TestLayoutBinding.inflate(LayoutInflater.from(context), this)

    init {
        layoutParams = LayoutParams(
            RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT
        )

        setPadding(dp(10).toInt())

        binding.root.background = getDrawable(R.drawable.player_background)
        binding.root.backgroundTintList =
            getMaterialColorStateList(Rm.attr.colorSurfaceContainerLowest)

        orientation = VERTICAL

        binding.firstSyllableToggle.addOnButtonCheckedListener { _, _, _ -> checkButtonAvailability() }
        binding.secondSyllableToggle.addOnButtonCheckedListener { _, _, _ -> checkButtonAvailability() }

        binding.checkButton.setOnClickListener {
            solve()
        }

        binding.nextButton.setOnClickListener {
            onNextCallback?.invoke(ReturnStatus.CORRECT)
        }
    }

    fun bind(questionVo: QuestionVo) {
        when (questionVo) {
            is QuestionVo.Empty -> {}

            is QuestionVo.MinPair -> {
                binding.apply {
                    unsolve()

                    hanziTv.text = questionVo.hanzi
                    pinyinTv.text = questionVo.pinyin
                    englishTv.text = questionVo.english

                    audioPlayer.bind(questionVo.audio)

                    firstSyllableToggle.bind(
                        questionVo.firstTonedSyllable,
                        questionVo.tones.first
                    )
                    secondSyllableToggle.bind(
                        questionVo.secondTonedSyllable,
                        questionVo.tones.second
                    )
                }
            }
        }

    }

    private fun solve() {
        solved = true
        revealAnswers()
        binding.nextButton.show()
        binding.checkButton.hide()
        binding.checkButton.isEnabled = false
        binding.nextButton.isEnabled = true
        binding.firstSyllableToggle.check()
        binding.secondSyllableToggle.check()
    }

    private fun unsolve() {
        solved = false
        hideAnswersNow()
        binding.nextButton.hide()
        binding.checkButton.show()
        binding.checkButton.isEnabled = false
        binding.nextButton.isEnabled = false
    }

    private fun revealAnswers() {
        binding.apply {
            hanziTv.revealText()
            pinyinTv.revealText()
            englishTv.revealText()
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
        animateTextColor(
            getMaterialColor(Rm.attr.colorSurfaceContainerLow),
            getMaterialColor(Rm.attr.colorOnSurfaceVariant)
        )
    }

    private fun checkButtonAvailability() {
        binding.checkButton.isEnabled =
            binding.firstSyllableToggle.checkedButtonIds.isNotEmpty() && binding.secondSyllableToggle.checkedButtonIds.isNotEmpty() && !solved
    }
}