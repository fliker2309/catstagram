package com.example.androidtask5network.data.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.create

private const val BASE_URL = "https://api.thecatapi.com/v1/"

@ExperimentalSerializationApi
object RetrofitConfig {

    private val json = Json {
        prettyPrint = true
        ignoreUnknownKeys = true
    }

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val contentType = "application/json".toMediaType()

    private val httpClient = OkHttpClient.Builder()
        .addInterceptor(AuthInterceptor())
        .addInterceptor(loggingInterceptor)
        .addNetworkInterceptor(loggingInterceptor)
        .build()

    private val retrofitBuilder = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(httpClient)
        .addConverterFactory(json.asConverterFactory(contentType))
        .build()

    val theCatApiService: TheCatApiService = retrofitBuilder.create()
}
