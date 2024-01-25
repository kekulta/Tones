package com.kekulta.tones.features.main.data.api

import com.kekulta.tones.features.main.data.models.TonePairDto
import de.jensklingenberg.ktorfit.http.GET

interface YoYoApi {
    @GET("tools/tones")
    suspend fun getTones(): List<List<TonePairDto>>
}