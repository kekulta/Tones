package com.kekulta.tones.features.main.data.api

import com.kekulta.tones.features.main.data.models.TonePairDto

interface NetworkDataStore {
    suspend fun getTones(): List<TonePairDto>
}