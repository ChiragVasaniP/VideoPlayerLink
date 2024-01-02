package com.mediaPlayer.link.network

import com.mediaPlayer.link.BuildConfig
import com.mediaPlayer.link.network.okhttploginterceptor.LoggingInterceptor
import com.mediaPlayer.link.network.okhttploginterceptor.Priority
import okhttp3.Dispatcher
import okhttp3.OkHttpClient
import okhttp3.Protocol
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit


class RetrofitClient private constructor() {
    val myApi: ApiInterFace
    private var client = OkHttpClient.Builder()
    private val loggIn: HttpLoggingInterceptor = HttpLoggingInterceptor()

    companion object {
        @get:Synchronized
        var instance: RetrofitClient? = null
            get() {
                if (field == null) {
                    field = RetrofitClient()
                }
                return field
            }
            private set
    }

    init {
        val logInterceptor by lazy {
            LoggingInterceptor(showLog = true, isShowAll = false, priority = Priority.I, visualFormat = true)
        }

        val dispatcher = Dispatcher()
        dispatcher.maxRequests = 9999999
        client.addInterceptor(logInterceptor)
            .connectTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .protocols(listOf(Protocol.HTTP_1_1))
            .dispatcher(dispatcher)
            .retryOnConnectionFailure(true)
        loggIn.setLevel(HttpLoggingInterceptor.Level.BODY)
        val retrofit = Retrofit.Builder().baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(client.build())
            .build()
        myApi = retrofit.create(ApiInterFace::class.java)
    }


}