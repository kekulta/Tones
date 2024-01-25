package com.kekulta.tones.di

import com.kekulta.tones.features.main.data.api.NetworkDataStore
import com.kekulta.tones.features.main.data.api.YoYoApi
import com.kekulta.tones.features.main.data.impl.NetworkDataStoreImpl
import com.kekulta.tones.features.main.domain.QuestionsRepository
import com.kekulta.tones.features.main.domain.mappers.QuestionMapper
import com.kekulta.tones.features.main.domain.mappers.TonePairMapper
import com.kekulta.tones.features.main.domain.viewmodels.MainViewModel
import com.kekulta.tones.features.main.presentation.formatters.QuestionVoFormatter
import com.kekulta.tones.features.settings.SettingsRepository
import de.jensklingenberg.ktorfit.Ktorfit
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val koinModule = module {
    single<NetworkDataStore> { NetworkDataStoreImpl(get()) }
    single<YoYoApi> { ktorfit(get(), "https://yoyochinese.com/api/v1/").create() }
    single { jsonClient() }
    single { QuestionsRepository(get(), get(), get()) }
    single { SettingsRepository() }

    factory { TonePairMapper() }
    factory { QuestionMapper() }
    factory { QuestionVoFormatter() }
}

val viewModelsModule = module {
    viewModel { MainViewModel(get(), get()) }
}

private fun ktorfit(client: HttpClient, baseUrl: String): Ktorfit {
    return Ktorfit.Builder().httpClient(client).baseUrl(baseUrl)
        .build()
}

private fun jsonClient(): HttpClient {
    return HttpClient(CIO) {
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                ignoreUnknownKeys = true
            })
        }
    }
}