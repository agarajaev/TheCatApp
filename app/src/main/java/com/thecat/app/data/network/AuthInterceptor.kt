package com.thecat.app.data.network

import okhttp3.Interceptor

class AuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
        var request = chain.request()
        request =
            request.newBuilder().header("x-api-key", "0e1dae39-4acb-449b-adcc-39e2e1ea51c0").build()
        return chain.proceed(request)
    }
}