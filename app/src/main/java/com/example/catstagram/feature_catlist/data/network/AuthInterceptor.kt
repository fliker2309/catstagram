package com.example.catstagram.data.network

import okhttp3.Interceptor
import okhttp3.Response

private const val AUTHORIZATION_HEADER = "Authorization"
private const val API_KEY = "a3239555-704c-4dd2-8c99-62c5d72aae28"

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
