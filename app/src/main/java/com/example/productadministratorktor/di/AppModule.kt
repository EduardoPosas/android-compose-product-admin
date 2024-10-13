package com.example.productadministratorktor.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.resources.Resources
import io.ktor.http.URLProtocol
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideKtorHttpClient(): HttpClient = HttpClient(Android) {
        // engine configuration
        engine { }
        // default request config
        defaultRequest {
            host = "192.168.1.69"
            port = 8000
            url {
                protocol = URLProtocol.HTTP
            }
        }
        // type safe requests
        install(Resources)
        // serialization/deserialization json
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
            })
        }
        // Default response validation
        expectSuccess = true
    }
}