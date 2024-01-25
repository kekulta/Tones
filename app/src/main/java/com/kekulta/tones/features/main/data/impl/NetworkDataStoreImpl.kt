package com.kekulta.tones.features.main.data.impl

import com.kekulta.tones.features.main.data.api.NetworkDataStore
import com.kekulta.tones.features.main.data.api.YoYoApi
import com.kekulta.tones.features.main.data.models.TonePairDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class NetworkDataStoreImpl(private val api: YoYoApi) : NetworkDataStore {
    override suspend fun getTones(): List<TonePairDto> {
        return withContext(Dispatchers.IO) { api.getTones().flatten() }
    }
}