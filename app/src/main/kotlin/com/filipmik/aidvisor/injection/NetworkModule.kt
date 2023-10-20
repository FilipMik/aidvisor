package com.filipmik.aidvisor.injection

import com.filipmik.aidvisor.BuildConfig
import com.filipmik.aidvisor.data.remote.AidvisorRoutes
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.observer.ResponseObserver
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import timber.log.Timber
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun provideHttpClient(
        json: Json
    ) : HttpClient {
        return HttpClient(Android) {
            install(Logging) {
                logger = object: Logger {
                    override fun log(message: String) {
                        Timber.d("KTOR: $message")
                    }
                }
                level = LogLevel.ALL
            }

            install(DefaultRequest) {
                url(AidvisorRoutes.BASE_URL)
                header(HttpHeaders.ContentType, ContentType.Application.Json)
                bearerAuth(BuildConfig.API_KEY)
            }

            install(ContentNegotiation) {
                json(json)
            }

            install(ResponseObserver) {
                onResponse { response ->
                    Timber.d("HTTP status:", "${response.status.value}")
                }
            }
        }
    }
}
