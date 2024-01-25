package com.kekulta.tones.features.main.domain.mappers

import android.net.Uri
import com.kekulta.tones.features.main.domain.models.QuestionDo
import com.kekulta.tones.features.main.domain.models.TonePairDo

class QuestionMapper {
    fun map(pair: TonePairDo): QuestionDo {
        return QuestionDo.MinPair(
            tones = pair.tones,
            audio = Uri.parse(pair.audio),
            hanzi = pair.hanzi,
            pinyin = pair.pinyin,
            english = pair.english,
        )
    }
}