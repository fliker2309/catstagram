package com.example.androidtask5network.data.network

import com.example.androidtask5network.utils.API_KEY
import com.example.androidtask5network.utils.AUTHORIZATION_HEADER
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val originalHttpUrl = originalRequest.url

        val request = originalRequest.newBuilder()
            .url(originalHttpUrl)
            .addHeader(AUTHORIZATION_HEADER, API_KEY)
            .build()

        return chain.proceed(request)
    }
}