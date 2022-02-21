package br.com.gtbulla.libraries.data

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

private const val BASE_URL = "https://api.github.com/"
private const val TIMEOUT_IN_SECONDS: Long = 30

private val clientBuilder: OkHttpClient.Builder = OkHttpClient.Builder()

private fun getClient(): OkHttpClient {
    clientBuilder
        .addInterceptor { chain ->
            val request = chain.request().newBuilder()
            val url = chain.request().url.newBuilder().build()
            request.url(url)
            return@addInterceptor chain.proceed(request.build())
        }
        .connectTimeout(TIMEOUT_IN_SECONDS, TimeUnit.SECONDS)
        .readTimeout(TIMEOUT_IN_SECONDS, TimeUnit.SECONDS)
    if (BuildConfig.DEBUG) {
        val loggingInterceptor = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger.DEFAULT)
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        clientBuilder.addInterceptor(loggingInterceptor)
    }
    return clientBuilder.build()
}

fun getRetrofit(): Retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .client(getClient())
    .addConverterFactory(MoshiConverterFactory.create())
    .build()
