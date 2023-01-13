package com.hamedsafari.filckrphotos.data.network.util

import com.hamedsafari.filckrphotos.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val currentRequest = chain.request()
        val url = currentRequest.url.newBuilder()
            .addQueryParameter("format", "json")
            .addQueryParameter("nojsoncallback", "1")
            .addQueryParameter("api_key", BuildConfig.FLICKR_KEY)
            .build()
        val newRequest = currentRequest.newBuilder()
            .url(url)
            .build()
        return chain.proceed(newRequest)
    }
}
