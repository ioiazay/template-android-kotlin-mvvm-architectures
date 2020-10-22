package com.odlsoon.mvvm_template.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class WebClient {
    private var retrofit: Retrofit? = null
    private var webService: WebService? = null

    private val BASE_URL = "https://api.stackexchange.com/2.2/"

    fun getService(): WebService {
        if (webService == null) {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY

            val builder = OkHttpClient().newBuilder()
            builder.readTimeout(5, TimeUnit.SECONDS)
            builder.connectTimeout(5, TimeUnit.SECONDS)

            builder.addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .method(chain.request().method(), chain.request().body())
                    .build()
                chain.proceed(request)
            }

            val client = builder.addInterceptor(interceptor).build()
            retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            webService = retrofit?.create(WebService::class.java)
        }

        return webService!!
    }

    companion object {
        val instance = WebClient()
    }
}