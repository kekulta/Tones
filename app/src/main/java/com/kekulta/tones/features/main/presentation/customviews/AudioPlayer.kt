package com.kekulta.tones.features.main.presentation.customviews


import android.animation.ObjectAnimator
import android.content.Context
import android.media.MediaPlayer
import android.net.Uri
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.animation.LinearInterpolator
import android.widget.LinearLayout
import androidx.core.animation.doOnEnd
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.progressindicator.LinearProgressIndicator
import com.kekulta.tones.R
import com.kekulta.tones.databinding.AudioPlayerLayoutBinding
import com.kekulta.tones.features.shared.getDrawable
import com.kekulta.tones.features.shared.getMaterialColorStateList
import com.google.android.material.R as Rm


class AudioPlayer @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0, defStyleRes: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {
    private val binding: AudioPlayerLayoutBinding =
        AudioPlayerLayoutBinding.inflate(LayoutInflater.from(context), this)
    private var player: MediaPlayer? = null


    init {
        layoutParams = LayoutParams(
            RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT
        )

        binding.root.background = getDrawable(R.drawable.player_background)
        binding.root.backgroundTintList =
            getMaterialColorStateList(Rm.attr.colorSurfaceContainerLow)

        binding.playButton.setOnClickListener {
            binding.playButton.icon = getDrawable(R.drawable.baseline_pause_circle_24)
            player?.apply {
                start()
                binding.progressIndicator.animate(duration.toLong())
            }
        }
    }

    fun bind(uri: Uri) {
        player?.release()
        binding.playButton.isEnabled = false

        player = MediaPlayer().apply {
            setDataSource(context, uri)
            setOnPreparedListener {
                binding.playButton.isEnabled = true
            }
            setOnCompletionListener {
                binding.playButton.icon = getDrawable(R.drawable.baseline_play_circle_24)
            }
            prepareAsync()
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        player?.release()
        player = null
    }

    private fun LinearProgressIndicator.animate(duration: Long) {
        val progressAnimator = ObjectAnimator.ofInt(this, "progress", 0, max)
        progressAnimator.duration = duration
        progressAnimator.setAutoCancel(true)
        progressAnimator.interpolator = LinearInterpolator()
        progressAnimator.doOnEnd {
            val progressAnimatorBack = ObjectAnimator.ofInt(this, "progress", max, 0)
            progressAnimatorBack.setAutoCancel(true)
            progressAnimatorBack.interpolator = LinearInterpolator()
            progressAnimatorBack.duration = 200
            progressAnimatorBack.start()
        }
        progressAnimator.start()
    }
}